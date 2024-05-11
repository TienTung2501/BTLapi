package com.example.btlapi.Retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    private static final int MAX_RETRIES = 3; // Maximum number of retries
    private static final int RETRY_DELAY_MS = 1000; // Delay between retries in milliseconds

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        IOException exception = null;

        // Retry logic
        for (int retryCount = 0; retryCount < MAX_RETRIES; retryCount++) {
            try {
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
            } catch (IOException e) {
                exception = e;
            }
            // Wait before retrying
            try {
                Thread.sleep(RETRY_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return response;
            }
        }

        // If all retries fail, throw the last exception
        if (exception != null) {
            throw exception;
        }

        return response;
    }
}