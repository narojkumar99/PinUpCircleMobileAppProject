package com.pinupcircle.ui.subscriberRegistration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.location.LocationListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.RegisteredUserModel;
import com.pinupcircle.model.UserSubscriberModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.businessServicesProvider.BusinessServiceProvider;
import com.pinupcircle.ui.home.DashboardActivity;
import com.pinupcircle.ui.launcher.SplashActivity;
import com.pinupcircle.ui.map.MapPlaceActivity;
import com.pinupcircle.ui.mobileOTPAuthentication.MobileAuthentication;
import com.pinupcircle.ui.mobileOTPAuthentication.OTPAuthenticaton;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.CheckPermission;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.ImageUtil;
import com.pinupcircle.utils.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.Result;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static com.pinupcircle.utils.Constants.trimMessage;

public class SubscriberRegistration extends AppCompatActivity implements LocationListener {

    String tag_string_obj = "json_string_req";
    //final String url = "http://13.59.60.142:8080/users/registerUser?userdef=abc";
    UserSubscriberModel subscriberModel;
    private final int IMG_REQUEST=1;
    ImageView imgNext,imgSubscriberUpload;
    EditText editTextSubscriberName,editTextMobileNumber,editTextPinCode,editTextSubscriberEmail,editTextNickName;
    String mobileNumber = "";
    JSONObject jsonObjectResponse = null;
    String json = null;
    Context mContext;
    AppProgressDialog appProgressDialog;
    RegisteredUserModel user;
    public final static int TAG_PERMISSION_LOCATION_CODE=1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    TextView  registerYourServices;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    Button btnGO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_registration);
        mContext = SubscriberRegistration.this;
        initFields();
        setListener();
    }

    private void setListener() {

       registerYourServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(SubscriberRegistration.this,BusinessServiceProvider.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                finish();

            }
        });

       //OnbtnSelectImage click event...
        imgSubscriberUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CAMERA))
                    {
                        ActivityCompat.requestPermissions((Activity) mContext,
                                new String[]{Manifest.permission.CAMERA}, 102);
                    } else {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, 102);
                    }
                    //return false;
                } else {
                    //return true;
                    selectImage();
                }

            }
        });

        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    doSubscriberRegistration();
                    /*Intent mIntent = new Intent(SubscriberRegistration.this,MapPlaceActivity.class);
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    finish();*/
                } else {
                }
            }
        });
    }

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SubscriberRegistration.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();

                imgSubscriberUpload.setImageBitmap(bitmap);
                subscriberModel.setUserPickRef(ImageUtil.convert(bitmap));
                subscriberModel.setUserPicName(imgPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                imgSubscriberUpload.setImageBitmap(bitmap);
                subscriberModel.setUserPickRef(ImageUtil.convert(bitmap));
                subscriberModel.setUserPicName(imgPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void initFields() {

        //imgNext                 = findViewById(R.id.imgNext);
        imgSubscriberUpload     = findViewById(R.id.imgSubscriberUpload);
        editTextSubscriberName  = findViewById(R.id.editTextSubscriberName);
        editTextNickName        = findViewById(R.id.editTextNickName);
        //editTextMobileNumber    = findViewById(R.id.editTextMobileNumber);
        //editTextPinCode         = findViewById(R.id.editTextPinCode);
        registerYourServices    = findViewById(R.id.registerYourService);
        btnGO                   = findViewById(R.id.btnGO);
        appProgressDialog = new AppProgressDialog(mContext, "Please wait..");
        subscriberModel   = new UserSubscriberModel();
        user              = new RegisteredUserModel(this);
    }

    private void doSubscriberRegistration() {
        appProgressDialog.initializeProgress();
        appProgressDialog.showProgressDialog();
        subscriberModel.setUserName(editTextSubscriberName.getText().toString().trim());
        subscriberModel.setUserNickName(editTextNickName.getText().toString().trim());
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
                       /* Intent mIntent=new Intent(SubscriberRegistration.this,DashboardActivity.class);
                        startActivity(mIntent);
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                        finish();*/
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
                appProgressDialog.hideProgressDialog();
                System.out.println("JsonObjectSubscriberRegistrationErrorResponse" + error.toString());

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

            Snackbar.make(findViewById(android.R.id.content), "Enter Your Name", Snackbar.LENGTH_SHORT).show();
            editTextSubscriberName.requestFocus();
            editTextSubscriberName.setText("");
            hideKeyboard();
            return validResp;
        }
        else if (editTextNickName.getText().toString().trim().isEmpty()) {
            validResp = false;
            Snackbar.make(findViewById(android.R.id.content), "Enter Your Nick Name", Snackbar.LENGTH_SHORT).show();
            editTextNickName.requestFocus();
            editTextNickName.setText("");
            hideKeyboard();
            return validResp;
        }
        return validResp;
    }

    public void clearEditText() {
        editTextSubscriberName.setText("");
        editTextNickName.setText("");

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("title_location_permission")
                        .setMessage("text_location_permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SubscriberRegistration.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mContext, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onLocationChanged(Location location) {

    }
}
