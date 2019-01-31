package com.zengzhaoxing.browser.presenter;

import android.app.Activity;
import android.util.Log;

import com.zengzhaoxing.browser.App;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.greendao.FileBeanDao;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;
import com.zxz.www.base.utils.MyTask;
import com.zxz.www.base.utils.ToastUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class DownPresenter{

    private static final int MAX_DOWNLOAD_TASK = 2;

    private MyTask mMyTask = new MyTask();

    public List<FileBean> getFileBeans() {
        return mFileBeans;
    }

    private final List<FileBean> mFileBeans;

    private List<Downloader> mDownloaders = new ArrayList<>();

    private List<FileBean> mCurrBean = new ArrayList<>();


    private static DownPresenter ourInstance;

    public static DownPresenter getInstance() {
        if (ourInstance == null) {
            ourInstance = new DownPresenter();
        }
        return ourInstance;
    }

    private Activity mActivity;

    private DownPresenter() {
        QueryBuilder<FileBean> qb =  App.getDaoSession().queryBuilder(FileBean.class).orderDesc(FileBeanDao.Properties.Time);
        mFileBeans = qb.list();
        mMyTask.start(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mDownloaders.size(); i++) {
                    FileBean b = mCurrBean.get(i);
                    Downloader downloader = mDownloaders.get(i);
                    b.setDownLoadLength(downloader.getDownLoadLength());
                    App.getDaoSession().update(b);
                }
                callUpData(null);
            }
        },0,1000);
    }

    public void startNew(FileBean bean) {
        boolean isExist = false;
        for (FileBean fileBean : mFileBeans) {
            if (fileBean.getName().contains(bean.getPrefix())) {
                if (!fileBean.isComplete()) {
                    isExist = true;
                    break;
                }
            }
        }

        if (isExist) {
            ToastUtil.toast(R.string.download_exit);
        } else {
            int sameFileCount = 0;
            File file = new File(bean.getDir());
            for (File file1 : file.listFiles()) {
                if (!file1.isDirectory() && file1.getName().contains(bean.getPrefix())) {
                    sameFileCount++;
                }
            }
            if (sameFileCount > 0) {
                bean.appendName("(" + sameFileCount + ")");
            }
            App.getDaoSession().insertOrReplace(bean);
            mFileBeans.add(0,bean);
            newDownLoad(bean);
            ToastUtil.toast(R.string.downloading);
        }
    }

    private void newDownLoad(FileBean bean) {
        if (mCurrBean.size() == MAX_DOWNLOAD_TASK) {
            stop(mCurrBean.get(0));
        }
        HttpDownloader httpDownloader = new HttpDownloader(bean.getUrl(),bean.getName(),true,mActivity);
        mDownloaders.add(httpDownloader);
        mCurrBean.add(bean);
        httpDownloader.setContentLength(bean.getContentLength());
        httpDownloader.starDownload();
    }

    public interface OnUpDateUiListener {
        void onUpDateUi(FileBean bean);

        void onDelete(int... pos);
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
            stop(bean);
        } else {
            newDownLoad(bean);
        }
        callUpData(null);
    }

    private void stop(FileBean bean) {
        int index = mCurrBean.indexOf(bean);
        if (index >= 0) {
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
        if (mOnUpDateUiListener != null) {
            mOnUpDateUiListener.onDelete(pos);
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

    public void reDown(FileBean bean) {
        delete(bean, true);
        startNew(bean);
    }

}
