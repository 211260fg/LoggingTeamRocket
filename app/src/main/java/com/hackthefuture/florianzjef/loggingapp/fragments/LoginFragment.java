package com.hackthefuture.florianzjef.loggingapp.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.AuthActivity;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Researcher;
import com.hackthefuture.florianzjef.loggingapp.repo.OnResearcherConnectedListener;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;
import com.hackthefuture.florianzjef.loggingapp.session.UserSessionManager;


public class LoginFragment extends Fragment implements OnResearcherConnectedListener {


    private EditText input_email;
    private EditText input_password;
    private Button btnLogin;
    private TextView link_signup;

    private ProgressDialog progressDialog;

    private View rootView;

    private OnFragmentInteractionListener mListener;

    private UserSessionManager session;


    public static LoginFragment newInstance(UserSessionManager session) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("SESSION", session);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = (UserSessionManager) getArguments().getSerializable(
                "SESSION");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);


        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        link_signup = (TextView) rootView.findViewById(R.id.link_signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Repository.addListener(this);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Repository.removeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    private void login(){
        if(!validateFields()){
            return;
        }

        //Repository.loginUser(input_email.getText().toString(), input_password.getText().toString());
        Repository.loginResearcher(new Researcher(input_email.getText().toString(), input_password.getText().toString()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Laden...");
        progressDialog.show();
    }

    private void signup(){
        mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.AUTHENTICATION, 0);
    }


    /*@Override
    public void onLoginSuccess(User user){

        if(progressDialog!=null)
            progressDialog.dismiss();

        session.createUserLoginSession(input_email.getText().toString(), input_password.getText().toString());
        session.saveCurrentUser(user);

        Intent i = new Intent(getActivity(), MembersActivity.class);
        i.putExtra("SESSION", (Parcelable) session);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed(String message){

        if(progressDialog!=null)
            progressDialog.dismiss();

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }*/


    private boolean validateFields() {
        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty()) {
            input_email.setError("error");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty()) {
            input_password.setError("error");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }

    @Override
    public void onTokenReceived(String token) {
        if(progressDialog!=null)
            progressDialog.dismiss();

        session.createUserLoginSession(token);
        session.saveCurrentUser(new Researcher(input_email.getText().toString(), input_password.getText().toString()));

        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("SESSION", (Parcelable) session);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onConnectionFailed(String message) {
        if(progressDialog!=null)
            progressDialog.dismiss();

        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
