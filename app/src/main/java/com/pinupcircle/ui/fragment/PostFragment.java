package com.pinupcircle.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pinupcircle.R;
import com.pinupcircle.ui.fragment.PostSubFragment.GeneralPost;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PostFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    Context mContext;
    View view;
    PostFragment postFragment;
    public static RelativeLayout relativeLayoutPost;
    public PostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        postFragment = this;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        // get the reference of All Views
        initViews();
        // perform setOnClickListener event on all views
        setListener();

        return view;
    }

    private void setListener() {
        // perform setOnClickListener event on relativeLayoutPost
        relativeLayoutPost.setOnClickListener(this);

    }

    private void initViews() {
        relativeLayoutPost = view.findViewById(R.id.relativePost);

        relativeLayoutPost.setVisibility(View.VISIBLE);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativePost:
                relativeLayoutPost.setVisibility(View.GONE);
                //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                // load First Fragment
                loadFragment(new GeneralPost());
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayoutGeneralPost, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
