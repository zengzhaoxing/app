/*
package com.example.admin.fastpay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.fastpay.MyApplication;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.utils.KDXFUtils;
import com.iflytek.cloud.SpeechSynthesizer;

public class RemovemoneyActivity extends AppCompatActivity {

    private ImageView back;
    private RelativeLayout selectBankcard;
    private EditText editText;

    private ImageView now;
    private boolean zhuangtai = true;
    private ImageView tomorrow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private Button sure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removemoney);
        MyApplication.getAppManager().addActivity(RemovemoneyActivity.this);
        rl1 = (RelativeLayout) findViewById(R.id.removemoney_now);
        rl2 = (RelativeLayout) findViewById(R.id.removemoney_tomorrow);
        sure = (Button) findViewById(R.id.removemoney_sure);



        editText = (EditText) findViewById(R.id.removemoney_editmoney);
        selectBankcard = (RelativeLayout) findViewById(R.id.removemoney_card);
        back  = (ImageView) findViewById(R.id.back12);

        now = (ImageView) findViewById(R.id.image1);
        tomorrow = (ImageView) findViewById(R.id.image2);
        zhuangtai = true;



        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now.setImageResource(R.drawable.xuanzhong);
                tomorrow.setImageResource(R.drawable.weixuanzhong);
                zhuangtai = true;
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomorrow.setImageResource(R.drawable.xuanzhong);
                now.setImageResource(R.drawable.weixuanzhong);
                zhuangtai = false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai){
                    SpeechSynthesizer mTts = KDXFUtils.getSpeechSynthesizer(RemovemoneyActivity.this);
                    mTts.startSpeaking("选择的是第一项", KDXFUtils.mSynListener);
                }else {
                    SpeechSynthesizer mTts = KDXFUtils.getSpeechSynthesizer(RemovemoneyActivity.this);
                    mTts.startSpeaking("选择的是第二项", KDXFUtils.mSynListener);
                }
            }
        });

        selectBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemovemoneyActivity.this,SelectcardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getAppManager().finishActivity(RemovemoneyActivity.this);
    }
}
*/
