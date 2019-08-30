package com.pinupcircle.ui.subscriberRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pinupcircle.R;
import com.pinupcircle.ui.mobileOTPAuthentication.MobileAuthentication;

public class UserScriberActivity extends AppCompatActivity {


    ImageView imgRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_scriber);

        initFields();

        imgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserScriberActivity.this, MobileAuthentication.class);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initFields() {

        imgRegister=(ImageView)findViewById(R.id.imgRegister);
    }
}
