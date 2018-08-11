package com.example.admin.fastpay.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.StoreInfo;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.request.TradePayReq;
import com.example.admin.fastpay.model.response.Resp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.KDXFUtils;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.view.Calculater;
import com.example.admin.fastpay.zxing.android.CaptureActivity;
import com.iflytek.cloud.SpeechSynthesizer;
import com.zxz.www.base.net.request.JsonRequester;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 2017/7/11.
 */

public class ReceiptFragment extends ChildBaseFragment implements View.OnClickListener {
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private String content;
    private String amount;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private EditText tv_show;
    private Calculater mCalculater;
    private List<StoreInfo.DataBean> menDianData;
    private List<String> mendianInfo;  //门店信息

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receiptfragment,container,false);
        Button saoyisao = (Button) view.findViewById(R.id.saoyisao);
        Button erweima = (Button) view.findViewById(R.id.erweima);
        Button shuaka = (Button) view.findViewById(R.id.cardpay);
        Button kuaijie = (Button) view.findViewById(R.id.fastpay);
        tv_show = (EditText) view.findViewById(R.id.tv_show);
        mCalculater = (Calculater)view.findViewById(R.id.calculater_view);
        saoyisao.setOnClickListener(this);
        erweima.setOnClickListener(this);
        shuaka.setOnClickListener(this);
        kuaijie.setOnClickListener(this);
        refreshUi();
        return view;
    }

    private String screeningCondition(String s){

        if(s.equals("o")){
            return "当面付";
        }else if(s.equals("s")){
            return "口碑";
        }else if(s.equals("w")){
            return "微信";
        }else if(s.equals("p")){
            return "平安银行";
        }else if(s.equals("f")){
            return "浦发银行";
        }else if(s.equals("u")){
            return "银联";
        }else if(s.equals("b")){
            return "微众银行";
        }else if(s.equals("m")){
            return "民生银行";
        }else{
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.saoyisao:
                amount = mCalculater.getAmount();
                String moren = tv_show.getText().toString();
                Log.i("tag", "onClick:+++++++++++++++++++++++++++++++ "+amount);
                if (amount == null|| moren.equals("0.00")) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.startsWith("0") && amount.length() <= 2) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.equals("0.0")) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.endsWith(".")) {
                    Toast.makeText(getActivity(), "金额的格式不对", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mBaseActivity, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }
                break;
            case R.id.erweima:
                moren = tv_show.getText().toString();
                amount = mCalculater.getAmount();
                Log.i("tag", "onClick:+++++++++++++++++++++++++++++++ "+amount);
                if (amount == null|| moren.equals("0.00")) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.startsWith("0") && amount.length() <= 2) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.equals("0.0")) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.endsWith(".")) {
                    Toast.makeText(getActivity(), "金额的格式不对", Toast.LENGTH_SHORT).show();
                } else {
//                    NormalSelectionDialog dialog1 = new NormalSelectionDialog.Builder(getActivity())
//                            .setlTitleVisible(true)   //设置是否显示标题
//                            .setTitleHeight(50)   //设置标题高度
//                            .setTitleText("请选择门店")  //设置标题提示文本
//                            .setTitleTextSize(14) //设置标题字体大小 sp
//                            .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
//                            .setItemHeight(45)  //设置item的高度
//                            .setItemWidth(0.9f)  //屏幕宽度*0.9
//                            .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
//                            .setItemTextSize(15)  //设置item字体大小
//                            .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
//                                @Override
//                                public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
//                                    dialog.dismiss();
//                                    StoreInfo.DataBean dataBean = menDianData.get(position);
//                                    ReceiptCodeFragment.showFragment(mBaseActivity,amount,dataBean.getStore_id());
//                                }
//                            })
//                            .setCanceledOnTouchOutside(false)  //设置是否可点击其他地方取消dialog
//                            .build();
//                    dialog1.setDatas(mendianInfo);
//                    dialog1.show();
                }
                break;
            case R.id.cardpay:
                FunctionFragment.showFragment(mBaseActivity,"刷卡收款");
                break;
            case R.id.fastpay:
                amount = mCalculater.getAmount();
                moren = tv_show.getText().toString();
                Log.i("tag", "onClick:+++++++++++++++++++++++++++++++ "+amount);
                if (amount == null|| moren.equals("0.00") || amount.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.startsWith("0") && amount.length() <= 2) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.equals("0.0")) {
                    Toast.makeText(getActivity(), "交易的金额要大于零", Toast.LENGTH_SHORT).show();
                } else if (amount.endsWith(".")) {
                    Toast.makeText(getActivity(), "金额的格式不对", Toast.LENGTH_SHORT).show();
                } else {
                    QuickPaySelectFragment.showFragment(mBaseActivity,Float.parseFloat(amount));
                }
                break;
        }
    }

    Dialog progressDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_SCAN && resultCode == RESULT_OK){
            if(data!=null){
                content = data.getStringExtra(DECODED_CONTENT_KEY);
                progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
                progressDialog.setContentView(R.layout.dialog);
                progressDialog.setCancelable(true);
                progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
                msg.setText("正在支付中");
                progressDialog.show();
                requestData();
            }
        }
    }

    private void requestData() {
        TradePayReq req = new TradePayReq(content, amount);
        RequestCaller.post(UrlBase.SAOMARUKU, req, Resp.class, new JsonRequester.OnResponseListener<Resp>() {
            @Override
            public void onResponse(Resp response, int resCode) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                SpeechSynthesizer mTts = KDXFUtils.getSpeechSynthesizer(mBaseActivity);
                if (response != null) {
                    PayResultFragment.showFragment(mBaseActivity,response.isSuccess(),response.getMsg());
                    if (UserInfoManager.isVideoOpen()) {
                        mTts.startSpeaking(response.getMsg(), KDXFUtils.mSynListener);
                    }
                }
            }
        });
    }

    @Override
    public void refreshUi() {
        if (UserInfoManager.isLogin()) {
            RequestCaller.post(UrlBase.MENDIAN, new TokenModel(), StoreInfo.class, new JsonRequester.OnResponseListener<StoreInfo>() {
                @Override
                public void onResponse(StoreInfo response, int resCode) {
                    if (response != null && response.getData() != null) {
                        menDianData = response.getData();
                        mendianInfo = new ArrayList<>();
                        for (int i = 0;i < menDianData.size();i++){
                            char dataBean = menDianData.get(i).getStore_id().trim().charAt(0);
                            String s = String.valueOf(dataBean);
                            String mendian = screeningCondition(s);
                            mendianInfo.add(i,menDianData.get(i).getStore_name().trim() + "(" + mendian + ")");
                        }
                    }
                }
            });
        }
    }

}
