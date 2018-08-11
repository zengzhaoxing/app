package com.example.admin.fastpay.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.admin.fastpay.R;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/2/6.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.video_view)
    VideoView mVideoView;
    Unbinder unbinder;

    private static final String URL = "url";

    private static final String TITLE = "title";

    public static void showFragment(BaseActivity activity, String url, String title) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String url = bundle.getString(URL);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mTitleTv.setText(bundle.getString(TITLE));
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        mVideoView.stopPlayback();
        return super.handleBackEvent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
