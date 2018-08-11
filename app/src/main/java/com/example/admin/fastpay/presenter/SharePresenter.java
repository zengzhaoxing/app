package com.example.admin.fastpay.presenter;

import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.response.CashierResp2;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.thirdsdk.ShareListener;
import com.example.admin.fastpay.thirdsdk.WeChatSDKConnector;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.net.request.JsonRequester;

/**
 * Created by 曾宪梓 on 2018/1/14.
 */

public class SharePresenter {

    private BaseActivity mActivity;

    private CashierResp2.DataBean mDataBean;

    public SharePresenter(BaseActivity activity) {
        mActivity = activity;
        RequestCaller.post(UrlBase.SHOUYINYUAN, new TokenModel(), CashierResp2.class, new JsonRequester.OnResponseListener<CashierResp2>() {
            @Override
            public void onResponse(CashierResp2 response, int resCode) {
                if (response != null) {
                    mDataBean = response.getData();
                }
            }
        });
    }

    public void share(ShareListener listener) {
        if (mDataBean == null) {
            return;
        }
        WeChatSDKConnector.getInstance().setShareListener(listener);
        String title = "800万商家收款神器，二维码0.25%信用卡38元封顶，手机变pos机！";
        String content = mDataBean.getName()+"邀请您一起使用秒到收款，支持商家二维码收款和个人信用卡收款所有需求！手机下载注册马上用！";
        WeChatSDKConnector.getInstance().shareWebPagerToMoments(UrlBase.ERWEIMA+mDataBean.getId(),title,content);
    }

}
