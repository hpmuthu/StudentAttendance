package com.example.vittal.studentattendance.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.vittal.studentattendance.utils.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for communicating with the server using api call
 */

public class ApiService {

    Context context;
    private RequestManager requestSession;
    private static int SOCKET_TIMEOUT = 20000;
    private ActivityNetworkListener nListener;
    private String tagName = ApiService.class.getSimpleName();
    private HashMap<String, String> params;

    /**
     * Constructor with class specified tag name
     **/

    public ApiService(Context context, String tagName) {
        this.context = context;
        this.tagName = tagName;
        this.nListener = (ActivityNetworkListener) context;
        initialize();
    }

    /**
     * Initialize the RequestManager instance
     */

    public void initialize() {
        requestSession = RequestManager.getInstance(context.getApplicationContext());
    }

    public void makeJSONObjectRequest(int method, String url, JSONObject jsonRequest, String tag) {
        try {
            ActivityResponseListener<JSONObject> resultListener = new ActivityResponseListener<JSONObject>(this.nListener, tag);
            ActivityErrorListener errorListener = new ActivityErrorListener(this.nListener, tagName);
            JsonObjectRequest jObjReq = new JsonObjectRequest(method == 1 ? Request.Method.POST : Request.Method.GET, url, jsonRequest, resultListener, errorListener) {
            };
            jObjReq.setRetryPolicy(getRetryPolicy());
            jObjReq.setShouldCache(false);
            requestSession.addToRequestQueue(jObjReq, tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Create a new String GET Request
     */
    public void makeStringGetRequest(String url, String tag) {
        try {
            url = url.replaceAll(" ", "%20");

            ActivityResponseListener<String> resultListener = new ActivityResponseListener<String>(this.nListener, tag);
            ActivityErrorListener errorListener = new ActivityErrorListener(this.nListener, tagName);
            StringRequest req = new StringRequest(Request.Method.GET, url, resultListener, errorListener) {
            };
            req.setRetryPolicy(getRetryPolicy());
            req.setShouldCache(false);
            requestSession.addToRequestQueue(req, tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create a new String POST Request
     */
    public void makeStringPostRequest(String url, String tag) {
        try {
            ActivityResponseListener<String> resultListener = new ActivityResponseListener<String>(this.nListener, tag);
            ActivityErrorListener errorListener = new ActivityErrorListener(this.nListener, tagName);
            StringRequest req = new StringRequest(Request.Method.POST, url, resultListener, errorListener) {
                @Override
                protected Map<String, String> getParams() {
                    if (getParamsValues() != null)
                        return getParamsValues();
                    else
                        return new HashMap<String, String>();
                }

                @Override
                public String getBodyContentType() {
                    return super.getBodyContentType();
                }
            };
            req.setRetryPolicy(getRetryPolicy());
            requestSession.addToRequestQueue(req, tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HashMap<String, String> getParamsValues() {
        return this.params;
    }

    /**
     * Method give the default retrypolicy
     */
    private RetryPolicy getRetryPolicy() {
        RetryPolicy policy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return policy;
    }

    /**
     * Cancel all request in the current activity
     */
    public void cancelRequest(String tName) {
        try {
            if (!AppUtils.isEmpty(requestSession))
                requestSession.cancelPendingRequest(tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTitleRequest(String tagName, String url, Response.Listener<String> resultListener, Response.ErrorListener errorListener) {
        try {
            if (requestSession != null) {
                StringRequest req = new StringRequest(Request.Method.GET, url, resultListener, errorListener);
                RetryPolicy policy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                req.setRetryPolicy(policy);
                requestSession.addToRequestQueue(req, tagName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
