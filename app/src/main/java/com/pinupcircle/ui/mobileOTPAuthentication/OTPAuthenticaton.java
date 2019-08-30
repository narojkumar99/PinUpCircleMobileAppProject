package com.pinupcircle.ui.mobileOTPAuthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;
import com.pinupcircle.R;
import com.pinupcircle.ui.subscriberRegistration.SubscriberNameRegistration;
import com.pinupcircle.ui.subscriberRegistration.SubscriberRegistration;

public class OTPAuthenticaton extends AppCompatActivity {

    ImageView imgVerify;
    PinView otpPinSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_authenticaton);
        initFields();
        imgVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpPinSet.getText().toString().length() >= 6) {
                    // services call after all fields entered
                    otpAuthenticationVerify();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "All fields cannot be blank", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void otpAuthenticationVerify() {
        startActivity(new Intent(OTPAuthenticaton.this, SubscriberRegistration.class));
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();

    }

    private void initFields() {
        imgVerify = (ImageView) findViewById(R.id.imgVerify);
        otpPinSet = findViewById(R.id.otpVerify);
    }
}
