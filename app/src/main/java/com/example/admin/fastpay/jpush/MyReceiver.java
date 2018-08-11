package com.example.admin.fastpay.jpush;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.admin.fastpay.MainActivity;
import com.example.admin.fastpay.MyApplication;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.fragment.LoginFragment;
import com.example.admin.fastpay.constant.SPConstant;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.KDXFUtils;
import com.iflytek.cloud.SpeechSynthesizer;

import cn.jpush.android.api.JPushInterface;



/**
 * Created by sun on 2017/5/17.
 */

public class MyReceiver extends BroadcastReceiver {
    private SpeechSynthesizer mTts;
    private String s;
    private String s1;

    @Override
    public void onReceive(final Context context, Intent intent) {
//
//        if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
//
//            boolean bobaoshezhi = UserInfoManager.isVideoOpen();
//
//            Bundle bundle = intent.getExtras();
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//
//            Log.i("tag","-------------->message"+message);
//            if (message.equals("已发送")) {
//                new NormalAlert2Dialog.Builder(context)
//                        .setHeight(0.25f)  //屏幕高度*0.23
//                        .setWidth(0.7f)  //屏幕宽度*0.65
//                        .setTitleVisible(true)
//                        .setTitleText("下线通知")
//                        .setTitleTextColor(R.color.colorPrimary)
//                        .setContentText("您的账号已在另一处登录")
//                        .setContentTextColor(R.color.bill_text_hei)
//                        .setSingleMode(true)
//                        .setLeftButtonText("退出")
//                        .setLeftButtonTextColor(R.color.bill_text_hei)
//                        .setRightButtonText("重新登录")
//                        .setRightButtonTextColor(R.color.bill_text_hei)
//                        .setCanceledOnTouchOutside(true)
//                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlert2Dialog>() {
//                            @Override
//                            public void clickLeftButton(NormalAlert2Dialog dialog, View view) {
//                                ((MainActivity)context).exit();
//                                UserInfoManager.logout();
//                            }
//
//                            @Override
//                            public void clickRightButton(NormalAlert2Dialog dialog, View view) {
//                                ((MainActivity)context).exit();
//                                UserInfoManager.logout();
//                                if (context instanceof MainActivity) {
//                                    ((MainActivity) context).goHome();
//                                    ((MainActivity) context).openNewFragmentWithDefaultAnim(new LoginFragment());
//                                } else {
//                                    context.startActivity(new Intent(context,MainActivity.class));
//                                }
//                            }
//                        })
//                        .build()
//                        .show();
//
//            }else {
//                mTts = KDXFUtils.getSpeechSynthesizer(context);
//                if (message == null) {
//                    if(!bobaoshezhi){
//                        mTts.startSpeaking("支付失败", KDXFUtils.mSynListener);
//                    }
//                } else {
//
//                    if(message.contains("-")){
//                        String[] split = message.split("-");
//
//                        s = split[0];
//                        s1 = split[1];
//
//                        if(!bobaoshezhi){
//                            mTts.startSpeaking(s1, KDXFUtils.mSynListener);
//                        }
//                    }else{
//                        if(!bobaoshezhi){
//                            mTts.startSpeaking(message, KDXFUtils.mSynListener);
//                        }
//                    }
//                }
//            }
//        }

    }

}
