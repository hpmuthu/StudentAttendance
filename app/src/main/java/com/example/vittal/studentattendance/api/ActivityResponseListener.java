package com.example.vittal.studentattendance.api;

import android.util.Log;

import com.android.volley.Response;

public class ActivityResponseListener<T> implements Response.Listener<T> {

    private ActivityNetworkListener activityReference;
    private String requestTag;

    public ActivityResponseListener(ActivityNetworkListener rhelper, String tag) {
        this.activityReference = rhelper;
        this.requestTag = tag;
    }

    @Override
    public void onResponse(T result) {
        Log.e("" + requestTag, "" + result.toString());
        if (activityReference != null)
            activityReference.onResponse(result, requestTag);
    }
}
