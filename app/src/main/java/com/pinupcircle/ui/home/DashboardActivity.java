package com.pinupcircle.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pinupcircle.R;
import com.pinupcircle.ui.mobileOTPAuthentication.OTPAuthenticaton;
import com.pinupcircle.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static com.pinupcircle.R.drawable.gradient;

public class DashboardActivity extends AppCompatActivity {

    Spinner spinnerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.setStatusBarGradiant(DashboardActivity.this);
        setContentView(R.layout.activity_home);

        initFields();

      /*  List<String> list = new ArrayList<String>();
        list.add("New Town");
        list.add("Kolkata");
        list.add("Kolkata");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DashboardActivity.this, R.layout.item_spinner_location, R.id.spinner_text_location, list);
        spinnerLocation.setAdapter(dataAdapter);*/

    }

    private void initFields() {

        /*spinnerLocation = findViewById(R.id.spinnerLocation);*/
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new PrettyDialog(DashboardActivity.this)
                .setIcon(R.drawable.logo)
                .setTitle("PinUp Circle")
                .setAnimationEnabled(true)
                .setMessage(String.valueOf(Html.fromHtml("Do u want to Exit This Application?")))
                .addButton(
                        "OK",R.color.pdlg_color_white,
                         R.color.quantum_googblue300,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                              finish();

                            }
                        }
                )

                .show();



    }
}
