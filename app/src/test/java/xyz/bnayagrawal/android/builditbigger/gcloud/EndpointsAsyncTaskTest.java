package xyz.bnayagrawal.android.builditbigger.gcloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

public class EndpointsAsyncTaskTest {

    EndpointsAsyncTask endpointsAsyncTask;

    @Before
    public void setUp() throws Exception {
        endpointsAsyncTask = Mockito.mock(EndpointsAsyncTask.class);
    }

    @Test
    public void getJokeFromBackend() {

        endpointsAsyncTask.execute(new EndpointsAsyncTask.Callback() {
            @Override
            public void onPostExecute(String data) {
                assertThat(data, not(isEmptyString()));
            }
        });
    }

    @After
    public void tearDown() throws Exception{
        endpointsAsyncTask = null;
    }
}