package com.propertyguru.hackernews.hackernews.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.propertyguru.hackernews.hackernews.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen_acitivty);

        /*Loads Next Activity after 2 seconds*/
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                launchNextActivity();
            }
        }, 2000);
    }


    private void launchNextActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, StoryActivity.class);
        startActivity(intent);
        finish();
    }

}
