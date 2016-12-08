package com.hackthefuture.florianzjef.loggingapp.rest;

import android.util.Base64;
import android.util.Log;

import com.hackthefuture.florianzjef.loggingapp.models.SampleWrapper;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Collections;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;

public class RestClient {

    private Retrofit client;

    public RestClient(String email, String password){
        client = createClient(email, password);
    }

    /*public RestClient(){
        HashMap<String, String> user = UserSessionManager.getUserDetails();
        String email = user.get(UserSessionManager.KEY_NAME);
        String password = user.get(UserSessionManager.KEY_PASSWORD);

        client = createClient(email, password);
    }*/

    private Retrofit createClient(final String email, final String password){

        OkHttpClient okClient = new OkHttpClient();

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                final String credentials = email + ":" + password;
                final String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                //final String basic = "Basic " + encodedCredentials;
                final String basic = "3aea67f6e2d54e2aa1e0495ea8bad4a9";

                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("Authorization", basic)
                        .build();
                Response response = chain.proceed(request);

                Log.w("Retrofit@Response", response.message());

                return response;
            }
        });

        okClient.setProtocols(Collections.singletonList(Protocol.HTTP_1_1));

        String baseurl = Values.BASE_URL;
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverter(String.class, new ToStringConverter())
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getClient() {
        return client;
    }

    interface SampleApiInterface{

        @GET(Values.URL_SAMPLES)
        Call<SampleWrapper> getSamples();

    }

}
