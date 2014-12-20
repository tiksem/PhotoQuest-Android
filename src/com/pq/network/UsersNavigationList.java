package com.pq.network;

import android.os.Handler;
import com.pq.data.User;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.threading.OnFinish;

import java.util.List;

/**
 * Created by CM on 12/21/2014.
 */
class UsersNavigationList extends NavigationList<User> {
    private RequestManager requestManager;
    private int pageSize = 10;

    public UsersNavigationList(RequestManager requestManager) {
        super(500);
        this.requestManager = requestManager;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected void getElementsOfPage(int pageNumber, final OnPageLoadingFinished<User> onPageLoadingFinished) {
        requestManager.getUsersAsync(pageNumber * pageSize, pageSize,
                new OnFinish<List<User>>() {
            @Override
            public void onFinish(List<User> users) {
                onPageLoadingFinished.onLoadingFinished(users, users.size() < pageSize);
            }
        });
    }
}
