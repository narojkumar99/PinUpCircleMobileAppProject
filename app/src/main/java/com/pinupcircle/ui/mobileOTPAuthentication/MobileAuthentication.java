package com.pinupcircle.ui.mobileOTPAuthentication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.pinupcircle.model.OTPAuthenticModel;

import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.home.DashboardActivity;
import com.pinupcircle.ui.map.MapPlaceActivity;
import com.pinupcircle.ui.subscriberRegistration.SubscriberRegistration;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.CustomPreference;
import com.pinupcircle.utils.Utility;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MobileAuthentication extends AppCompatActivity {
    JSONObject jsonObjectResponse;
    EditText edTMobileNumberAuthentication, otpValue;
    OTPAuthenticModel otpAuthenticModel;
    Context mContext;
    String tag_string_obj = "json_string_req";
    AppProgressDialog appProgressDialogOtpSend;
    AppCompatButton btnMobileVerify;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);
        mContext = MobileAuthentication.this;
        initFields();

        btnMobileVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMobileVerify.setEnabled(false);
                hideKeyboard();
                sendMobileAuthentication();
            }
        });

        edTMobileNumberAuthentication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() >= 10) {
                    btnMobileVerify.setEnabled(true);
                    btnMobileVerify.setBackgroundResource(R.drawable.register_active);
                } else {
                    btnMobileVerify.setEnabled(false);
                    btnMobileVerify.setBackgroundResource(R.drawable.register_in_active);
                }

            }
        });

    }

    private void sendMobileAuthentication() {
        if (validate())
            doMobileAuthentication();
    }

    private void initFields() {

        otpAuthenticModel = new OTPAuthenticModel();
        edTMobileNumberAuthentication = findViewById(R.id.edTMobileNumberAuthentication);
        btnMobileVerify = (AppCompatButton) findViewById(R.id.btnVerify);
    }


    private void doMobileAuthentication() {
        btnMobileVerify.setBackgroundResource(R.drawable.register_in_active);
        btnMobileVerify.setEnabled(false);
        appProgressDialogOtpSend = new AppProgressDialog(mContext, "OTP send to your mobile Number");
        appProgressDialogOtpSend.initializeProgress();
        appProgressDialogOtpSend.showProgressDialog();
        otpAuthenticModel.setMobileNum(Long.valueOf(edTMobileNumberAuthentication.getText().toString().trim()));
        Gson gson = new Gson();
        final String requestBody = gson.toJson(otpAuthenticModel);
        System.out.println("MobileAuthentication" + requestBody);
        //Toast.makeText(mContext, ""+requestBody, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.services_registerUserWithMobileNum, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     appProgressDialogOtpSend.hideProgressDialog();
                     System.out.println("MobileAuthentctionResponse" + response);
                     jsonObjectResponse = new JSONObject(response);
                     userId = jsonObjectResponse.getInt("userId");
                     CustomPreference.with(mContext).save(Constants.userId, userId);
                    redirectIntent();

                } catch (JSONException e) {
                    appProgressDialogOtpSend.hideProgressDialog();
                    System.out.println("MobileRegistrationWebServiceError" + e.getMessage());
                    e.printStackTrace();
                    btnMobileVerify.setEnabled(true);
                    btnMobileVerify.setBackgroundResource(R.drawable.register_active);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnMobileVerify.setEnabled(true);
                btnMobileVerify.setBackgroundResource(R.drawable.register_active);
                appProgressDialogOtpSend.hideProgressDialog();
                System.out.println("JsnObjetMobileRegistrationErrorResponse" + error.toString());
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
            hideKeyboard();
            return validResp;
        }
        return validResp;
    }

    private void clearEditText() {
        //edTMobileNumberAuthentication.setText("");
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void redirectIntent() {
        clearEditText();
        Toast.makeText(mContext, ""+ CustomPreference.with(this).getInt(Constants.userId,0), Toast.LENGTH_SHORT).show();
       /* Intent mIntent = new Intent(MobileAuthentication.this, MapPlaceActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();*/
         /* Intent mIntent = new Intent(MobileAuthentication.this, OTPAuthenticaton.class);
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    finish();*/

    }
}


