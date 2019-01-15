package com.zengzhaoxing.browser.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zengzhaoxing.browser.bean.UrlBean;

import com.zengzhaoxing.browser.greendao.UrlBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig urlBeanDaoConfig;

    private final UrlBeanDao urlBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        urlBeanDaoConfig = daoConfigMap.get(UrlBeanDao.class).clone();
        urlBeanDaoConfig.initIdentityScope(type);

        urlBeanDao = new UrlBeanDao(urlBeanDaoConfig, this);

        registerDao(UrlBean.class, urlBeanDao);
    }
    
    public void clear() {
        urlBeanDaoConfig.clearIdentityScope();
    }

    public UrlBeanDao getUrlBeanDao() {
        return urlBeanDao;
    }

}
