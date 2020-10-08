package com.test.stripe_integration.api;

import android.content.Context;


import androidx.viewbinding.BuildConfig;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private HashMap<String, String> commonHeadersMap;

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Retrofit getRestClient(Context context) {
        return getRestClient(context, false);
    }

    public Retrofit getRestClient(Context context, boolean isFormData) {
        return new Retrofit.Builder()
                .baseUrl("https://ass-hole-app-example.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(context))
                .build();
    }

    private OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        ApiErrorInterceptor apiInterceptor = new ApiErrorInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        httpClient.addInterceptor(apiInterceptor); //need to be the first(Very Important)
        httpClient.addInterceptor(interceptor); //need to be the last(Very Important)

        return httpClient
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
    }
}
