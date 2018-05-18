package com.zxz.www.base.net.request.volley;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zxz.www.base.app.BaseApp;

class VolleyRequestManager {
    private static RequestQueue mRequestQueue = Volley.newRequestQueue(BaseApp.getContext());

    private VolleyRequestManager() {
        // no instances
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);

    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
