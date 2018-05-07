package xyz.bnayagrawal.android.builditbigger.gcloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

public class EndpointsAsyncTaskTest {

    String joke;
    CountDownLatch signal;
    EndpointsAsyncTask endpointsAsyncTask;

    @Before
    public void setUp() throws Exception {
        signal = new CountDownLatch(1);
        endpointsAsyncTask = Mockito.mock(EndpointsAsyncTask.class);
    }

    @Test
    public void getJokeFromBackend() throws InterruptedException {
        endpointsAsyncTask.execute(new EndpointsAsyncTask.Callback() {
            @Override
            public void onPostExecute(String data) {
                joke = data;
                signal.countDown();
            }
        });
        signal.await(15, TimeUnit.SECONDS);
        assertThat(joke, not(isEmptyString()));
    }

    @After
    public void tearDown() throws Exception {
        endpointsAsyncTask = null;
    }
}