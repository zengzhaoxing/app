package com.zxz.www.base.net.request;

import com.zxz.www.base.model.BaseModel;

public interface OnResponseListener<T> {
    void onResponse(T response, int resCode);
}
