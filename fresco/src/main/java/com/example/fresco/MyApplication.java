package com.example.fresco;

import android.app.Application;
import android.util.Log;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by zcm on 2016/2/3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("yy","onCreate");
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE); //eng版本显示所有的log

    }
}
