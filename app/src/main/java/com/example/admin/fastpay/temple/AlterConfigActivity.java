//package com.example.admin.fastpay.activity;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.example.admin.fastpay.MyApplication;
//import com.example.admin.fastpay.R;
//import com.example.admin.fastpay.utils.ToastUtils;
//import com.example.admin.fastpay.utils.UrlBase;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import static com.example.admin.fastpay.MyApplication.sp;
//
//public class AlterConfigActivity extends AppCompatActivity {
//
//    private EditText id;
//    private EditText key;
//    private EditText name;
//    private Button sure;
//    private ImageView back;
//    private  String success;
//    private ConfigModel.ConfigDataBean configDataBean;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alter_config);
//        MyApplication.getAppManager().addActivity(AlterConfigActivity.this);
//        Intent intent = getIntent();
//        configDataBean = (ConfigModel.ConfigDataBean) intent.getSerializableExtra("data");
//        back = (ImageView) findViewById(R.id.back26);
//        id = (EditText) findViewById(R.id.config_xiugai_id);
//        key = (EditText) findViewById(R.id.config_xiugai_key);
//        name = (EditText) findViewById(R.id.config_xiugai_name);
//        sure = (Button) findViewById(R.id.config_xiugai_sure);
//
//
//        id.setText(configDataBean.getPushId()+"");
//        key.setText(configDataBean.getPushKey()+"");
//        name.setText(configDataBean.getPushUserName()+"");
//
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestData();
//            }
//        });
//
//
//
//    }
//
//
//    //   参数token,push_id,push_key,push_user_name   请求修改设备接口
//    private void requestData() {
//        String token = sp.getString("token","");
//        String push_id = id.getText().toString().trim();
//        String push_key = key.getText().toString().trim();
//        String push_user_name = name.getText().toString().trim();
//
//
//        OkHttpClient httpClient = new OkHttpClient();
//        RequestBody requestBody = new FormBody.Builder()
//                .add("token",token)
//                .add("push_id",push_id)
//                .add("push_key",push_key)
//                .add("push_user_name",push_user_name)
//                .build();
//        Request post = new Request.Builder()
//                .url(UrlBase.SHEBEIXIUGAI)
//                .post(requestBody)
//                .build();
//
//        httpClient.newCall(post).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                success = response.body().string();
//                try {
//                    JSONObject object = new JSONObject(success);
//                    String success = object.optString("success");
//                    if (success.equals("1")){
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtils.show(AlterConfigActivity.this,"修改成功");
//                                finish();
//                            }
//                        });
//                    }else if (success.equals("0")){
//
//                        final String msg = object.optString("msg");
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtils.show(AlterConfigActivity.this,msg);
//                            }
//                        });
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        MyApplication.getAppManager().finishActivity(AlterConfigActivity.this);
//    }
//}
