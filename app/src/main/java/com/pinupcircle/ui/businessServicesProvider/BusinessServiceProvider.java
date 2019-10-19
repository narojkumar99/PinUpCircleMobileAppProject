package com.pinupcircle.ui.businessServicesProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.pinupcircle.R;
import com.pinupcircle.model.RegisteredUserModel;
import com.pinupcircle.model.ServiceProviderModel;
import com.pinupcircle.ui.subscriberRegistration.SubscriberRegistration;
import com.pinupcircle.utils.AppProgressDialog;

public class BusinessServiceProvider extends AppCompatActivity {
    //final String url = "http://13.59.60.142:8080/serviceprovider";
    private final int IMG_REQUEST=1;
    RegisteredUserModel user;
    ServiceProviderModel serviceProviderModel;
    ImageView imgServiceNext,uploadServiceImage;
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
        setContentView(R.layout.activity_services_provider_registration);
        mContext = BusinessServiceProvider.this;
        initValues();
    }

    private void initValues() {
        appProgressDialog = new AppProgressDialog(mContext, "Please wait..");
        user = new RegisteredUserModel(this);
        serviceProviderModel = new ServiceProviderModel();
        /*imgServiceNext = findViewById(R.id.imgServiceNext);
        uploadServiceImage = findViewById(R.id.serviceUploadImage);
        editTextServiceProviderName = findViewById(R.id.editTextServiceProviderName);
        editTextServiceProviderService = findViewById(R.id.editTextServiceProviderService);
        editTextServiceState = findViewById(R.id.editTextServiceState);
        editTextServiceCity = findViewById(R.id.editTextServiceCity);
        editTextServiceProviderMobileNumber = findViewById(R.id.editTextServiceProviderMobileNumber);
        editTextServicePinCode = findViewById(R.id.editTextServicePinCode);
        editTextServiceProviderAddress = findViewById(R.id.editTextServiceProviderAddress);
        editTextServiceProviderEmail = findViewById(R.id.editTextServiceProviderEmail);*/
    }

   /* public void onServiceRegistrationClick(View view) {
        if (validate()) {
            doBusinessServicesRegistration();
        } else {
            //startActivity(new Intent(SubscriberRegistration.this, BusinessServiceProvider.class));
        }
    }
*/
   /* private boolean validate() {

        Boolean validResp = true;

        if (editTextServiceProviderName.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, " Name cannot be blank ", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"First Name cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Business Name cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextServiceProviderName.requestFocus();
            editTextServiceProviderName.setText("");
            return validResp;
        } else if (editTextServiceProviderMobileNumber.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "Mobile Number cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Last Name cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Business Mobile Number cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextServiceProviderMobileNumber.requestFocus();
            editTextServiceProviderMobileNumber.setText("");
            return validResp;
        }
        *//*else if (!Constants.emailValidator(editTextServiceProviderEmail.getText().toString().trim())) {
            validResp = false;
            //Toast.makeText(this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Business Email cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextServiceProviderEmail.requestFocus();
            editTextServiceProviderEmail.setText("");
            return validResp;
        }
        *//*
        else if (editTextServiceProviderService.getText().toString().trim().isEmpty()) {
            validResp = false;
            //Toast.makeText(this, "PinCode cannot be blank", Toast.LENGTH_SHORT).show();
            //Constants.showAlert(this,"Email cannot be blank");
            Snackbar.make(findViewById(android.R.id.content), "Business PinCode cannot be blank", Snackbar.LENGTH_SHORT).show();
            editTextServiceProviderService.requestFocus();
            editTextServiceProviderService.setText("");
            return validResp;
        }
        return validResp;
    }
*/
   /* private void doBusinessServicesRegistration() {

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
                System.out.println("JsonObjectBusinessProviderSubscriberRegistrationErrorResponse" + error.toString());
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
            Uri path=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                uploadServiceImage.setImageBitmap(bitmap);
                serviceProviderModel.addUserWorkView(ImageUtil.convert(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent mIntent=new Intent(BusinessServiceProvider.this,SubscriberRegistration.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }
}



