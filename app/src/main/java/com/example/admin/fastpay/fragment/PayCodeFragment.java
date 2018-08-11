package com.example.admin.fastpay.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.PayQRCodeResp;
import com.example.admin.fastpay.model.StoreInfo;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.ConstantsUtils;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.zxing.encode.CodeCreator;
import com.google.zxing.WriterException;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PayCodeFragment extends BaseFragment {

    private LinearLayout ll_fixed_collection;
    private Button bu_fixed_mendian;
    private Button bu_fixed_baocun;
    private ImageView iv_fixed_code;
    private LinearLayout sv_fixed_collection;
    private LinearLayout yincang;
    private List<StoreInfo.DataBean> data1;
    private String[] mendianInfo;
    private int mposition;

    //顶部裁剪坐标
    private int mCutTop;
    //左侧裁剪坐标
    private int mCutLeft;
    //截图成功后显示的控件
    private ImageView mPicGet;
    private FrameLayout mFL;
    //绘图区高度
    private int mPicGetHeight;
    //绘图区宽度
    private int mPicGetWidth;
    //最后的截图
    private Bitmap saveBitmap;
    //待裁剪区域的绝对坐标
    private int[] mSavePositions = new int[2];
    //成功动画handler
    private SuccessHandler successHandler;
    //恢复初始化handler
    private InitHandler initHandler;
    private Bitmap bmp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_pay_code,container,false);
        bu_fixed_mendian = (Button) view.findViewById(R.id.bu_fixed_mendian);
        bu_fixed_baocun = (Button) view.findViewById(R.id.bu_fixed_baocun);
        iv_fixed_code = (ImageView) view.findViewById(R.id.iv_fixed_code);
        ll_fixed_collection = (LinearLayout) view.findViewById(R.id.ll_fixed_collection);
        sv_fixed_collection = (LinearLayout) view.findViewById(R.id.sv_fixed_collection);
        ImageView back = (ImageView) view.findViewById(R.id.back2);
        mPicGet = (ImageView) view.findViewById(R.id.iv_pic_get);
        mFL = (FrameLayout) view.findViewById(R.id.fl_pic);
        yincang = (LinearLayout) view.findViewById(R.id.yincang);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        successHandler = new SuccessHandler();
        initHandler = new InitHandler();
        requestMenDian();
        setRadio();//设置单选框
        return view;
    }

    private void setRadio() {
        bu_fixed_mendian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity);
                builder.setTitle("请选择门店");//标题
//                builder.setCancelable(false);// 是否可退出
                builder.setSingleChoiceItems(mendianInfo, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        // which表示点击的条目
                        Object checkedItem = lw.getAdapter().getItem(which);
                        // 既然你没有cancel或者ok按钮，所以需要在点击item后使dialog消失
                        mposition = which;//获取点击的位置
                        dialog.dismiss();
                        // 更新你的view
                        bu_fixed_mendian.setText((String)checkedItem);

                        generateCode();//生成二维码
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        bu_fixed_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_fixed_collection.getLocationOnScreen(mSavePositions);
//                ll_fixed_collection.getLocationInWindow(mSavePositions);
                mCutLeft = mSavePositions[0];
                mCutTop = mSavePositions[1];
                mPicGetHeight = sv_fixed_collection.getHeight();
                mPicGetWidth = sv_fixed_collection.getWidth();

                // 获取屏幕
                View dView = mBaseActivity.getWindow().getDecorView();
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
            }
        });

    }

    private void screenshot() {

        if (bmp != null) {
            try {
                //二次截图
                saveBitmap = Bitmap.createBitmap(ll_fixed_collection.getWidth(), ll_fixed_collection.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(saveBitmap);
                Paint paint = new Paint();
                canvas.drawBitmap(bmp, new Rect(mCutLeft, mCutTop, mCutLeft + ll_fixed_collection.getWidth(), mCutTop + ll_fixed_collection.getHeight()),
                        new Rect(0, 0, ll_fixed_collection.getWidth(), ll_fixed_collection.getHeight()), paint);

                File imageDir = new File(ConstantsUtils.IMAGE_DIR);
                if (!imageDir.exists()) {
                    imageDir.mkdir();
                }
                String imageName = ConstantsUtils.SCREEN_SHOT;
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
                MediaStore.Images.Media.insertImage(mBaseActivity.getContentResolver(), file.getAbsolutePath(), imageName, null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                mBaseActivity.sendBroadcast(intent);
                successHandler.sendMessage(Message.obtain());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            initHandler.sendMessage(Message.obtain());
        }
    }

    private void generateCode() {
        String store_id = data1.get(mposition).getStore_id();
        PayQRCodeReq req = new PayQRCodeReq(store_id);
        RequestCaller.post(UrlBase.SHENGCHENGERWEIMA, req, PayQRCodeResp.class, new JsonRequester.OnResponseListener<PayQRCodeResp>() {
            @Override
            public void onResponse(PayQRCodeResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().getCode_url() != null && iv_fixed_code != null) {
                    Bitmap qrCode = null;
                    try {
                        qrCode = CodeCreator.createQRCode(response.getData().getCode_url());
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    iv_fixed_code.setImageBitmap(qrCode);
                } else {
                    ToastUtil.toast("系统异常");
                }
            }
        });
    }

    private void requestMenDian() {

        RequestCaller.post(UrlBase.MENDIAN, new TokenModel(), StoreInfo.class, new JsonRequester.OnResponseListener<StoreInfo>() {
            @Override
            public void onResponse(StoreInfo response, int resCode) {
                if (response != null && response.getData() != null) {
                    data1 = response.getData();
                    mendianInfo = new String[data1.size()];
                    for (int i = 0;i < data1.size();i++){
                        char dataBean = data1.get(i).getStore_id().trim().charAt(0);
                        String s = String.valueOf(dataBean);
                        String mendian = screeningCondition(s);
                        mendianInfo[i] = data1.get(i).getStore_name().trim() + "(" + mendian + ")";
                    }
                }
            }
        });
    }

    private String screeningCondition(String s) {
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


    private class SuccessHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            showSuccessNew();
        }
    }


    /**
     * 恢复初始化handler
     */
    private class InitHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            ToastUtil.toast("储存失败");
        }
    }

    private void showSuccessNew() {
        ToastUtil.toast("保存成功");
        mPicGet.setImageBitmap(saveBitmap);

        ObjectAnimator paramsAnimator = ObjectAnimator.ofFloat(new Wrapper(mPicGet), "params", 1f, 0.7f);
        paramsAnimator.setDuration(800);
        paramsAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFL.setVisibility(View.VISIBLE);
                mPicGet.setVisibility(View.VISIBLE);
                yincang.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPicGet.setVisibility(View.GONE);
                        mFL.setVisibility(View.GONE);
                        yincang.setVisibility(View.VISIBLE);
                    }
                }, 1500);
            }
        });
        paramsAnimator.start();
    }


    class Wrapper {
        private View mTarget;

        public Wrapper(View mTarget) {
            this.mTarget = mTarget;
        }

        public float getParams() {
            ViewGroup.LayoutParams lp = mTarget.getLayoutParams();
            return lp.height / mPicGetHeight;
        }

        public void setParams(float params) {
            ViewGroup.LayoutParams lp = mTarget.getLayoutParams();
            lp.height = (int) (mPicGetHeight * params);
            lp.width = (int) (mPicGetWidth * params);
            mTarget.requestLayout();
        }
    }

}
