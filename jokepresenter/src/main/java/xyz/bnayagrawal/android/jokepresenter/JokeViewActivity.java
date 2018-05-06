package xyz.bnayagrawal.android.jokepresenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_view);

        Intent receivedIntent = getIntent();
        if(receivedIntent != null && receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
            ((TextView)findViewById(R.id.tvJoke))
                    .setText(receivedIntent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
