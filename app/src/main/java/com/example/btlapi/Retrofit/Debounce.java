package com.example.btlapi.Retrofit;

import android.os.Handler;
import android.os.Looper;

public class Debounce {
    private final long delayMillis;
    private final Runnable runnable;
    private final Handler handler;

    public Debounce(long delayMillis, Runnable runnable) {
        this.delayMillis = delayMillis;
        this.runnable = runnable;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void run() {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(runnable, delayMillis);
    }
}
