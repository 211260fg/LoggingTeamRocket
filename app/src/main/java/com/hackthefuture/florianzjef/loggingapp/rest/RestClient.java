package com.hackthefuture.florianzjef.loggingapp.rest;

import android.util.Base64;
import android.util.Log;

import com.hackthefuture.florianzjef.loggingapp.models.Photo;
import com.hackthefuture.florianzjef.loggingapp.models.Researcher;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.models.SampleWrapper;
import com.hackthefuture.florianzjef.loggingapp.models.Token;
import com.hackthefuture.florianzjef.loggingapp.session.UserSessionManager;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Collections;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public class RestClient {

    private Retrofit client;

    public RestClient(String token){
        client = createClient(token);
    }

    public RestClient(){
        String token = UserSessionManager.getToken();

        client = createClient(token);
    }

    private Retrofit createClient(final String token){

        OkHttpClient okClient = new OkHttpClient();

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                final String basic = token;

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

        @GET(Values.URL_SAMPLES_ALL)
        Call<SampleWrapper> getAllSamples();

        @GET(Values.URL_PHOTOS)
        Call<SampleWrapper> getPhotos();

        @GET(Values.URL_PHOTOS_ALL)
        Call<SampleWrapper> getAllPhotos();

        @POST(Values.URL_SAMPLES_POST)
        Call<ResponseBody> postSample(@Body Sample sample);

        @POST(Values.URL_PHOTOS_POST)
        Call<ResponseBody> postPhoto(@Body Photo photo);

    }

    interface ResearcherApiInterface{

        @POST(Values.URL_RESEARCHER_REGISTER)
        Call<Token> register(@Body Researcher researcher);

        @POST(Values.URL_RESEARCHER_GETTOKEN)
        Call<Token> getToken(@Body Researcher researcher);

    }

}
