package com.pinupcircle.ui.subscriberRegistration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.pinupcircle.model.RegisteredUserModel;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.businessServicesProvider.BusinessServiceProvider;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SubscriberRegistration extends AppCompatActivity {

    String tag_string_obj = "json_string_req";
    //final String url = "http://13.59.60.142:8080/users/registerUser?userdef=abc";
    UserSubscriberModel subscriberModel;
    private final int IMG_REQUEST=1;
    ImageView imgNext,imgSubscriberUpload;
    EditText editTextSubscriberName,editTextMobileNumber,editTextPinCode,editTextSubscriberEmail;
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
        mContext = SubscriberRegistration.this;
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
        appProgressDialog = new AppProgressDialog(mContext, "Please wait..");
        subscriberModel = new UserSubscriberModel();
        user = new RegisteredUserModel(this);
        imgNext = findViewById(R.id.imgNext);
        imgSubscriberUpload=findViewById(R.id.imgSubscriberUpload);
        editTextSubscriberName = findViewById(R.id.editTextSubscriberName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPinCode = findViewById(R.id.editTextPinCode);
        editTextSubscriberEmail = findViewById(R.id.editTextSubscriberEmail);
    }

    public void onLoginClick(View view) {
        if (validate()) {
            user.logOut();
            doSubscriberRegistration();
        } else {
            //startActivity(new Intent(SubscriberRegistration.this, BusinessServiceProvider.class));
        }
    }

    private void doSubscriberRegistration() {
        appProgressDialog.initializeProgress();
        appProgressDialog.showProgressDialog();
        subscriberModel.setUserName(editTextSubscriberName.getText().toString().trim());
        subscriberModel.setUserPhone(Long.valueOf(editTextMobileNumber.getText().toString()));
        subscriberModel.setUserEmail(editTextSubscriberEmail.getText().toString().trim());
        subscriberModel.setUserCountryCode("IN");
        subscriberModel.addUserPins(Integer.parseInt(editTextPinCode.getText().toString().trim()));
        Gson gson = new Gson();
        final String requestBody = gson.toJson(subscriberModel);
        System.out.println("SubscriberRegistration" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.registerUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("SubscriberRegistrationResponse" + response);
                try {
                    jsonObjectResponse = new JSONObject(response);
                    int statusCode = jsonObjectResponse.getInt("registrationStatus");
                    user.setUserRegId(jsonObjectResponse.getInt("userRegId"));
                    user.setUserName(jsonObjectResponse.getString("userName"));
                    if (statusCode == 1) {
                        appProgressDialog.hideProgressDialog();
                        Toast.makeText(SubscriberRegistration.this, ""
                                        + jsonObjectResponse.getString("description")
                                , Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                        clearEditText();
                        startActivity(new Intent(SubscriberRegistration.this, BusinessServiceProvider.class));
                    } else {
                        appProgressDialog.hideProgressDialog();
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    System.out.println("SubscriberRegistrationWebServiceError" + e.getMessage());
                    e.printStackTrace();
                    appProgressDialog.hideProgressDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("JsonObjectSubscriberRegistrationErrorResponse" + error.toString());
                appProgressDialog.hideProgressDialog();
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
        } else if (!Constants.emailValidator(editTextSubscriberEmail.getText().toString().trim())) {
            validResp = false;
            //Toast.makeText(this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Email cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextSubscriberEmail.requestFocus();
            editTextSubscriberEmail.setText("");
            return validResp;
        }  else if (editTextPinCode.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "PinCode cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "PinCode cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextPinCode.requestFocus();
            editTextPinCode.setText("");
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
        editTextPinCode.setText("");
        editTextMobileNumber.setText("");
        editTextSubscriberEmail.setText("");
    }
    public void onAddImageClick(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri file=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),file);
                imgSubscriberUpload.setImageBitmap(bitmap);
                subscriberModel.setUserPickRef(ImageUtil.convert(bitmap));
                subscriberModel.setUserPicName(file.getLastPathSegment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
