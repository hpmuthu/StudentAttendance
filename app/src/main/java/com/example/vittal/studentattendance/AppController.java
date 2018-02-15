package com.example.vittal.studentattendance;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by user on 12/28/2017.
 */

public class AppController extends Application {

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //FlowManager.init(this);
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        FlowManager.destroy();
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
