package com.example.admin.fastpay.thirdsdk;

/**
 * Created by 曾宪梓 on 2018/1/23.
 */

public interface ShareListener {

    int SUCCESS = 1;

    int FAIL = 2;

    int CANCEL = 3;

    void onShareResult(int resultCode);
}
