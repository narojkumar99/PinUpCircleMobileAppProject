package com.pinupcircle.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppProgressDialog {
    Context ctx;
    //Progress Dialog
    ProgressDialog pDialog;
    String msg;

    //context ,
    // msg = for mg
    public AppProgressDialog(Context ctx, String msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    public void initializeProgress() {
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    //Checking Internet connection
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
