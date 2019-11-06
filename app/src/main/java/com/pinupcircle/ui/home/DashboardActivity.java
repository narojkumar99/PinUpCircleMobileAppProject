package com.pinupcircle.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pinupcircle.R;
import com.pinupcircle.ui.fragment.AccountFragment;
import com.pinupcircle.ui.fragment.BottomSheetFragment.BottomSheetFragment;
import com.pinupcircle.ui.fragment.HomeFragment;
import com.pinupcircle.ui.fragment.PostFragment;
import com.pinupcircle.ui.mobileOTPAuthentication.OTPAuthenticaton;
import com.pinupcircle.utils.Constants;
import com.pinupcircle.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static com.pinupcircle.R.drawable.gradient;

public class DashboardActivity extends AppCompatActivity implements
        View.OnClickListener, HomeFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener, PostFragment.OnFragmentInteractionListener {

    RelativeLayout relativeNewUpdate, relativeDiscover;
    RelativeLayout relativeInbox, relativeAccount;
    FragmentManager fManager;
    FragmentTransaction fTransaction;
    HomeFragment homeFragment;
    AccountFragment accountFragment;
    PostFragment postFragment;
    Context mContext;
    FloatingActionButton postAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = DashboardActivity.this;
        initFields();
        setListener();
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        postFragment = new PostFragment();
        openLandingFragment();
    }

    private void openLandingFragment() {
        HomeFragment newFragment = new HomeFragment();
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();
        fManager.addOnBackStackChangedListener(getListener());
        fTransaction
                .add(R.id.contentContainer, newFragment, Constants.TAG_FRAGMENT_HOME)
                .addToBackStack(Constants.TAG_FRAGMENT_HOME)
                .commit();
    }

    private int searchFragmentfromStack(String fragmentTag) {
        int pos = -1;
        for (int i = 0; i < fManager.getBackStackEntryCount(); ++i) {
            String name = fManager.getBackStackEntryAt(i).getName();
            Log.d("Fragment_Name", name);
            if (name.equalsIgnoreCase(fragmentTag)) {
                pos = i;
                break;
            }
//                fManager.popBackStack();
        }
        return pos;
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                Fragment currentFragment = fManager.findFragmentById(R.id.contentContainer);
                if (currentFragment != null)
                    currentFragment.onResume();
//                }
            }
        };
        return result;
    }

    private void setListener() {
        relativeNewUpdate.setOnClickListener(this);
        relativeDiscover.setOnClickListener(this);
        relativeInbox.setOnClickListener(this);
        relativeAccount.setOnClickListener(this);
        postAction.setOnClickListener(this);
    }

    private void initFields() {
        relativeNewUpdate = findViewById(R.id.relativeNewUpdate);
        relativeDiscover = findViewById(R.id.relativeDiscover);
        relativeInbox = findViewById(R.id.relativeInbox);
        relativeAccount = findViewById(R.id.relativeAccount);
        postAction = findViewById(R.id.postAction);
        //relativeNewUpdate.setSelected(true);
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
                        "OK", R.color.pdlg_color_white,
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeNewUpdate:
                Fragment frag = fManager.findFragmentById(R.id.contentContainer);
                if (!(frag instanceof HomeFragment)) {
                    fManager = getFragmentManager();
                    fTransaction = fManager.beginTransaction();
                    fManager.addOnBackStackChangedListener(getListener());
                    int pos = searchFragmentfromStack(Constants.TAG_FRAGMENT_HOME);
                    if (pos != -1) {//fragment found, need to pop  all fragments from stack from the current search position incrementally
                        removeFragmentsFromStack(pos + 1);//method for pop fragment from stack
                    } else {//fragment not in stack,add new
                        fTransaction
                                .replace(R.id.contentContainer, homeFragment, Constants.TAG_FRAGMENT_HOME)
                                .addToBackStack(Constants.TAG_FRAGMENT_HOME)
                                .commit();
                    }
                }
                break;
            case R.id.relativeDiscover:
                break;
            case R.id.relativeInbox:
                break;
            case R.id.relativeAccount:
                Fragment frag2 = fManager.findFragmentById(R.id.contentContainer);
                if (!(frag2 instanceof AccountFragment)) {
                    fManager = getFragmentManager();
                    fTransaction = fManager.beginTransaction();
                    fManager.addOnBackStackChangedListener(getListener());
                    int pos = searchFragmentfromStack(Constants.TAG_FRAGMENT_ACCOUNT);
                    if (pos != -1) {//fragment found, need to pop  all fragments from stack from the current search position incrementally
                        removeFragmentsFromStack(pos + 1);//method for pop fragment from stack
                    } else {//fragment not in stack,add new
                        fTransaction
                                .replace(R.id.contentContainer, accountFragment, Constants.TAG_FRAGMENT_ACCOUNT)
                                .addToBackStack(Constants.TAG_FRAGMENT_ACCOUNT)
                                .commit();
                    }
                }
                break;
            case R.id.postAction:
                //Toast.makeText(mContext, "Ok", Toast.LENGTH_SHORT).show();
                //BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
                //bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
                Fragment frag3 = fManager.findFragmentById(R.id.contentContainer);
                if (!(frag3 instanceof PostFragment)) {
                    fManager = getFragmentManager();
                    fTransaction = fManager.beginTransaction();
                    fManager.addOnBackStackChangedListener(getListener());
                    int pos = searchFragmentfromStack(Constants.TAG_FRAGMENT_POST);
                    if (pos != -1) {//fragment found, need to pop  all fragments from stack from the current search position incrementally
                        removeFragmentsFromStack(pos + 1);//method for pop fragment from stack
                    } else {//fragment not in stack,add new
                        fTransaction
                                .replace(R.id.contentContainer, postFragment, Constants.TAG_FRAGMENT_POST)
                                .addToBackStack(Constants.TAG_FRAGMENT_POST)
                                .commit();
                    }
                }
                break;
        }
    }

    private void removeFragmentsFromStack(int startPos) {
        for (int i = startPos; i < fManager.getBackStackEntryCount(); i++) {
            fManager.popBackStack();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
