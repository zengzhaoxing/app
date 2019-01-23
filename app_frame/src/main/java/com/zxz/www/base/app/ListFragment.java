package com.zxz.www.base.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxz.www.base.R;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.view.TitleView;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class ListFragment<T,VH extends RecyclerView.ViewHolder> extends TitleFragment implements View.OnLongClickListener, View.OnClickListener {

    protected RecyclerView mRecyclerView;

    protected RecyclerView.Adapter mAdapter;

    public void setList(List<T> list) {
        mList = list;
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected List<T> mList;

    @Override
    public int getContentLayoutId() {
        return R.layout.bs_fra_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerView.Adapter<VH>() {
            @Override
            public VH onCreateViewHolder(ViewGroup parent, int viewType) {
                VH vh = null;
                try {
                    Constructor<VH> c = null;
                    View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
                    view.setOnClickListener(ListFragment.this);
                    view.setOnLongClickListener(ListFragment.this);
                    c = getVHClass().getDeclaredConstructor(View.class);
                    c.setAccessible(true);
                    vh = c.newInstance(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vh;
            }

            @Override
            public void onBindViewHolder(VH vh, int position) {
                vh.itemView.setTag(mList.get(position));
                ListFragment.this.onBindViewHolder(vh,mList.get(position),position);
            }

            @Override
            public int getItemCount() {
                return mList != null ? mList.size() : 0;
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    private Class<VH> getVHClass() {
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[1];
        return (Class<VH>) type;
    }

    protected abstract void onBindViewHolder(VH vh, T t,int position);

    protected abstract @LayoutRes
    int getItemLayoutId();

    @Override
    public boolean onLongClick(View v) {
        return true;
    }

    @Override
    public void onClick(View v) {

    }


}
