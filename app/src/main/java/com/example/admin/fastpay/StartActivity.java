package com.example.admin.fastpay;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.admin.fastpay.presenter.LoginPresenter;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.utils.PermissionUtil;

public class StartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PermissionUtil.requestPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil.OnPermissionRequest() {
            @Override
            public void onResult(boolean isAllow) {
                if (!isAllow) {
                    finish();
                } else {
                    requestLoge();
                }
            }
        });
    }

    private void requestLoge() {
        LoginPresenter loginPresenter = new LoginPresenter();
        loginPresenter.login(UserInfoManager.getUserName(), UserInfoManager.getPassword());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }



}
