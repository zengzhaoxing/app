package com.zengzhaoxing.browser;

import com.zengzhaoxing.browser.greendao.DaoMaster;
import com.zengzhaoxing.browser.greendao.DaoSession;
import com.zengzhaoxing.browser.view.web.ActivityLifeCycle;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKAgent;

import org.greenrobot.greendao.database.Database;

public class App extends BaseApp {

    static {
        SDKAgent.getInstance().addSDKConnector(ActivityLifeCycle.getInstance());
    }

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DaoMaster.class.getName());
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }



}
