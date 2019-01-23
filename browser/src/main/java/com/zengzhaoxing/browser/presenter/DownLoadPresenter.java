package com.zengzhaoxing.browser.presenter;

import com.zengzhaoxing.browser.App;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.greendao.FileBeanDao;
import com.zengzhaoxing.browser.greendao.UrlBeanDao;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;
import com.zxz.www.base.utils.ToastUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DownLoadPresenter {

    public List<Downloader> getDownloaders() {
        return mDownloaders;
    }

    private List<Downloader> mDownloaders;

    private static final DownLoadPresenter ourInstance = new DownLoadPresenter();

    public static DownLoadPresenter getInstance() {
        return ourInstance;
    }

    private DownLoadPresenter() {
        if (mDownloaders == null) {
            QueryBuilder<FileBean> qb =  App.getDaoSession().queryBuilder(FileBean.class).orderDesc(FileBeanDao.Properties.Time);
            mDownloaders = new ArrayList<>();
            for (FileBean bean : qb.list()) {
                final HttpDownloader httpDownloader = new HttpDownloader(bean.getUrl(),bean.getName());
                httpDownloader.setDownloadListener(mDownLoadListener);
                mDownloaders.add(httpDownloader);
            }
        }
    }

    public void startNew(FileBean bean) {
        final HttpDownloader httpDownloader = new HttpDownloader(bean.getUrl(),bean.getName());
        bean.setDir(httpDownloader.getFileDir());
        QueryBuilder<FileBean> qb =  App.getDaoSession().queryBuilder(FileBean.class)
                .where(FileBeanDao.Properties.Url.eq(bean.getUrl()),FileBeanDao.Properties.Dir.eq(bean.getDir()),FileBeanDao.Properties.Name.eq(bean.getName()));
        if (qb.list().size() > 0) {
            ToastUtil.toast("下载已存在");
        } else {
            App.getDaoSession().insert(bean);
            httpDownloader.setDownloadListener(mDownLoadListener);
            mDownloaders.add(httpDownloader);
            httpDownloader.starDownload();
        }
    }

    private Downloader.DownLoadListener mDownLoadListener;

    public void setDownLoadListener(Downloader.DownLoadListener downLoadListener) {
        mDownLoadListener = downLoadListener;
    }



}
