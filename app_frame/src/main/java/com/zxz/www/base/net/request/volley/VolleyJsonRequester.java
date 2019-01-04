package com.zxz.www.base.net.request.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.net.request.JsonRequester;

import java.util.ArrayList;
import java.util.List;


class VolleyJsonRequester<RequestModel extends BaseModel,ResponseModel extends BaseModel> extends JsonRequester<RequestModel,ResponseModel>{

    public static String TAG = "VolleyJsonRequester";

    VolleyJsonRequester(String url, RequestModel request, Class<ResponseModel> responseClass, int requestMethod) {
        super(url, request, responseClass, requestMethod);
    }

    @Override
    public void startRequest() {
        StringRequest stringRequest = new StringRequest(mRequestMethod, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       parseResponse(response.getBytes(),200);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseResponse(null,error.networkResponse.statusCode);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (VolleyJsonRequester.this.getBody() == null) {
                    return null;
                }
                return VolleyJsonRequester.this.getBody().getBytes();
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


