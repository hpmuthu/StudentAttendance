package com.example.vittal.studentattendance.api;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import org.json.JSONObject;


public class ActivityErrorListener implements ErrorListener {

    private ActivityNetworkListener activityReference;
    private String requestTag;

    public ActivityErrorListener(ActivityNetworkListener context, String tag) {
        this.activityReference = context;
        this.requestTag = tag;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            JSONObject jsonObject = new JSONObject();
            String responseBody = new String(error.networkResponse.data, "utf-8");
            if (responseBody != null) {
                jsonObject = new JSONObject(responseBody);
            }

            if (activityReference != null) {
                activityReference.onError(jsonObject, requestTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
