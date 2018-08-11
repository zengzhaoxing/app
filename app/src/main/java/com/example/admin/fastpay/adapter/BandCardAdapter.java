package com.example.admin.fastpay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.CreditCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 曾宪梓 on 2018/1/6.
 */

public class BandCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    List<CreditCard> mCards;

    public BandCardAdapter(List<CreditCard> cards) {
        mCards = cards;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CreditCard card = mCards.get(position);
        viewHolder.mBankNameTv.setText(card.getBank_name());
        viewHolder.mCardNoTv.setText(card.getLastNo(4));
    }

    @Override
    public int getItemCount() {
        return mCards == null ? 0 : mCards.size();
    }

    @Override
    public void onClick(View v) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bank_iv)
        ImageView mBankIv;
        @BindView(R.id.bank_name_tv)
        TextView mBankNameTv;
        @BindView(R.id.card_no_tv)
        TextView mCardNoTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
