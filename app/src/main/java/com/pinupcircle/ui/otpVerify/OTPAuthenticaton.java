package com.pinupcircle.ui.otpVerify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pinupcircle.R;
import com.pinupcircle.ui.subscriber.Subscriber;

public class OTPAuthenticaton extends AppCompatActivity {

    ImageView imgVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_authenticaton);


        initFields();

        imgVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPAuthenticaton.this,PinCode.class));
            }
        });
    }

    private void initFields() {
        imgVerify = (ImageView) findViewById(R.id.imgVerify);
    }
}
