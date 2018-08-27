package com.sivsivsree.reatimeandroid;

import android.app.Application;

import com.parse.Parse;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(
                new Parse.Configuration.Builder(this)
                        .applicationId("APPLICATION_ID")
                        .server("http://192.168.47.153:1337/parse/")
                        .enableLocalDataStore()
                        .build()
        );
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
    }
}
