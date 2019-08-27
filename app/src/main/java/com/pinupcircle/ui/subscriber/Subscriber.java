package com.pinupcircle.ui.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pinupcircle.R;
import com.pinupcircle.ui.otpVerify.OTPAuthenticaton;

public class Subscriber extends AppCompatActivity {
    ImageView imgNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);
        initFields();
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Subscriber.this,OTPAuthenticaton.class));
            }
        });
    }
    private void initFields() {
        imgNext = (ImageView) findViewById(R.id.imgNext);
    }
}
