package com.hackthefuture.florianzjef.loggingapp.fragments;


public interface OnFragmentInteractionListener {

    void onFragmentInteraction(InteractedFragment interactedFragment, int pos);
        enum InteractedFragment{
            LOGS, NEWLOG, AUTHENTICATION, LOGDETAILS
        }
}
