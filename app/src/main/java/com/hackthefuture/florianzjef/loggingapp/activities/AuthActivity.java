package com.hackthefuture.florianzjef.loggingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.fragments.LoginFragment;
import com.hackthefuture.florianzjef.loggingapp.fragments.OnFragmentInteractionListener;
import com.hackthefuture.florianzjef.loggingapp.session.UserSessionManager;


public class AuthActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private LoginFragment loginFragment;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Intent i = getIntent();
        UserSessionManager sessionfromintent = i.getParcelableExtra("SESSION");
        if(sessionfromintent!=null){
            session = sessionfromintent;
        }else{
            session = new UserSessionManager(getApplicationContext());
        }
            loginFragment = LoginFragment.newInstance(session);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentPane, loginFragment).commit();

    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment interactedFragment, int pos) {
        /*email = "";
        password = "";
        confirm_password = "";
        //Als loginfragment getoond wordt moeten we het registreerfragment tonen
        if(loginFragment!=null&&loginFragment.isVisible()){
            registerFragment = RegisterFragment.newInstance(session);

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.fragmentPane, registerFragment).commit();

        }else{ //anders moeten we het loginfragment tonen

            loginFragment = LoginFragment.newInstance(session);

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragmentPane, loginFragment).commit();

        }*/
    }
}
