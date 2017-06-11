package com.propertyguru.hackernews.hackernews.ui;

import android.app.Application;

/**
 * Created by Uba
 */

public class HackerNewsApplication extends Application {

    private static HackerNewsApplication sInstance;

    public static HackerNewsApplication getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
