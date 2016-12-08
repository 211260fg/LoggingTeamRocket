package com.hackthefuture.florianzjef.loggingapp.rest;

import android.util.Log;

import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.models.SampleWrapper;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class SamplesCallback implements Callback<SampleWrapper> {


    public void getAllSamples(){
        RestClient restClient = new RestClient("", "");
        RestClient.SampleApiInterface service = restClient.getClient().create(RestClient.SampleApiInterface.class);
        Call<SampleWrapper> sampleCall = service.getAllSamples();
        sampleCall.enqueue(this);
    }

    public void getResearcherSamples(){
        RestClient restClient = new RestClient("", "");
        RestClient.SampleApiInterface service = restClient.getClient().create(RestClient.SampleApiInterface.class);
        Call<SampleWrapper> sampleCall = service.getSamples();
        sampleCall.enqueue(this);
    }

    public void postSample(Sample sample){
        RestClient restClient = new RestClient("","");
        RestClient.SampleApiInterface service = restClient.getClient().create(RestClient.SampleApiInterface.class);
        Call<Sample> sampleCall = service.postSample(sample);
        sampleCall.enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Response<Sample> response) {
                if(response.isSuccess()){
                    Repository.onSamplePosted(response.body());
                }
                else{
                    try {
                        Log.d("sample response", "fail - "+response.errorBody().string());
                        Repository.onSampleLoadFailed("Error posting sample");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onResponse(Response<SampleWrapper> response) {
        if(response.isSuccess()){
            Repository.onSamplesLoaded(response.body().getItems());
        }
        else{
            try {
                Log.d("sample response", "fail - "+response.errorBody().string());
                Repository.onSampleLoadFailed("Error loading samples");
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
        Log.d("sample load failure", sw.toString());

    }
}
