package com.upb.laguaca;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instanciaVolley;
    private RequestQueue request;
    private static Context contexto;

    private VolleySingleton(Context context) {
        contexto = context;
        request = getRequestQueue();
    }



    public static synchronized VolleySingleton getInstanciaVolley(Context contexto) {
        if (instanciaVolley == null) {
            instanciaVolley = new VolleySingleton(contexto);
        }
        return instanciaVolley;
    }

    private RequestQueue getRequestQueue() {

        if (request == null) {
            request = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return request;
    }
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
