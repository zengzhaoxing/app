package com.zengzhaoxing.browser.presenter;

import android.util.Log;

import com.zengzhaoxing.browser.App;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.greendao.DaoSession;
import com.zengzhaoxing.browser.greendao.UrlBeanDao;
import com.zxz.www.base.utils.ToastUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UrlCollectPresenter {

    private static final UrlCollectPresenter ourInstance = new UrlCollectPresenter();

    public static UrlCollectPresenter getInstance() {
        return ourInstance;
    }

    private UrlCollectPresenter() {
    }

    public void addCollect(UrlBean urlBean) {
        UrlBean bean = urlBean.copy();
        bean.setCollect(true);
        bean.setTime(System.currentTimeMillis());
        QueryBuilder<UrlBean> qb =  App.getDaoSession().queryBuilder(UrlBean.class)
                .where(UrlBeanDao.Properties.IsCollect.eq(true),UrlBeanDao.Properties.Url.eq(urlBean.getUrl()));
        if (qb.list().size() == 0) {
            App.getDaoSession().insert(bean);
            ToastUtil.toast("收藏成功");
        } else {
            ToastUtil.toast("收藏已存在");
        }
    }

    public void addHistory(UrlBean urlBean) {
        urlBean.setCollect(false);
        urlBean.setTime(System.currentTimeMillis());
        App.getDaoSession().insertOrReplace(urlBean);
    }

    public void delete(UrlBean urlBean) {
        App.getDaoSession().delete(urlBean);
    }

    public List<UrlBean> getAllCollect() {
        QueryBuilder<UrlBean> qb =  App.getDaoSession().queryBuilder(UrlBean.class)
                .where(UrlBeanDao.Properties.IsCollect.eq(true)).orderDesc(UrlBeanDao.Properties.Time);
        if (qb != null && qb.count() > 0) {
            return qb.list();
        }
        return null;
    }

    public List<UrlBean> getAllHistory() {
        QueryBuilder<UrlBean> qb =  App.getDaoSession().queryBuilder(UrlBean.class)
                .where(UrlBeanDao.Properties.IsCollect.eq(false)).orderDesc(UrlBeanDao.Properties.Time);
        if (qb != null && qb.count() > 0) {
            return qb.list();
        }
       return null;
    }

    public void deleteAllCollect() {
        List<UrlBean> beans = getAllCollect();
        if (beans != null) {
            for (UrlBean bean : beans) {
                delete(bean);
            }
        }
    }

    public void deleteAllHistory() {
        List<UrlBean> beans = getAllHistory();
        if (beans != null) {
            for (UrlBean bean : beans) {
                delete(bean);
            }
        }
    }




}
