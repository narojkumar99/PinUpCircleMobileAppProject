package com.pinupcircle.ui.businessServicesProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.RegisteredUserModel;
import com.pinupcircle.model.ServiceProviderModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;

import java.io.UnsupportedEncodingException;

public class BusinessServiceProvider extends AppCompatActivity {
    //final String url = "http://13.59.60.142:8080/serviceprovider";
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
    Context mContext;
    AppProgressDialog appProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        mContext = BusinessServiceProvider.this;
        initValues();
    }

    private void initValues() {
        appProgressDialog = new AppProgressDialog(mContext, "Please wait..");
        user = new RegisteredUserModel(this);
        serviceProviderModel = new ServiceProviderModel();
        imgServiceNext = findViewById(R.id.imgServiceNext);
        editTextServiceProviderName = findViewById(R.id.editTextServiceProviderName);
        editTextServiceProviderService = findViewById(R.id.editTextServiceProviderService);
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
        serviceProviderModel.setBusinessAddress(editTextServiceProviderAddress.getText().toString().trim(), editTextServiceCity.getText().toString().trim(), editTextServiceState.getText().toString().trim());
        serviceProviderModel.setBusinessPhone(Long.valueOf(editTextServiceProviderMobileNumber.getText().toString().trim()));
        serviceProviderModel.setBusinessEmail(editTextServiceProviderEmail.getText().toString().trim());
        serviceProviderModel.addBusinessPins(editTextServicePinCode.getText().toString().trim());
        serviceProviderModel.addUserExpertise("Crafting");
        serviceProviderModel.addUserExpertise("Designing");
        serviceProviderModel.addUserWorkView("abc.jpeg");

        Gson gson = new Gson();
        final String requestBody = gson.toJson(serviceProviderModel);
        System.out.println("BusinessServiceProvider" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url +
                Constants.services_provied_reg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("BusinessServiceProviderResponse" + " " + response);
                Toast.makeText(BusinessServiceProvider.this, "Business Registered Successfully", Toast.LENGTH_SHORT).show();
                clearEditText();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                System.out.println("JsonObjetBusinessProviderSubscriberRegistrationErrorResponse" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(mContext,
                            "Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
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
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void clearEditText() {

        editTextServiceProviderName.setText("");
        editTextServiceProviderAddress.setText("");
        editTextServiceProviderMobileNumber.setText("");
        editTextServiceProviderService.setText("");
        editTextServiceState.setText("");
        editTextServicePinCode.setText("");
        editTextServiceProviderEmail.setText("");
        editTextServiceCity.setText("");
    }
}



