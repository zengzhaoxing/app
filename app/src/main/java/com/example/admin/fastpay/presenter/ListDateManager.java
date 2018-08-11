package com.example.admin.fastpay.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.Name;
import com.example.admin.fastpay.model.request.CityReqModel;
import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.City;
import com.example.admin.fastpay.model.response.Province;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class ListDateManager {

    private static RequesterFactory mRequesterFactory = RequesterFactory.getDefaultRequesterFactory();

    private static List<City> getCities(String provinceCode) {
        return mCityMap.get(provinceCode);
    }

    private static List<Province> mProvinces ;

    private static HashMap<String,List<City>> mCityMap = new HashMap<>();

    private static List<Bank> mBanks;

    static void requestLocation() {
        mCityMap.clear();
        JsonRequester requester = mRequesterFactory.createGetRequesterList(UrlBase.PROVINCE_URL, null, Province.class);
        requester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
        requester.setListener(new JsonRequester.OnResponseListener<List<Province>>() {
            @Override
            public void onResponse(List<Province> provinces, int resCode) {
                if (provinces != null) {
                    mProvinces = provinces;
                    for (final Province province : mProvinces) {
                        CityReqModel model = new CityReqModel(province.getAreaCode());
                        JsonRequester requester = mRequesterFactory.createGetRequesterList(UrlBase.CITY_URL, model, City.class);
                        requester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
                        requester.setListener(new JsonRequester.OnResponseListener<List<City>>() {
                            @Override
                            public void onResponse(List<City> city, int resCode) {
                                if (city != null) {
                                    mCityMap.put(province.getAreaCode(), city);
                                }
                            }
                        });
                        requester.startRequest();
                    }
                }
            }
        });
        requester.startRequest();
    }

    static void requestBankList() {
        JsonRequester requester = mRequesterFactory.createGetRequesterList(UrlBase.BINK_URL, null, Bank.class);
        requester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
        requester.setListener(new JsonRequester.OnResponseListener<List<Bank>>() {
            @Override
            public void onResponse(List<Bank> response, int resCode) {
                if (resCode == 200) {
                    mBanks = response;
                }
            }
        });
        requester.startRequest();
    }

    static void clearList() {
        mProvinces = null;
        if (mCityMap != null) {
            mCityMap.clear();
        }
        mBanks = null;
    }

    public static void showProvinceList(Activity activity, final OnProvinceSelectListener listener) {
        if (mProvinces == null || mProvinces.size() == 0) {
            requestLocation();
        }
        showName(activity, mProvinces, "请选择省", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onSelect(mProvinces.get(which));
                }
            }
        });
    }

    public static void showCityList(Activity activity, final OnCitySelectListener listener,Province province) {
        if (province == null) {
            return;
        }
        final List<City> cities = getCities(province.getAreaCode());
        showName(activity,cities , "请选择市", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onSelect(cities.get(which));
                }
            }
        });
    }

    public static void showBankList(Activity activity, final OnBankSelectListener listener) {
        if (mBanks == null || mBanks.size() == 0) {
            requestBankList();
        }
        showName(activity,mBanks , ResUtil.getString(R.string.bank_name_hint), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onSelect(mBanks.get(which));
                }
            }
        });
    }

    private static void showName(final Activity activity, final List<? extends Name> names, String title,DialogInterface.OnClickListener listener) {
        if (names == null || names.size() == 0) {
            title = "数据未加载完成，请稍后再试！";
        }
        new AlertDialog.Builder(activity).setTitle(title).setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return names != null ? names.size() : 0;
            }

            @Override
            public Object getItem(int position) {
                return names.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView;
                if (convertView instanceof TextView) {
                    textView = (TextView) convertView;
                } else {
                    textView = new TextView(activity);
                }
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(DensityUtil.dip2px(15),0,0,0);
                textView.setTextSize(15);
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(40)));
                textView.setTextColor(ResUtil.getColor(R.color.text_black));
                textView.setText(names.get(position).getName());
                return textView;
            }
        }, listener).show();
    }

    public interface OnProvinceSelectListener {
        void onSelect(Province province);
    }

    public interface OnCitySelectListener {
        void onSelect(City city);
    }

    public interface OnBankSelectListener {
        void onSelect(Bank bank);
    }



}
