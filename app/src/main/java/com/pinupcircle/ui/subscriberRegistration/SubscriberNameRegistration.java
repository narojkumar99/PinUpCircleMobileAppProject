package com.pinupcircle.ui.subscriberRegistration;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SubscriberNameRegistration extends AppCompatActivity {
    String json = null;
    JSONObject jsonObjectResponse;
    EditText editTextName;
    EditText enterPinCode;
    String tag_string_obj = "json_string_req";
    UserSubscriberModel userSubscriberModel;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = SubscriberNameRegistration.this;
        initFields();
    }

    private void initFields() {
        userSubscriberModel = new UserSubscriberModel();
        editTextName = findViewById(R.id.editTextName);

    }

    public void onMobileAuthenticationClickName(View view) {
        userSubscriberModel.setUserName(editTextName.getText().toString().trim());
        Gson gson = new Gson();
        final String requestBody = gson.toJson(userSubscriberModel);
        System.out.println("SubscriberNameRegistration" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.sub_registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("SubscriberNameRegistrationResponse" + response);
                try {
                    jsonObjectResponse = new JSONObject(response);
                    int statusCode = jsonObjectResponse.getInt("registrationStatus");
                    if (statusCode == 1) {
                        //appProgressDialog.hideProgressDialog();
                        /*Toast.makeText(MobileAuthentication.this, ""
                                        + jsonObjectResponse.getString("description")
                                , Toast.LENGTH_SHORT).show();*/
//                        Snackbar.make(findViewById(android.R.id.content), jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
//                        clearEditText();
//                        Intent i = new Intent(mContext, OTPAuthenticaton.class);
//                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
//                        startActivity(i);
//                        finish();
                    } else {
                        //appProgressDialog.hideProgressDialog();
//                        Snackbar.make(findViewById(android.R.id.content),jsonObjectResponse.getString("description"), Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //appProgressDialog.hideProgressDialog();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                //appProgressDialog.hideProgressDialog();
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = Constants.trimMessage(json, "message");
                            System.out.println("testAD" + json);
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,tag_string_obj);

    }

    private void doMobileAuthentication() {

    }

    private void clearEditText() {

    }

}
