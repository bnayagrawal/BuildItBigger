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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import xyz.bnayagrawal.android.builditbigger.gcloud.EndpointsAsyncTask;
import xyz.bnayagrawal.android.jokepresenter.JokeViewActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button tellJokeButton;
    private ProgressBar jokeLoadingProgress;

    private InterstitialAd interstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(root);
        return root;
    }

    private void initViews(View view) {
        tellJokeButton = view.findViewById(R.id.tellJokeButton);
        jokeLoadingProgress = view.findViewById(R.id.jokeLoadingProgress);

        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleProgressVisibility(true);
                new EndpointsAsyncTask().execute(new EndpointsAsyncTask.Callback() {
                    @Override
                    public void onPostExecute(String data) {
                        if (null == data)
                            data = "";

                        toggleProgressVisibility(false);
                        Intent intent = new Intent(getContext(), JokeViewActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, data);
                        startActivity(intent);
                    }
                });
            }
        });

        //Google AdMob
        MobileAds.initialize(getContext(),
                "ca-app-pub-3940256099942544~3347511713");

        //Banner Ad
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstitial Ad
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
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
            //show interstitial ad
            if (interstitialAd.isLoaded())
                interstitialAd.show();
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
