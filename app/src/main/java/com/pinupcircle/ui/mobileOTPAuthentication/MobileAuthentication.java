package com.pinupcircle.ui.mobileOTPAuthentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MobileAuthentication extends AppCompatActivity {
    JSONObject jsonObjectResponse;
    EditText edTMobileNumberAuthentication;
    UserSubscriberModel subscriberModel;
    String json = null;
    Context mContext;
    String tag_string_obj = "json_string_req";
    AppProgressDialog appProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);
        mContext = MobileAuthentication.this;
        initFields();
    }

    private void initFields() {
        appProgressDialog = new AppProgressDialog(mContext, "Please wait..");
        subscriberModel = new UserSubscriberModel();
        edTMobileNumberAuthentication = findViewById(R.id.edTMobileNumberAuthentication);
    }

    public void onMobileAuthenticationClick(View view) {
        if (validate())
            doMobileAuthentication();
    }

    private void doMobileAuthentication() {
        appProgressDialog.initializeProgress();
        appProgressDialog.showProgressDialog();
        subscriberModel.setUserPhone(Long.valueOf(edTMobileNumberAuthentication.getText().toString().trim()));
        subscriberModel.setUserAge("12");
        subscriberModel.setUserEmail("None");
        subscriberModel.setUserPickRef("None");
        subscriberModel.setUserCountryCode("IN");
        subscriberModel.setBonusPoints("0");
        subscriberModel.addUserPins("0");
        subscriberModel.setOtpEntity("0");
        subscriberModel.addUserAddresses("None", "None", "None", "None");
        subscriberModel.addUserSocialInterests("None");
        Gson gson = new Gson();
        final String requestBody = gson.toJson(subscriberModel);
        System.out.println("MobileAuthentication" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.sub_registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("MobileAuthentctionResponse" + response);
                try {
                    jsonObjectResponse = new JSONObject(response);
                    int statusCode = jsonObjectResponse.getInt("registrationStatus");
                    if (statusCode == 1) {
                        appProgressDialog.hideProgressDialog();
                        /*Toast.makeText(MobileAuthentication.this, ""
                                        + jsonObjectResponse.getString("description")
                                , Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                        clearEditText();
                        Intent i = new Intent(mContext, OTPAuthenticaton.class);
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                        startActivity(i);
                        //finish();
                    } else {
                        appProgressDialog.hideProgressDialog();
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    appProgressDialog.hideProgressDialog();
                    System.out.println("MobileRegistrationWebServiceError" + e.getMessage());
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                appProgressDialog.hideProgressDialog();
                System.out.println("JsnObjetMobileRegistrationErrorResponse" + error.toString());
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(mContext,
                            "Oops. Timeout error!",
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest, tag_string_obj);
    }

    private boolean validate() {
        Boolean validResp = true;
        if (edTMobileNumberAuthentication.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, " Name cannot be blank ", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"First Name cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Mobile Number cannot be blank", Snackbar.LENGTH_SHORT).show();
            edTMobileNumberAuthentication.requestFocus();
            edTMobileNumberAuthentication.setText("");
            return validResp;
        }
        return validResp;
    }

    private void clearEditText() {
        edTMobileNumberAuthentication.setText("");
    }

}
