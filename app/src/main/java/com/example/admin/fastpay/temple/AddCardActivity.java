/*
package com.example.admin.fastpay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.fastpay.MyApplication;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.utils.EditCheckUtil;
import com.example.admin.fastpay.utils.ToastUtils;
import com.example.admin.fastpay.utils.UrlBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.admin.fastpay.MyApplication.sp;

public class AddCardActivity extends AppCompatActivity {


    private EditText name;
    private EditText bank;
    private EditText shenfenzheng;
    private EditText number;
    private EditText phone;
    private Button enter;

    private ImageView back;
    private String aname;
    private String abank;
    private String ashenfenzheng;
    private String anumber;
    private String aphone;
    private String result;
    private String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addbankcard);
        MyApplication.getAppManager().addActivity(AddCardActivity.this);
        back = (ImageView) findViewById(R.id.back22);

        name = (EditText) findViewById(R.id.add_card_name);
        bank = (EditText) findViewById(R.id.add_card_bank);
        shenfenzheng = (EditText) findViewById(R.id.add_card_shenfenzheng);
        number = (EditText) findViewById(R.id.add_card_number);
        phone = (EditText) findViewById(R.id.add_card_phone);


        enter = (Button) findViewById(R.id.add_card_enter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCardData();
            }
        });
    }

    private void addCardData() {
        aname = name.getText().toString().trim();
        abank = bank.getText().toString().trim();
        ashenfenzheng = shenfenzheng.getText().toString().trim();
        anumber = number.getText().toString().trim();
        aphone = phone.getText().toString().trim();

        if(!TextUtils.isEmpty(aname)){
           if (!TextUtils.isEmpty(abank)){
                if (!TextUtils.isEmpty(ashenfenzheng)){
                   if (EditCheckUtil.IDCardValidate(ashenfenzheng)){
                       if (!TextUtils.isEmpty(anumber)){
                           if (!TextUtils.isEmpty(aphone)){
                               requestData();
                           }else {
                               Toast.makeText(AddCardActivity.this,"请输入预留手机号",Toast.LENGTH_LONG).show();
                           }
                       }else {
                           Toast.makeText(AddCardActivity.this,"请输入银行卡号",Toast.LENGTH_LONG).show();
                       }
                   }else {
                       Toast.makeText(AddCardActivity.this,"身份证信息不正确",Toast.LENGTH_LONG).show();
                   }
                }else {
                    Toast.makeText(AddCardActivity.this,"请输入身份证号",Toast.LENGTH_LONG).show();
                }
           }else {
               Toast.makeText(AddCardActivity.this,"请输入开户行",Toast.LENGTH_LONG).show();
           }
        }else {
            Toast.makeText(AddCardActivity.this,"请输入开户名",Toast.LENGTH_LONG).show();
        }
    }

    private void requestData() {

//        ToastUtils.show(AddCardActivity.this,abank+"  "+aname+"  "+anumber+"  "+aphone+"  "+ashenfenzheng);

//        $no=$request->no;       no身份证号
//        $card_no=$request->card_no;   银行卡
//        $bank_name=$request->bank_name;     银行名称
//        $phone=$request->phone;    手机号
//        $name=$request->name;        姓名
        String token = sp.getString("token","");
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token",token)
                .add("no",ashenfenzheng)
                .add("card_no",anumber)
                .add("bank_name",abank)
                .add("phone",aphone)
                .add("name",aname)
                .build();

        Request requestPost = new Request.Builder()
                .url(UrlBase.ADDYINHANGKA)
                .post(requestBody)
                .build();

        httpClient.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    success = object.optString("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (success.equals("1")){
                    final String data = object.optString("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(AddCardActivity.this,data);
                            finish();
                        }
                    });
                }else if (success.equals("0")){
                    final String msg = object.optString("msg");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(AddCardActivity.this,msg);
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getAppManager().finishActivity(AddCardActivity.this);
    }
}
*/
