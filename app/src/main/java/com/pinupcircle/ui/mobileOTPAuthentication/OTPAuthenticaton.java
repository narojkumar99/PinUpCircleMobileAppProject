package com.pinupcircle.ui.mobileOTPAuthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.OTPValidateModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.home.DashboardActivity;
import com.pinupcircle.ui.map.MapPlaceActivity;
import com.pinupcircle.ui.subscriberRegistration.SubscriberNameRegistration;
import com.pinupcircle.ui.subscriberRegistration.SubscriberRegistration;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class OTPAuthenticaton extends AppCompatActivity {

    String tag_string_OTPVerifyobj = "json_string_OTPVerify_req";
    Button btnVerify;
    PinView otpPinSet;
    int userRegId;
    Context mContext;
    AppProgressDialog  appProgressVerifyDialog;
    OTPValidateModel otpValidateModel;
    JSONObject jsonObjectResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_authenticaton);
        mContext=OTPAuthenticaton.this;
        userRegId = CustomPreference.with(mContext).getInt(Constants.userId,0);
        initFields();
        setListener();

        otpPinSet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() >=6) {
                    btnVerify.setEnabled(true);
                    btnVerify.setBackgroundResource(R.drawable.otp_verify_active);
                } else {
                    btnVerify.setEnabled(false);
                    btnVerify.setBackgroundResource(R.drawable.otp_verify_in_active);
                }

            }
        });
    }

    private void setListener() {

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVerify.setEnabled(false);
                btnVerify.setBackgroundResource(R.drawable.otp_verify_in_active);
                // services call after all fields entered
                otpAuthenticationVerify();
               /* Intent mIntent=new Intent(OTPAuthenticaton.this, SubscriberRegistration.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                finish();*/
            }
        });
    }

    private void otpAuthenticationVerify() {

        //Toast.makeText(this, ""+ otpPinSet.getText().toString().trim(), Toast.LENGTH_SHORT).show();

        appProgressVerifyDialog = new AppProgressDialog(mContext, "Verify your mobile Number");
        appProgressVerifyDialog.initializeProgress();
        appProgressVerifyDialog.showProgressDialog();

        otpValidateModel.setUserRegId(userRegId);
        otpValidateModel.setUserOTP(otpPinSet.getText().toString().trim());
        Gson gson = new Gson();
        final String requestBody = gson.toJson(otpValidateModel);
        System.out.println("MobileOTPVerified" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                Constants.base_url + Constants.services_registerUserValidateOTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                appProgressVerifyDialog.hideProgressDialog();
                System.out.println("MobileOTPVERIFYResponse" + response);
                try {
                    jsonObjectResponse = new JSONObject(response);

                    int validationStatus = jsonObjectResponse.getInt("identityValidationStatus");

                    if (validationStatus == 1) {

                        new PrettyDialog(OTPAuthenticaton.this)
                                .setIcon(R.drawable.pdlg_icon_success)
                                .setIconTint(R.color.pdlg_color_green)
                                .setTitle("Success")
                                .setMessage(jsonObjectResponse.getString("description"))
                                .addButton(
                                        "OK",R.color.pdlg_color_white,
                                        R.color.pdlg_color_green,
                                        new PrettyDialogCallback() {
                                            @Override
                                            public void onClick() {
                                               Intent mIntent=new Intent(OTPAuthenticaton.this,SubscriberRegistration.class);
                                                startActivity(mIntent);
                                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                                                finish();
                                            }
                                        }
                                )
                                .show();
                    } else {

                        new PrettyDialog(OTPAuthenticaton.this)
                                .setIcon(R.drawable.pdlg_icon_close)
                                .setIconTint(R.color.color_dark_red)
                                .setTitle("Failure")
                                .setMessage(jsonObjectResponse.getString("description"))
                                .addButton(
                                        "OK",R.color.pdlg_color_white,
                                        R.color.pdlg_color_red,
                                        new PrettyDialogCallback() {
                                            @Override
                                            public void onClick() {

                                                Intent mIntent=new Intent(OTPAuthenticaton.this,MobileAuthentication.class);
                                                startActivity(mIntent);
                                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                                                finish();
                                            }
                                        }
                                )
                                .show();
                    }

                } catch (JSONException e) {
                    appProgressVerifyDialog.hideProgressDialog();
                    System.out.println("MobileOTPVERIFYWebServiceError" + e.getMessage());
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                appProgressVerifyDialog.hideProgressDialog();
                System.out.println("JsnObjetMobileOTPVERIFYErrorResponse" + error.toString());
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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest, tag_string_OTPVerifyobj);

    }

   private void initFields() {

       otpValidateModel = new OTPValidateModel();
        btnVerify = (Button) findViewById(R.id.btnVerify);
        otpPinSet = findViewById(R.id.otpVerify);



    }
}
