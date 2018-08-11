package com.example.admin.fastpay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.MenuAdapter;
import com.example.admin.fastpay.model.MenuModel;
import com.example.admin.fastpay.model.StoreInfo;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.response.RemarkResp;
import com.example.admin.fastpay.model.response.ZhangdanBean2;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.thirdsdk.KeFuSDKConnector;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.zxing.android.CaptureActivity;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by admin on 2017/7/11.
 */

public class HomeFragment extends ChildBaseFragment {
    Unbinder unbinder;
    private GridView gridView;
    private GridView netgridView;
    private TextView total;
    private TextView mouth;
    private TextView today;
    private TextView notice;

    private List<MenuModel> mMenuModels;


    //定义gridview的图片
    private int[] image = {R.drawable.cashier, R.drawable.sell_records, R.drawable.equipment_management, R.drawable.certification};
    //定义listview的文本信息
    private String[] text = {"收银员", "结算记录", "设备管理", "认证中心"};

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home_rb1, R.id.home_rb2, R.id.home_rb3, R.id.home_fragment_ll1, R.id.home_fragment_ll2, R.id.home_fragment_ll3})
    public void onViewClicked(View view) {
        if (view.getId() != R.id.home_rb3) {
            if (!UserInfoManager.isAuth()) {
                mBaseActivity.openNewFragmentWithDefaultAnim(new CertificationFragment());
                ToastUtil.toast("请先进行实名认证！");
                return;
            }
        }
        switch (view.getId()) {
            case R.id.home_rb3:
                KeFuSDKConnector.getInstance().startChat();
                break;
            case R.id.home_rb1:
                mBaseActivity.openNewFragmentWithDefaultAnim(new PayCodeFragment());
                break;
            case R.id.home_rb2:
            case R.id.home_fragment_ll1:
            case R.id.home_fragment_ll2:
            case R.id.home_fragment_ll3:
                if (!UserInfoManager.isAuth()) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new CertificationFragment());
                    ToastUtil.toast("请先进行实名认证！");
                    return;
                }
                mBaseActivity.openNewFragmentWithDefaultAnim(new BillFragment());
                break;
        }
    }

    private void initGridView() {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        // 使用HashMap将图片添加到一个数组中，注意一定要是HashMap<String,Object>类型的，因为装到map中的图片要是资源ID，而不是图片本身
        // 如果是用findViewById(R.drawable.image)这样把真正的图片取出来了，放到map中是无法正常显示的
        for (int i = 0; i < text.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", image[i]);
            map.put("text", text[i]);
            imagelist.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), imagelist,
                R.layout.layout_homefragment_gridview, new String[]{"image", "text"}, new int[]{
                R.id.grid_itempic, R.id.grid_itemname});
        // 设置GridView的适配器为新建的simpleAdapter
        gridView.setAdapter(simpleAdapter);

        //固定GridView的点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (text[position].equals("认证中心")) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new CertificationFragment());
                } else if (!UserInfoManager.isAuth()) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new CertificationFragment());
                    ToastUtil.toast("请先进行实名认证！");
                } else if (text[position].equals("结算记录")) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new TransactionRecordsFragment());
                } else if (text[position].equals("收银员")) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new StoreListFragment());
                } else {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new DevicesManagerFragment());
                }
            }
        });

        netgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!UserInfoManager.isAuth()) {
                    mBaseActivity.openNewFragmentWithDefaultAnim(new CertificationFragment());
                    ToastUtil.toast("请先进行实名认证！");
                    return;
                }
                H5Fragment.showH5Fragment(mBaseActivity, mMenuModels.get(position).getName(), mMenuModels.get(position).getUrl());
            }
        });

        notice.setVisibility(View.GONE);
        RequestCaller.get(UrlBase.REMARK_URL,null, RemarkResp.class,new JsonRequester.OnResponseListener<RemarkResp>(){

            @Override
            public void onResponse(RemarkResp response, int resCode) {
                if (response != null && response.isSuccess()) {
                    notice.setText(response.getRemark());
                    notice.setVisibility(View.VISIBLE);
                    notice.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    notice.setSingleLine(true);
                    notice.setSelected(true);
                    notice.setFocusable(true);
                    notice.setFocusableInTouchMode(true);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.home_fragment, null);
        gridView = (GridView) view.findViewById(R.id.home_fragment_gridview);
        netgridView = (GridView) view.findViewById(R.id.home_fragment_gridview2);
        total = (TextView) view.findViewById(R.id.totaltranslation);
        mouth = (TextView) view.findViewById(R.id.mouthtranslation);
        today = (TextView) view.findViewById(R.id.daytranslation);
        notice = (TextView) view.findViewById(R.id.notice);
        initGridView();
        refreshUi();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshUi() {
        if (!UserInfoManager.isLogin()) {
            return;
        }

        RequestCaller.postList(UrlBase.LISTVIEW, new TokenModel(), MenuModel.class, new JsonRequester.OnResponseListener<List<MenuModel>>() {
            @Override
            public void onResponse(List<MenuModel> response, int resCode) {
                if (response != null && netgridView != null) {
                    mMenuModels = response;
                    MenuAdapter menuadapter = new MenuAdapter(getActivity(), response);
                    netgridView.setAdapter(menuadapter);
                }
            }
        });

        RequestCaller.post(UrlBase.GETCOUNT, new TokenModel(), ZhangdanBean2.class, new JsonRequester.OnResponseListener<ZhangdanBean2>() {
            @Override
            public void onResponse(ZhangdanBean2 response, int resCode) {
                if (response != null && total != null) {
                    total.setText(response.getTamount());
                    mouth.setText(response.getMamount());
                    today.setText(response.getDamount());
                }
            }
        });
    }

}
