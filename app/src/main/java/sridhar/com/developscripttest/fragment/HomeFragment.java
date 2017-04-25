package sridhar.com.developscripttest.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import sridhar.com.developscripttest.R;

/**
 * Created by SriMaddy on 4/25/2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // UI
    private Button btnLogin;
    private Button btnSignup;

    private Activity mActivity;

    private CallbackManager callbackManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        callbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViewsById(view);
        setClickListener();
        registerFacebookListener();

        return view;
    }

    private void findViewsById(View view) {
        btnLogin = (Button) view.findViewById(R.id.login_btn);
        btnSignup = (Button) view.findViewById(R.id.signup_btn);
    }

    private void setClickListener() {
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    private void registerFacebookListener() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i("FBLogin", "Success");
                        moveToStepMainFragment();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.w("FBLogin", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("FBLogin", "Error " + exception);
                    }
                });
    }

    private void moveToStepMainFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.container, new StepMainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn) {
            doLogin();
        } else if(v.getId() == R.id.signup_btn) {
            doSignup();
        }
    }

    private void doLogin() {
        Profile profile = Profile.getCurrentProfile();
        if(profile != null) {
            moveToStepMainFragment();
        } else {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }
    }

    private void doSignup() {
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
