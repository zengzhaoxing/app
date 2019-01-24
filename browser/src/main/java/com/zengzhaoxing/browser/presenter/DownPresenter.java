package com.zengzhaoxing.browser.presenter;

import android.app.Activity;
import android.util.Log;

import com.zengzhaoxing.browser.App;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.greendao.FileBeanDao;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;
import com.zxz.www.base.utils.ToastUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DownPresenter implements Downloader.DownLoadListener {

    private static final int MAX_DOWNLOAD_TASK = 300;


    public List<FileBean> getFileBeans() {
        return mFileBeans;
    }

    private final List<FileBean> mFileBeans;

    private List<Downloader> mDownloaders = new ArrayList<>();

    private List<FileBean> mCurrBean = new ArrayList<>();


    private static DownPresenter ourInstance;

    public static DownPresenter getInstance(Activity activity) {
        if (ourInstance == null) {
            ourInstance = new DownPresenter(activity);
        }
        return ourInstance;
    }

    private Activity mActivity;

    private DownPresenter(Activity activity) {
        mActivity = activity;
        QueryBuilder<FileBean> qb =  App.getDaoSession().queryBuilder(FileBean.class).orderDesc(FileBeanDao.Properties.Time);
        mFileBeans = qb.list();
    }

    public void startNew(FileBean bean) {
        boolean isExist = false;
        int sameFileCount = 0;
        for (FileBean fileBean : mFileBeans) {
            if (fileBean.getName().contains(bean.getPrefix())) {
                if (!fileBean.isComplete()) {
                    isExist = true;
                    break;
                }
                sameFileCount++;
            }
        }

        if (isExist) {
            ToastUtil.toast(R.string.download_exit);
        } else {
            if (sameFileCount > 0) {
                bean.appendName("(" + sameFileCount + ")");
            }
            App.getDaoSession().insertOrReplace(bean);
            mFileBeans.add(0,bean);
            newDownLoad(bean);
        }
    }

    private void newDownLoad(FileBean bean) {
        if (mCurrBean.size() == MAX_DOWNLOAD_TASK) {
            stop(mCurrBean.get(0));
        }
        HttpDownloader httpDownloader = new HttpDownloader(bean.getUrl(),bean.getName(),true,mActivity);
        bean.setDir(httpDownloader.getFileDir());
        httpDownloader.setDownloadListener(this);
        mDownloaders.add(httpDownloader);
        mCurrBean.add(bean);
        httpDownloader.starDownload();
    }


    @Override
    public void onDownLoad(float progress, Downloader downloader) {
        FileBean b = mCurrBean.get(mDownloaders.indexOf(downloader));
        b.setContentLength(downloader.getContentLength());
        b.setDownLoadLength(downloader.getDownLoadLength());
        App.getDaoSession().update(b);
        callUpData(b);
    }

    public interface OnUpDateUiListener {
        void onUpDateUi(FileBean bean);
    }

    public void setOnUpDateUiListener(OnUpDateUiListener onUpDateUiListener) {
        mOnUpDateUiListener = onUpDateUiListener;
    }

    private OnUpDateUiListener mOnUpDateUiListener;

    public boolean isDowning(FileBean bean) {
        return mCurrBean.contains(bean);
    }

    public void exChangeDownStatus(FileBean bean) {
        if (isDowning(bean)) {
            newDownLoad(bean);
        } else {
            stop(bean);
        }
        callUpData(bean);
    }

    private void stop(FileBean bean) {
        int index = mCurrBean.indexOf(bean);
        if (index > 0) {
            mDownloaders.get(index).stopDownload();
            mDownloaders.remove(index);
            mCurrBean.remove(index);
        }
    }

    private void callUpData(FileBean bean) {
        if (mOnUpDateUiListener != null) {
            mOnUpDateUiListener.onUpDateUi(bean);
        }
    }

    public void delete(boolean isDeleteFile,int... pos) {
        List<FileBean> temp = new ArrayList<>();
        if (pos == null || pos.length == 0) {
            temp.addAll(mFileBeans);
        } else {
            for (int i : pos) {
                temp.add(mFileBeans.get(i));
            }
        }
        for (FileBean bean : temp) {
            delete(bean,isDeleteFile);
        }
    }

    public void delete(FileBean bean,boolean isDeleteFile) {
        if (isDeleteFile) {
            bean.deleteFile();
        }
        App.getDaoSession().delete(bean);
        stop(bean);
        mFileBeans.remove(bean);
    }

}
