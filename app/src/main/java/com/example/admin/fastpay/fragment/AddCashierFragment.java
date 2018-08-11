package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.request.AddCashierReq;
import com.example.admin.fastpay.model.response.Resp;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddCashierFragment extends BaseFragment {


    @BindView(R.id.ed_add_name)
    EditText mEdAddName;
    @BindView(R.id.ed_add_phone)
    EditText mEdAddPhone;
    @BindView(R.id.ed_add_password)
    EditText mEdAddPassword;
    Unbinder unbinder;

    private String store_id;

    private static final String STORE_ID = "store_id";

    public static void showFragment(BaseActivity activity , String storeId) {
        AddCashierFragment fragment = new AddCashierFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STORE_ID, storeId);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cashier, container, false);
        unbinder = ButterKnife.bind(this, view);

        store_id = getArguments().getString(STORE_ID);

        //不能有空格
        setEditTextInhibitInputSpace(mEdAddName);
        setEditTextInhibitInputSpace(mEdAddPhone);
        setEditTextInhibitInputSpace(mEdAddPassword);
        //不能有特殊字符
        setEditTextInhibitInputSpeChat(mEdAddName);
        setEditTextInhibitInputSpeChat(mEdAddPhone);
        setEditTextInhibitInputSpeChat(mEdAddPassword);

        return view;
    }

    private void addRequest() {

        String name = mEdAddName.getText().toString().trim();
        String phone = mEdAddPhone.getText().toString().trim();
        String password = mEdAddPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast("请输入密码");
            return;
        }

//        AddCashierReq req = new AddCashierReq();
//        req.setPassword(password);
//        req.setName(name);
//        req.setPhone(phone);
//        req.setStore_id(store_id);
//        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.ADDSHOUYINYUAN, req, Resp.class);
//        requester.setListener(new JsonRequester.OnResponseListener<Resp>() {
//            @Override
//            public void onResponse(Resp response, int resCode) {
//                if (response != null && response.isSuccess()) {
//                    new NormalAlertDialog.Builder(mBaseActivity)
//                            .setHeight(0.23f)  //屏幕高度*0.23
//                            .setWidth(0.65f)  //屏幕宽度*0.65
//                            .setTitleVisible(true)
//                            .setTitleText("温馨提示")
//                            .setTitleTextColor(R.color.colorPrimary)
//                            .setContentText(response.getMsg())
//                            .setContentTextColor(R.color.colorPrimaryDark)
//                            .setSingleMode(true)
//                            .setSingleButtonText("关闭")
//                            .setSingleButtonTextColor(R.color.colorAccent)
//                            .setCanceledOnTouchOutside(true)
//                            .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
//                                @Override
//                                public void clickSingleButton(NormalAlertDialog dialog, View view) {
//                                    dialog.dismiss();
//                                    goBack();
//                                }
//                            })
//                            .build()
//                            .show();
//                } else {
//                    new NormalAlertDialog.Builder(mBaseActivity)
//                            .setHeight(0.23f)  //屏幕高度*0.23
//                            .setWidth(0.65f)  //屏幕宽度*0.65
//                            .setTitleVisible(true)
//                            .setTitleText("温馨提示")
//                            .setTitleTextColor(R.color.colorPrimary)
//                            .setContentText("系统异常")
//                            .setContentTextColor(R.color.colorPrimaryDark)
//                            .setSingleMode(true)
//                            .setSingleButtonText("关闭")
//                            .setSingleButtonTextColor(R.color.colorAccent)
//                            .setCanceledOnTouchOutside(true)
//                            .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
//                                @Override
//                                public void clickSingleButton(NormalAlertDialog dialog, View view) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .build()
//                            .show();
//                }
//            }
//        });
//        requester.startRequest();

    }


    private static void setEditTextInhibitInputSpeChat(EditText ed_add_name) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        ed_add_name.setFilters(new InputFilter[]{filter});
    }

    private static void setEditTextInhibitInputSpace(EditText edit_text) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        edit_text.setFilters(new InputFilter[]{filter});
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back15, R.id.btn_add_default})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back15:
                goBack();
                break;
            case R.id.btn_add_default:
                addRequest();
                break;
        }
    }
}
