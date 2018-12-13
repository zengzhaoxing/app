package com.zxz.www.base.app;

import android.os.Bundle;

import com.zxz.www.base.app.UIPage;

import java.util.Stack;

public abstract class  UIRouter {

    protected Stack<UIPage> mUIPages = new Stack<>();

    public int getPageCount() {
        return mPageCount;
    }

    public boolean isReady() {
        return mIsReady;
    }

    private int mPageCount = 0;

    private boolean mIsReady;

    public abstract void openPage(Class<? extends UIPage> clz, Bundle bundle);

    public final void closeCurrentPage() {
        mUIPages.pop();
        closeCurrentPageImpl();
    }

    protected abstract void closeCurrentPageImpl();

    public final UIPage getCurrentPage(){
        return mUIPages.peek();
    }

    public UIPage getPage(int index){
        if (index >= mUIPages.size()) {
            return null;
        }
        return mUIPages.get(index);
    }

    public final void goToFirstPage(){
        while (mUIPages.size() > 1) {
            closeCurrentPage();
        }
    }

    public void backToPage(Class<? extends UIPage> clz){
        UIPage page = getCurrentPage();
        while (page != null && page.getClass() != clz) {
            closeCurrentPage();
        }
    }

    void onRouterReady(UIPage uiPage) {
        mUIPages.push(uiPage);
        mIsReady = true;
        mPageCount = 1;
    }

    void onRouterDestroy() {
        mUIPages.removeAllElements();
        mIsReady = false;
        mPageCount = 0;
    }

    public final void notifyPage(Bundle bundle,UIPage... uiPages) {
        for (UIPage uiPage : uiPages) {
            uiPage.onNotify(bundle);
        }
    }

    public final void notifyPage(Bundle bundle,int... ints) {
        for (int i : ints) {
            getPage(i).onNotify(bundle);
        }
    }


}
