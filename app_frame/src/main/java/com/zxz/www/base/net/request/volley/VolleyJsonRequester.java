package com.zxz.www.base.net.request.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.model.RequestModel;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.net.request.JsonRequester;

import java.util.ArrayList;
import java.util.List;


class VolleyJsonRequester<req extends RequestModel,resp extends ResponseModel> extends JsonRequester<req,resp>{

    public static String TAG = "VolleyJsonRequester";

    public VolleyJsonRequester(String url, req request, Class<resp> responseClass, int requestMethod) {
        super(url, request, responseClass, requestMethod);
    }

    @Override
    public void startRequest() {
        StringRequest stringRequest = new StringRequest(mRequestMethod, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       parseResponse(response,200);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseResponse(null,error.networkResponse.statusCode);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestData == null ? null : mRequestData.toJson().getBytes();
            }
        };
        DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(mTimeOutMs,mRepeatTime,1);
        stringRequest.setRetryPolicy(defaultRetryPolicy);
        VolleyRequestManager.addRequest(stringRequest,this);
    }

    @Override
    public void stopRequest() {
        VolleyRequestManager.cancelAll(this);
    }

}


