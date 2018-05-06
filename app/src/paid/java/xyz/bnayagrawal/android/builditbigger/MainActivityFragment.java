package xyz.bnayagrawal.android.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import xyz.bnayagrawal.android.builditbigger.gcloud.EndpointsAsyncTask;
import xyz.bnayagrawal.android.jokepresenter.JokeViewActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.Callback {

    private Button tellJokeButton;
    private ProgressBar jokeLoadingProgress;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onPostExecute(String data) {
        if (null == data)
            data = "";

        toggleProgressVisibility(false);
        Intent intent = new Intent(getContext(), JokeViewActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intent);
    }

    private void initViews(View view) {
        tellJokeButton = view.findViewById(R.id.tellJokeButton);
        jokeLoadingProgress = view.findViewById(R.id.jokeLoadingProgress);

        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleProgressVisibility(true);
                new EndpointsAsyncTask().execute(MainActivityFragment.this);
            }
        });
    }

    private void toggleProgressVisibility(boolean show) {
        if(show) {
            tellJokeButton.setVisibility(View.GONE);
            jokeLoadingProgress.setAlpha(0.0f);
            jokeLoadingProgress.setVisibility(View.VISIBLE);
            jokeLoadingProgress.animate()
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(300)
                    .alpha(1.0f);
        } else {
            jokeLoadingProgress.animate()
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(300)
                    .alpha(0.0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    jokeLoadingProgress.setVisibility(View.GONE);
                    tellJokeButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
