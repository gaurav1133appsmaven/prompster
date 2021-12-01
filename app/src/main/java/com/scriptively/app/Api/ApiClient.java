package com.scriptively.app.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {
//test url   http://18.208.104.63/
//client base url = "http://18.211.81.17/";

//    http://3.22.110.46/promster/index.php/

//    http://139.59.61.222/prompster/index.php/
    public static final String BASE_URL ="http://3.22.110.46/promster/index.php/";

    private static Retrofit retrofit = null;



    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getClient() {
        if (retrofit==null) {

            OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                    .connectTimeout(7000, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)

                    .build();
        }
        return retrofit;
    }
}
