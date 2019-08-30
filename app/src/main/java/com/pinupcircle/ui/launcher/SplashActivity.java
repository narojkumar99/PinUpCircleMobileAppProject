package com.pinupcircle.ui.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.pinupcircle.R;
import com.pinupcircle.ui.subscriberRegistration.UserScriberActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        scheduleSplashScreen();
    }

    private void scheduleSplashScreen() {
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(SplashActivity.this, UserScriberActivity.class));
                Intent intent = new Intent(SplashActivity.this, UserScriberActivity.class);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
