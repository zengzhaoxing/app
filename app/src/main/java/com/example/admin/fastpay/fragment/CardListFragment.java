package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.CreditCard;
import com.example.admin.fastpay.model.response.BindCardResp;
import com.example.admin.fastpay.model.response.CreditCardList;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/6.
 */

public class CardListFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.card_rv)
    ListView mCardRv;
    Unbinder unbinder;

    List<CreditCard> mCards;

    private BaseAdapter mAdapter;

    private CreditCard mCard;

    private static final String CAN_SELECT = "select";

    public static void showFragment(BaseActivity activity,boolean canSelect) {
        CardListFragment listFragment = new CardListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(CAN_SELECT, canSelect);
        listFragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(listFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mCards == null ? 0 : mCards.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mBaseActivity).inflate(R.layout.row_card, parent, false);
                }
                CreditCard card = mCards.get(position);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.delete_iv);
                ((TextView) convertView.findViewById(R.id.bank_name_tv)).setText(card.getBank_name());
                ((TextView) convertView.findViewById(R.id.card_no_tv)).setText(card.getLastNo(4));
                imageView.setOnClickListener(CardListFragment.this);
                imageView.setTag(position);
                return convertView;
            }
        };
        boolean b = false;
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            b = bundle.getBoolean(CAN_SELECT);
        }
        if (b) {
            mCardRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCard = mCards.get(position);
                    goBack();
                }
            });
        }
        mCardRv.setAdapter(mAdapter);
        loadCards();
        return view;
    }

    private void loadCards() {
        RequestCaller.post(UrlBase.CREDIT_CARD, null, CreditCardList.class, new JsonRequester.OnResponseListener<CreditCardList>() {
            @Override
            public void onResponse(CreditCardList response, int resCode) {
                if (response != null) {
                    mCards = response.getData();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_sll)
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.add_sll:
                mBaseActivity.openNewFragmentWithDefaultAnim(new CreditCardFragment());
                break;
        }
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        super.onTopFragmentExit(topFragmentClass, params);
        if (topFragmentClass == CreditCardFragment.class) {
            loadCards();
        }
    }

    @Override
    protected Bundle onExit() {
        if (mCard == null) {
            return null;
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(CreditCard.class.getName(),mCard);
            return bundle;
        }
    }


    @Override
    public void onClick(View v) {
        final int p = (int) v.getTag();
        RequestCaller.delete(UrlBase.CREDIT_CARD + "/" + mCards.get(p).getId(), null, BindCardResp.class, new JsonRequester.OnResponseListener<BindCardResp>() {
            @Override
            public void onResponse(BindCardResp response, int resCode) {
                if (response != null) {
                    if (response.isSuccess()) {
                        loadCards();
                    }
                    ToastUtil.toast(response.getMessage());
                }
            }
        });


    }
}
