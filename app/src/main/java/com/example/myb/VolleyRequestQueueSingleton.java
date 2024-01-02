package com.example.myb;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyRequestQueueSingleton {

    private static VolleyRequestQueueSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyRequestQueueSingleton(Context ctx) {
        context = ctx.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyRequestQueueSingleton.class) {
                if (instance == null) {
                    instance = new VolleyRequestQueueSingleton(context);
                }
            }}
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void makeJsonObjectRequest(String url, Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, onResponse, onErrorResponse);
        getRequestQueue().add(jsonObjectRequest);
    }

    public void makeJsonRequest(String url, Response.Listener<String> onResponse, Response.ErrorListener onErrorResponse) {
        makeJsonObjectRequest(url, response -> {
            onResponse.onResponse(response.toString());
        }, onErrorResponse);
    }
    public void makeStringRequest(String url, Response.Listener<String> onResponse, Response.ErrorListener onErrorResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onResponse, onErrorResponse);
        getRequestQueue().add(stringRequest);
    }
    public void cancelRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
