package com.pinupcircle.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {

    //http://13.59.60.142:8080/users/registerUser?userdef=abc"
    public static String base_url = "http://13.59.60.142:8080/";
    public static String sub_registration="users/registerUser?userdef=abc";

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    //Somewhere that has access to a context
    public static void displayMessage(Context mContext, String toastString){
        Toast.makeText(mContext, toastString, Toast.LENGTH_LONG).show();
    }

}