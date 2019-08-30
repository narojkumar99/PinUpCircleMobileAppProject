package com.pinupcircle.ui.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
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
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.ServiceProvider;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Subscriber extends AppCompatActivity {

    String tag_json_obj = "json_string_req";
    //final String url = "http://13.59.60.142:8080/users/registerUser?userdef=abc";
    UserSubscriberModel subscriberModel;
    ImageView imgNext;
    EditText editTextSubscriberName,
            editTextSubscriberAddress,
            editTextMobileNumber,
            editTextState,
            editTextPinCode,
            editTextSubsriberEmail,
            editTextCity;
    String mobileNumber = "";
    JSONObject jsonObjectResponse = null;
    String json = null;
    Context mContext;
    AppProgressDialog appProgressDialog;
    RegisteredUserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);
        mContext = Subscriber.this;
        initFields();
       /* editTextState.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String otpValue = editTextState.getText().toString().trim();
                if (otpValue.length() == 6) {
                    //imgNext.setEnabled(true);
                    //imgNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    //imgNext.setEnabled(false);
                    //imgNext.setBackgroundColor(getResources().getColor(R.color.grey));
                }
            }
        });*/
    }

    private void initFields() {
        appProgressDialog =new AppProgressDialog(mContext,"Please wait..");
        subscriberModel = new UserSubscriberModel();
        user=new RegisteredUserModel(this);
        imgNext = findViewById(R.id.imgNext);
        editTextSubscriberName = findViewById(R.id.editTextSubscriberName);
        editTextState = findViewById(R.id.editTextState);
        editTextCity = findViewById(R.id.editTextCity);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPinCode = findViewById(R.id.editTextPinCode);
        editTextSubscriberAddress = findViewById(R.id.editTextSubscriberAddress);
        editTextSubsriberEmail = findViewById(R.id.editTextSubscriberEmail);
    }

    public void onLoginClick(View view) {
        if (validate()) {
            user.logOut();
            doSubscriberRegistration();
        }else{
            startActivity(new Intent(Subscriber.this, ServiceProvider.class));
        }
    }

    private void doSubscriberRegistration() {
        appProgressDialog.initializeProgress();
        appProgressDialog.showProgressDialog();
        subscriberModel.setUserName(editTextSubscriberName.getText().toString().trim());
        subscriberModel.setUserPhone(Long.valueOf(editTextMobileNumber.getText().toString()));
        subscriberModel.setUserAge("34");
        subscriberModel.setUserEmail(editTextSubsriberEmail.getText().toString().trim());
        subscriberModel.setUserPickRef("c:image");
        subscriberModel.setUserCountryCode("IN");
        subscriberModel.setBonusPoints("100");
        subscriberModel.addUserPins(editTextPinCode.getText().toString().trim());
        subscriberModel.setOtpEntity("123456789");
        subscriberModel.addUserAddresses(editTextSubscriberAddress.getText().toString().trim(), "Kokata", "Salt Lake", "700108");
        subscriberModel.addUserSocialInterests("None");
        Gson gson = new Gson();
        final String requestBody = gson.toJson(subscriberModel);
        System.out.println("tetag" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.sub_registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    jsonObjectResponse = new JSONObject(response);
                    int statusCode = jsonObjectResponse.getInt("registrationStatus");
                    user.setUserRegId(jsonObjectResponse.getInt("userRegId"));
                    user.setUserName(jsonObjectResponse.getString("userName"));
                    if (statusCode == 1) {
                        appProgressDialog.hideProgressDialog();
                        Toast.makeText(Subscriber.this, ""
                                        + jsonObjectResponse.getString("description")
                                , Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                        clearEditText();
                        startActivity(new Intent(Subscriber.this, ServiceProvider.class));
                    } else {
                        appProgressDialog.hideProgressDialog();
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    appProgressDialog.hideProgressDialog();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                appProgressDialog.hideProgressDialog();
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = Constants.trimMessage(json, "message");
                            if (json != null) Constants.displayMessage(mContext, json);
                            break;
                    }
                    //Additional cases
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,tag_json_obj);
    }

    private boolean validate() {
        Boolean validResp = true;
        if (editTextSubscriberName.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, " Name cannot be blank ", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"First Name cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Name cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextSubscriberName.requestFocus();
            editTextSubscriberName.setText("");
            return validResp;
        } else if (editTextMobileNumber.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "Mobile Number cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Last Name cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Mobile Number cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextMobileNumber.requestFocus();
            editTextMobileNumber.setText("");
            return validResp;
        } else if (!Constants.emailValidator(editTextSubsriberEmail.getText().toString().trim())) {
            validResp = false;
            //Toast.makeText(this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Email cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextSubsriberEmail.requestFocus();
            editTextSubsriberEmail.setText("");
            return validResp;
        } else if (editTextCity.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "City cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "City cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextCity.requestFocus();
            editTextCity.setText("");
            return validResp;

        } else if (editTextPinCode.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "PinCode cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "PinCode cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextPinCode.requestFocus();
            editTextPinCode.setText("");
            return validResp;
        } else if (editTextState.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "State cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "State cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextState.requestFocus();
            editTextState.setText("");
            return validResp;
        }
        return validResp;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void clearEditText() {
        editTextSubscriberName.setText("");
        editTextState.setText("");
        editTextPinCode.setText("");
        editTextMobileNumber.setText("");
        editTextCity.setText("");
        editTextSubsriberEmail.setText("");
        editTextSubscriberAddress.setText("");
    }
}
