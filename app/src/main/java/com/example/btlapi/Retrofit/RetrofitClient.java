package com.example.btlapi.Retrofit;

import com.example.btlapi.Utils.GsonFactory;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.0.103:333";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Add retry interceptor
            httpClient.addInterceptor(new RetryInterceptor());
            OkHttpClient client = httpClient.build();

            // Create Gson instance with custom date adapter
            Gson gson = GsonFactory.createGson();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Use custom Gson
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
