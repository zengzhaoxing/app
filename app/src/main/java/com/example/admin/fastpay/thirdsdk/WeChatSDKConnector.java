package com.example.admin.fastpay.thirdsdk;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.example.admin.fastpay.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKConnector;
import com.zxz.www.base.utils.ToastUtil;

/**
 * Created by 曾宪梓 on 2018/1/23.
 */
public class WeChatSDKConnector extends SDKConnector implements IWXAPIEventHandler {

    private String APP_ID = "wxa178c8a862715b37";

    private IWXAPI mApi;

    private static WeChatSDKConnector ourInstance = new WeChatSDKConnector();

    public static WeChatSDKConnector getInstance() {
        return ourInstance;
    }

    private WeChatSDKConnector() {

    }

    @Override
    protected void onAppCreate(BaseApp application) {
        mApi = WXAPIFactory.createWXAPI(application, APP_ID, true);
        mApi.registerApp(APP_ID);
    }

    public boolean isWXAvailable() {
        return mApi.isWXAppInstalled() && mApi.isWXAppSupportAPI();
    }

    public void handleIntent(Intent intent) {
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp == null) {
            return;
        }
        if (baseResp instanceof SendAuth.Resp) {
            // TODO: 2018/1/23  login
        } else if (baseResp instanceof PayResp) {
            // TODO: 2018/1/23 pay
        } else if (baseResp instanceof SendMessageToWX.Resp && mShareListener != null) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                ToastUtil.toast("分享成功");
                mShareListener.onShareResult(ShareListener.SUCCESS);
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                ToastUtil.toast("分享取消");
                mShareListener.onShareResult(ShareListener.CANCEL);
            } else {
                ToastUtil.toast("分享失败");
                mShareListener.onShareResult(ShareListener.FAIL);
            }
        }
    }

    private void share(int shareType, WXMediaMessage.IMediaObject shareObject, String title, String description) {
        if (!isWXAvailable()) {
            ToastUtil.toast("请先安装微信");
        } else {
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = shareObject;
            msg.description = description;
            msg.title = title;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = shareType;
            mApi.sendReq(req);
        }
    }

    private void shareWebPager(int shareType, String webUrl, String title, String description) {
        WXWebpageObject webObj = new WXWebpageObject(webUrl);
        share(shareType, webObj, title, description);
    }

    public void shareWebPagerToFriend(String webUrl, String title, String description) {
        shareWebPager(SendMessageToWX.Req.WXSceneSession, webUrl, title, description);
    }

    public void shareWebPagerToMoments(String webUrl, String title, String description) {
        shareWebPager(SendMessageToWX.Req.WXSceneTimeline, webUrl, title, description);
    }

    public void setShareListener(ShareListener shareListener) {
        mShareListener = shareListener;
    }

    private ShareListener mShareListener;

}
