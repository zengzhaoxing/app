package com.example.admin.fastpay.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.utils.DialogUtil;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class UpLoadView extends RelativeLayout {

    public Bitmap getBitmap() {
        return mBitmap;
    }

    private Bitmap mBitmap;

    private ImageView mImageView;

    public UpLoadView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public UpLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public UpLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UpLoadView, defStyleAttr, 0);
        String text = ta.getString(R.styleable.UpLoadView_text);
        ta.recycle();

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_IN_PARENT,TRUE);
        linearLayout.setLayoutParams(lp);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView cameraIv = new ImageView(context);
        LinearLayout.LayoutParams ivLp = new  LinearLayout.LayoutParams(DensityUtil.dip2px(24),DensityUtil.dip2px(19));
        ivLp.bottomMargin = DensityUtil.dip2px(10);
        cameraIv.setLayoutParams(ivLp);
        cameraIv.setScaleType(ImageView.ScaleType.FIT_XY);
        cameraIv.setImageResource(R.drawable.camera);

        TextView textView = new TextView(context);
        textView.setTextColor(ResUtil.getColor(R.color.text_gray));
        textView.setTextSize(15);
        textView.setText(text);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(cameraIv);
        linearLayout.addView(textView);

        mImageView = new ImageView(context);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewUtil.interceptTouchEvent(mImageView);
        mImageView.setVisibility(GONE);

        addView(linearLayout);
        addView(mImageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewUtil.isDoubleRequest(v)) {
                    return;
                }
                DialogUtil.showGetPhoto((Activity) getContext(), new DialogUtil.OnPhotoGet() {
                    @Override
                    public void onGet(Bitmap photo) {
                        setImageBitmap(photo);
                    }
                });
            }
        });

    }

    public void setImageUrl(String url) {
        Glide.with(getContext()).load(url).into(mImageView);
        mImageView.setVisibility(VISIBLE);
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        mImageView.setImageBitmap(bitmap);
        mImageView.setVisibility(mBitmap == null ? GONE :VISIBLE);
    }

}
