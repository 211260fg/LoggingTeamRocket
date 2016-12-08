package com.hackthefuture.florianzjef.loggingapp.repo;


public interface OnResearcherConnectedListener {

    void onTokenReceived(String token);
    void onConnectionFailed(String message);
}
