package com.pinupcircle.ui.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pinupcircle.R;
import com.pinupcircle.ui.launcher.SplashActivity;

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
                startActivity(new Intent(UserScriberActivity.this, Subscriber.class));
            }
        });
    }

    private void initFields() {

        imgRegister=(ImageView)findViewById(R.id.imgRegister);
    }
}
