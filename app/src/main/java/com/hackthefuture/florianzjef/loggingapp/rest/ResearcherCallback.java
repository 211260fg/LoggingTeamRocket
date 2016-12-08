package com.hackthefuture.florianzjef.loggingapp.rest;

import android.util.Log;

import com.hackthefuture.florianzjef.loggingapp.models.Researcher;
import com.hackthefuture.florianzjef.loggingapp.models.SampleWrapper;
import com.hackthefuture.florianzjef.loggingapp.models.Token;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class ResearcherCallback implements Callback<Token> {

    public void register(Researcher researcher) {
        RestClient restClient = new RestClient("", "");
        RestClient.ResearcherApiInterface service = restClient.getClient().create(RestClient.ResearcherApiInterface.class);
        Call<Token> sampleCall = service.register(researcher);
        sampleCall.enqueue(this);
    }

    public void getToken(Researcher researcher) {
        RestClient restClient = new RestClient("", "");
        RestClient.ResearcherApiInterface service = restClient.getClient().create(RestClient.ResearcherApiInterface.class);
        Call<Token> sampleCall = service.getToken(researcher);
        sampleCall.enqueue(this);
    }


    @Override
    public void onResponse(Response<Token> response) {
        if (response.isSuccess()) {
            Repository.onResearcherTokenResponse(response.body().getToken());
        } else {
            try {
                Log.d("researcher response", "fail - " + response.errorBody().string());
                Repository.onResearcherConnectionFailed(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFailure(Throwable t) {
        Repository.onSampleLoadFailed("No connection to server");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        Log.d("researcher load failure", sw.toString());

    }
}