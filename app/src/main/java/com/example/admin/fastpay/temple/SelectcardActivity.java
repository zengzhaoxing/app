/*
package com.example.admin.fastpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.fastpay.MyApplication;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.SelectCardAdapter;
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

public class SelectcardActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView addBankcard;

    private ListView listView;

    private int position;
    private List<BankCradBean.Datum> list;

    private String result;
    private String success;
    private ImageView lastCheckOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcard);
        MyApplication.getAppManager().addActivity(SelectcardActivity.this);
        back = (ImageView) findViewById(R.id.back18);
        addBankcard = (ImageView) findViewById(R.id.addbankcard);
        listView = (ListView) findViewById(R.id.selectcard_lv);

//得到上个页面传过来的position  进行相应的操作
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", position);
                //设置返回数据
                SelectcardActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });

        //添加银行卡点击事件
        addBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectcardActivity.this, AddCardActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastCheckOption != null) {
                    lastCheckOption.setVisibility(View.INVISIBLE);
                }
                //设置点击的条目  状态图片变为选中
                lastCheckOption = (ImageView) view.findViewById(R.id.selectcard_card_zhuangtai);
                lastCheckOption.setImageResource(R.drawable.xuanzhong);
                Toast.makeText(SelectcardActivity.this, "你点击了" + position, Toast.LENGTH_LONG).show();

                //携带参数传回上个界面
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", position);
                //设置返回数据
                SelectcardActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                SelectcardActivity.this.finish();
            }
        });

        //退出这个activity的时候传回 从上个activity带来的position
        Intent intent1 = new Intent();
        //把返回数据存入Intent
        intent1.putExtra("result", position);
        //设置返回数据
        SelectcardActivity.this.setResult(RESULT_OK, intent1);

    }


    private void initData(final int position) {
        String token = sp.getString("token", null);
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
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
                result = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    success = jsonObject.optString("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (success.equals("1")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BankCradBean>() {
                    }.getType();
                    final BankCradBean bankCradBean = gson.fromJson(result, type);
                    list = bankCradBean.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size() == 0) {
                                ToastUtils.show(SelectcardActivity.this, "点击右上角添加银行卡");
                            } else {

                                SelectCardAdapter adapter = new SelectCardAdapter(SelectcardActivity.this, list);
                                listView.setAdapter(adapter);

                                listView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        lastCheckOption = (ImageView) listView.getChildAt(position).findViewById(R.id.selectcard_card_zhuangtai);
                                        lastCheckOption.setImageResource(R.drawable.xuanzhong);
                                    }
                                });
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getAppManager().finishActivity(SelectcardActivity.this);
    }
}
*/
