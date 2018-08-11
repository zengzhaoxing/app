/*
package com.example.admin.fastpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.fastpay.MyApplication;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.BankCradAdapter;
import com.example.admin.fastpay.bean.BankCradBean;
import com.example.admin.fastpay.utils.ToastUtils;
import com.example.admin.fastpay.utils.UrlBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.admin.fastpay.MyApplication.sp;

public class BankManagerActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView back;
    private TextView textView;
    private RelativeLayout addbank;
    private String sesult;

    private String success1;

    private List<BankCradBean.Datum> list;
    private BankCradAdapter adapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_manager);
        MyApplication.getAppManager().addActivity(BankManagerActivity.this);
        back = (ImageView) findViewById(R.id.back24);
        addbank = (RelativeLayout) findViewById(R.id.bankcard_jiahao);
        textView = (TextView) findViewById(R.id.bankcard_text);
        lv = (ListView) findViewById(R.id.bankcard_lv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addbank.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        String token = sp.getString("token", null);
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token",token)
                .build();
        Request requestPost = new Request.Builder()
                .url(UrlBase.YINHANGKA)
                .post(requestBody)
                .build();

        httpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sesult = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(sesult);
                    success1 = jsonObject.optString("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (success1.equals("1")){

                    Gson gson = new Gson();
                    Type type = new TypeToken<BankCradBean>() {
                    }.getType();
                    BankCradBean bankCradBean = gson.fromJson(sesult,type);
                    list = bankCradBean.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            ToastUtils.show(BankManagerActivity.this,list.get(1).getPhone());
                            if (list.size()==0||list==null){
                                textView.setVisibility(View.VISIBLE);
                            }else {
                                textView.setVisibility(View.GONE);
                                adapter = new BankCradAdapter(BankManagerActivity.this,list);
                                lv.setAdapter(adapter);
                            }
                        }
                    });

                }else if (success1.equals("0")){
                    final String msg = jsonObject.optString("msg");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(BankManagerActivity.this,msg);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bankcard_jiahao:
                Intent intent = new Intent(BankManagerActivity.this,AddCardActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getAppManager().finishActivity(BankManagerActivity.this);
    }
}
*/
