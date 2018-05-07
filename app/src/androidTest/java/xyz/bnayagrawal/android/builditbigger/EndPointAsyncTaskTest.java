package xyz.bnayagrawal.android.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import xyz.bnayagrawal.android.builditbigger.gcloud.EndpointsAsyncTask;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest extends AndroidTestCase {

    private final CountDownLatch signal = new CountDownLatch(1);

    @Test
    public void getJokeFromBackend() throws InterruptedException {
        new EndpointsAsyncTask().execute(new EndpointsAsyncTask.Callback() {
            @Override
            public void onPostExecute(String joke) {
                assertThat(joke, not(isEmptyString()));
                signal.countDown();
            }
        });
        signal.await(5, TimeUnit.SECONDS);
    }
}