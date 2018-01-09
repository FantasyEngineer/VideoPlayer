package com.example.administrator.videoplayer;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2018/1/9.
 */

public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
