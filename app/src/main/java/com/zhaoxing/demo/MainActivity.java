package com.zhaoxing.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected MainFragment returnMainFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
