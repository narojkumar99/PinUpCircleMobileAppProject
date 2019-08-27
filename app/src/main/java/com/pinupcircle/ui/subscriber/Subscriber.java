package com.pinupcircle.ui.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.ui.otpVerify.OTPAuthenticaton;

import org.json.JSONException;
import org.json.JSONObject;

public class Subscriber extends AppCompatActivity {

    final String url="http://13.59.60.142:8080/users/registerUser?userdef=abc";
    UserSubscriberModel subscriberModel;
    ImageView imgNext;
    EditText editTextSubscriberName,editTextSubscriberAddress,editTextMobileNumber,editTextLocality,editTextPinCode,editTextSubsriberEmail;
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
        subscriberModel=new UserSubscriberModel();
        imgNext = (ImageView) findViewById(R.id.imgNext);
        editTextSubscriberName=findViewById(R.id.editTextSubscriberName);
        editTextLocality=findViewById(R.id.editTextLocality);
        editTextMobileNumber=findViewById(R.id.editTextMobileNumber);
        editTextPinCode=findViewById(R.id.editTextPinCode);
        editTextSubscriberAddress=findViewById(R.id.editTextSubscriberAddress);
        editTextSubsriberEmail=findViewById(R.id.editTextSubscriberEmail);
    }

    public void onLoginClick(View view) {
        subscriberModel.setUserName(editTextSubscriberName.getText().toString().trim());
        subscriberModel.setUserPhone(editTextMobileNumber.getText().toString().trim());
        subscriberModel.setUserAge("34");
        subscriberModel.setUserEmail(editTextSubsriberEmail.getText().toString().trim());
        subscriberModel.setUserPickRef("c://image");
        subscriberModel.setUserCountryCode("IN");
        subscriberModel.setBonusPoints("100");
        subscriberModel.addUserPins(editTextPinCode.getText().toString());
        subscriberModel.setOtpEntity("123456789");

        subscriberModel.addUserAddresses(editTextSubscriberAddress.toString().trim(),"Kokata","Salt Lake","700108");
        subscriberModel.addUserSocialInterests("None");

        Gson gson=new Gson();
        try {
            JSONObject requestObject=new JSONObject(gson.toJson(subscriberModel));
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url,requestObject,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(Subscriber.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
