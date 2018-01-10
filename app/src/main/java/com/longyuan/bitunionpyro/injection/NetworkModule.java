package com.longyuan.bitunionpyro.injection;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longyuan.bitunionpyro.api.BUService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by loxu on 10/01/2018.
 */

@Module
public class NetworkModule {

    String mBaseUrl;


    Context mContext;

    public NetworkModule(String mBaseUrl,Context context) {
        this.mBaseUrl = mBaseUrl;
        this.mContext = context;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public BUService provideBuService(Retrofit retrofit) {
        return retrofit.create(BUService.class);
    }

    @Provides
    @Singleton
    public RequestQueue provideRequestQueue() {
        return Volley.newRequestQueue(mContext);
    }
}
