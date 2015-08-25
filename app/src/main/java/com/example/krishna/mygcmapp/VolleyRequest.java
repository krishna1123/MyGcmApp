package com.example.krishna.mygcmapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by balakrishna on 30/6/15.
 */
public class VolleyRequest {

    private static VolleyRequest instance;
    private RequestQueue requestQueue;

    public static VolleyRequest getInstance() {
        if (instance == null) {
            instance = new VolleyRequest();
        }

        return instance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }
}
