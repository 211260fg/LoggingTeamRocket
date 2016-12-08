package com.hackthefuture.florianzjef.loggingapp.repo;

import com.hackthefuture.florianzjef.loggingapp.fragments.SamplesFragment;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.rest.SamplesCallback;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static SamplesCallback samplesCallback = new SamplesCallback();

    private static List<Sample> samples = new ArrayList<>();
    private static List<OnSamplesLoadedListener> samplesListeners = new ArrayList<>();


    public static List<Sample> getSamples() {
        return samples;
    }

    public static void loadSamples(){
        samplesCallback.startCallback();
    }


    public static void addListener(OnSamplesLoadedListener listener){
        if(!samplesListeners.contains(listener))
            samplesListeners.add(listener);
    }

    public static void removeListener(OnSamplesLoadedListener listener) {
        if(samplesListeners.contains(listener))
            samplesListeners.remove(listener);
    }


    public static void onSamplesLoaded(List<Sample> samples) {
        Repository.samples = samples;
        for(OnSamplesLoadedListener listener: samplesListeners){
            listener.onSamplesLoadSuccess(samples);
        }
    }

    public static void onSampleLoadFailed(String message) {
        for(OnSamplesLoadedListener listener: samplesListeners){
            listener.onSamplesLoadFailed(message);
        }
    }
}
