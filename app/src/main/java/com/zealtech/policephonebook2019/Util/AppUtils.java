package com.zealtech.policephonebook2019.Util;


import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppUtils {

    public static OkHttpClient okHttpClient;

    public static Api getApiService() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
        }

        Retrofit client = new Retrofit.Builder()
                .baseUrl(ApplicationConfig.getApiBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return client.create(Api.class);
    }

}
