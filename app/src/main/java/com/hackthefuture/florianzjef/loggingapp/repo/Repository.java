package com.hackthefuture.florianzjef.loggingapp.repo;

import com.hackthefuture.florianzjef.loggingapp.models.Researcher;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.rest.ResearcherCallback;
import com.hackthefuture.florianzjef.loggingapp.rest.SamplesCallback;
import com.hackthefuture.florianzjef.loggingapp.session.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static SamplesCallback samplesCallback = new SamplesCallback();
    private static ResearcherCallback researcherCallback = new ResearcherCallback();

    private static List<Sample> samples = new ArrayList<>();
    private static List<OnSamplesLoadedListener> samplesListeners = new ArrayList<>();
    private static List<OnResearcherConnectedListener> researcherListeners = new ArrayList<>();


    public static List<Sample> getSamples() {
        return samples;
    }

    public static void loadAllSamples(){
        samplesCallback.getAllSamples();
    }
    public static void loadResearcherSamples(){
        samplesCallback.getResearcherSamples();
    }


    public static void addListener(OnSamplesLoadedListener listener){
        if(!samplesListeners.contains(listener))
            samplesListeners.add(listener);
    }

    public static void removeListener(OnSamplesLoadedListener listener) {
        if(samplesListeners.contains(listener))
            samplesListeners.remove(listener);
    }

    public static void addListener(OnResearcherConnectedListener listener){
        if(!researcherListeners.contains(listener))
            researcherListeners.add(listener);
    }

    public static void removeListener(OnResearcherConnectedListener listener) {
        if(researcherListeners.contains(listener))
            researcherListeners.remove(listener);
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

    public static void loginResearcher(Researcher researcher){
        researcherCallback.getToken(researcher);
    }

    public static void registerResearcher(Researcher researcher){
        researcherCallback.register(researcher);
    }

    public static void logoutResearcher(){
        UserSessionManager.logoutUser();
    }

    public static void onResearcherTokenResponse(String token) {
        for(OnResearcherConnectedListener listener: researcherListeners){
            listener.onTokenReceived(token);
        }
    }

    public static void onResearcherConnectionFailed(String message){
        for(OnResearcherConnectedListener listener: researcherListeners){
            listener.onConnectionFailed(message);
        }
    }
}
