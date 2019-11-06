package com.pinupcircle.ui.fragment.PostSubFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.pinupcircle.R;
import com.pinupcircle.model.CommunityPostTextModel;
import com.pinupcircle.networkutilts.VolleySingleton;
import com.pinupcircle.ui.fragment.PostFragment;
import com.pinupcircle.ui.home.DashboardActivity;
import com.pinupcircle.utils.AppProgressDialog;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class GeneralPost extends Fragment implements View.OnClickListener {
    View view;
    TextView textView;
    GeneralPost generalPost;
    ImageView imgViewCross;
    EditText editTextGeneralPost;
    Context mContext;
    ImageView sendPost;
    String tag_string_obj = "json_string_req";
    private CommunityPostTextModel communityPostTextModel;
    AppProgressDialog appProgressDialogOtpSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((DashboardActivity) getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mContext = getActivity();
        generalPost = this;
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_general_post, container, false);
// get the reference of Button
        initViews();
        // perform setOnClickListener event on all views
        setListener();
        return view;
    }

    private void setListener() {
        textView.setOnClickListener(this);
        imgViewCross.setOnClickListener(this);
        sendPost.setOnClickListener(this);
    }

    private void initViews() {
        textView = view.findViewById(R.id.generalPost);
        imgViewCross = view.findViewById(R.id.imgViewCross);
        sendPost = view.findViewById(R.id.sendPost);
        editTextGeneralPost = view.findViewById(R.id.generalPost);
        communityPostTextModel = new CommunityPostTextModel();
        appProgressDialogOtpSend = new AppProgressDialog(getActivity(), "wait while your post");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgViewCross:
                PostFragment.relativeLayoutPost.setVisibility(View.VISIBLE);
                getActivity().getFragmentManager().beginTransaction().remove(generalPost).commit();
                break;
            case R.id.sendPost:
                String communityPost = editTextGeneralPost.getText().toString().trim();
                communityTextPostRequest(communityPost);
                break;
        }
    }

    private void communityTextPostRequest(String communityPost) {
        //appProgressDialogOtpSend.showProgressDialog();
        communityPostTextModel.setPostText(communityPost);
        communityPostTextModel.setUserId(3);
        communityPostTextModel.addCommunityPin("700120");
        Gson gson = new Gson();
        final String requestBody = gson.toJson(communityPostTextModel);
        System.out.println("atagcommunitypost" + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.base_url + Constants.communipost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("CommunityPostTextResponse" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("JsonObjectCommunityPostErrorResponse" + error.toString());

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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest, tag_string_obj);
    }

}
