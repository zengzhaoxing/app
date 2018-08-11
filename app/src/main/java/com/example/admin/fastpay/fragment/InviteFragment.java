package com.example.admin.fastpay.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.StoreInfo;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.response.CashierResp2;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.ConstantsUtils;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.zxing.encode.CodeCreator;
import com.google.zxing.WriterException;
import com.zxz.www.base.net.request.JsonRequester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by admin on 2017/7/11.
 */


public class InviteFragment extends ChildBaseFragment {
    private TextView name;
    private ImageView pic;
    private  CashierResp2.DataBean shouYinYuanBean;

    private int[] mSavePositions = new int[2];

    private RelativeLayout jietu;

    private Bitmap bmp;
    //顶部裁剪坐标
    private int mCutTop;
    //左侧裁剪坐标
    private int mCutLeft;
    private String codeurl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.invite_fragment,null);
        ImageView back = (ImageView) view.findViewById(R.id.back10);
        name = (TextView) view.findViewById(R.id.inviteer_name);
        pic = (ImageView) view.findViewById(R.id.inviteer_pic);
        Button sure = (Button) view.findViewById(R.id.invite_fragment_button);
        jietu = (RelativeLayout) view.findViewById(R.id.jietu);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jietu.getLocationOnScreen(mSavePositions);
                mCutLeft = mSavePositions[0];
                mCutTop = mSavePositions[1];
                // 获取屏幕
                View dView = getActivity().getWindow().getDecorView();
                dView.destroyDrawingCache();
                dView.setDrawingCacheEnabled(true);
                dView.buildDrawingCache();
                bmp = dView.getDrawingCache();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        screenshot();
                    }
                }).start();
                OnekeyShare oks = new OnekeyShare();
                oks.setTitle("800万商家收款神器，二维码0.25%信用卡38元封顶，手机变pos机！");
                oks.setTitleUrl(codeurl);
                String name = shouYinYuanBean == null ? "我" : shouYinYuanBean.getName();
                oks.setText(name+"邀请您一起使用秒到收款，支持商家二维码收款和个人信用卡收款所有需求！手机下载注册马上用！");
                oks.setImagePath(ConstantsUtils.IMAGE_DIR+"/invite.jpg");
                oks.setUrl(codeurl);
                oks.setSite("秒到收款");
                oks.setSiteUrl(codeurl);
                oks.show(mBaseActivity);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        refreshUi();
        return view;
    }

    private void screenshot() {


        if (bmp != null) {
            try {
                //二次截图
                Bitmap saveBitmap = Bitmap.createBitmap(jietu.getWidth(), jietu.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(saveBitmap);
                Paint paint = new Paint();
                canvas.drawBitmap(bmp, new Rect(mCutLeft, mCutTop, mCutLeft + jietu.getWidth(), mCutTop + jietu.getHeight()),
                        new Rect(0, 0, jietu.getWidth(), jietu.getHeight()), paint);

                File imageDir = new File(ConstantsUtils.IMAGE_DIR);
                if (!imageDir.exists()) {
                    imageDir.mkdir();
                }
                String imageName = ConstantsUtils.JIETU;
                File file = new File(imageDir, imageName);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream os = new FileOutputStream(file);
                saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();

                //将截图保存至相册并广播通知系统刷新
                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), imageName, null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                getActivity().sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void refreshUi() {
        if (UserInfoManager.isLogin()) {
            RequestCaller.post(UrlBase.SHOUYINYUAN, new TokenModel(), CashierResp2.class, new JsonRequester.OnResponseListener<CashierResp2>() {
                @Override
                public void onResponse(CashierResp2 response, int resCode) {
                    if (response == null || name == null || response.getData() == null) {
                        return;
                    }
                    shouYinYuanBean = response.getData();
                    name.setText(shouYinYuanBean.getName()+"的邀请码");
                    codeurl = UrlBase.ERWEIMA+shouYinYuanBean.getId();
                    Bitmap qrCode = null;
                    try {
                        qrCode = CodeCreator.createQRCode(codeurl);
                        pic.setImageBitmap(qrCode);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
