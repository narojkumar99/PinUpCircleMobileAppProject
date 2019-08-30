package com.pinupcircle.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.RegisteredUserModel;
import com.pinupcircle.model.ServiceProviderModel;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.subscriber.Subscriber;
import com.pinupcircle.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ServiceProvider extends AppCompatActivity {
    final String url="http://13.59.60.142:8080/serviceprovider";
    RegisteredUserModel user;
    ServiceProviderModel serviceProviderModel;
    ImageView imgServiceNext;
    EditText editTextServiceProviderName,
            editTextServiceProviderAddress,
            editTextServiceProviderMobileNumber,
            editTextServiceProviderService,
            editTextServiceState,
            editTextServicePinCode,
            editTextServiceProviderEmail,
            editTextServiceCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        initValues();
    }

    private void initValues() {
        user=new RegisteredUserModel(this);
        serviceProviderModel=new ServiceProviderModel();
        imgServiceNext = findViewById(R.id.imgServiceNext);
        editTextServiceProviderName = findViewById(R.id.editTextServiceProviderName);
        editTextServiceProviderService=findViewById(R.id.editTextServiceProviderService);
        editTextServiceState = findViewById(R.id.editTextServiceState);
        editTextServiceCity = findViewById(R.id.editTextServiceCity);
        editTextServiceProviderMobileNumber = findViewById(R.id.editTextServiceProviderMobileNumber);
        editTextServicePinCode = findViewById(R.id.editTextServicePinCode);
        editTextServiceProviderAddress = findViewById(R.id.editTextServiceProviderAddress);
        editTextServiceProviderEmail = findViewById(R.id.editTextServiceProviderEmail);
    }

    public void onServiceRegistrationClick(View view) {
        serviceProviderModel.setUserRegId(user.getUserRegId());
        serviceProviderModel.setUserName(user.getUserName());
        serviceProviderModel.setShopName(editTextServiceProviderName.getText().toString().trim());
        serviceProviderModel.setServiceSpecification(editTextServiceProviderService.getText().toString().trim());
        serviceProviderModel.setBusinessType("Consumer Service");
        serviceProviderModel.setBusinessAddress(editTextServiceProviderAddress.getText().toString().trim(),editTextServiceCity.getText().toString().trim(),editTextServiceState.getText().toString().trim());
        serviceProviderModel.setBusinessPhone(Long.valueOf(editTextServiceProviderMobileNumber.getText().toString().trim()));
        serviceProviderModel.setBusinessEmail(editTextServiceProviderEmail.getText().toString().trim());
        serviceProviderModel.addBusinessPins(editTextServicePinCode.getText().toString().trim());
        serviceProviderModel.addUserExpertise("Crafting");
        serviceProviderModel.addUserExpertise("Designing");
        serviceProviderModel.addUserWorkView("abc.jpeg");

        Gson gson = new Gson();
        final String requestBody = gson.toJson(serviceProviderModel);
        System.out.println("tetag" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
