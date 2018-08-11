package com.example.admin.fastpay.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.PayQRCodeResp;
import com.example.admin.fastpay.model.request.ReceiptCodeReq;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.zxing.encode.CodeCreator;
import com.google.zxing.WriterException;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.ToastUtil;

public class ReceiptCodeFragment extends BaseFragment {
    private TextView tv_code_price;
    private ImageView iv_code_change;

    public static final String PRICE = "price";

    public static final String STORE_ID = "store_id";

    public static void showFragment(BaseActivity activity,String price,String storeId){
        ReceiptCodeFragment fragment = new ReceiptCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PRICE, price);
        bundle.putString(STORE_ID, storeId);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt_code,container,false);
        tv_code_price = (TextView) view.findViewById(R.id.tv_code_price);
        iv_code_change = (ImageView) view.findViewById(R.id.iv_code_change);
        View back = view.findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        generateCode();
        return view;
    }

    private void generateCode() {
        Bundle bundle = getArguments();
        String mPrice = bundle.getString(PRICE);
        String mStoreId = bundle.getString(STORE_ID);
        tv_code_price.setText("收款金额：￥" + mPrice);
        ReceiptCodeReq req = new ReceiptCodeReq(mStoreId, mPrice);
        RequestCaller.post(UrlBase.SHENGCHENGERWEIMA, req, PayQRCodeResp.class, new JsonRequester.OnResponseListener<PayQRCodeResp>() {
            @Override
            public void onResponse(PayQRCodeResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().getCode_url() != null && iv_code_change != null) {
                    Bitmap qrCode = null;
                    try {
                        qrCode = CodeCreator.createQRCode(response.getData().getCode_url());
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    iv_code_change.setImageBitmap(qrCode);
                } else {
                    ToastUtil.toast("系统异常");
                }
            }
        });
    }
}
