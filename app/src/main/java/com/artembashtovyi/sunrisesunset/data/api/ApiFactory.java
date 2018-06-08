package com.artembashtovyi.sunrisesunset.data.api;

import android.support.annotation.NonNull;

import com.artembashtovyi.sunrisesunset.data.api.service.SunService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory for control instance of services
 */

public class ApiFactory {
    private static final String API_ENDPOINT = "https://api.sunrise-sunset.org/";

    private static volatile SunService sunService;

    // double-checked solution
    @NonNull
    public static SunService createSunService() {
        if (sunService == null) {
            synchronized (ApiFactory.class) {
                if (sunService == null) {
                    sunService = new Retrofit.Builder()
                            .baseUrl(API_ENDPOINT)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(buildOkHttpClient())
                            .build()
                            .create(SunService.class);
                }
            }
        }
        return sunService;
    }

    // single instance too
    private static OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12,TimeUnit.SECONDS)
                .addInterceptor(createLogInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor createLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

}
