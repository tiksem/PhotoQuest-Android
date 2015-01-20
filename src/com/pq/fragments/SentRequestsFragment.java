package com.pq.fragments;

import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.json.OnAllDataLoaded;

/**
 * Created by CM on 1/20/2015.
 */
public class SentRequestsFragment extends UsersFragment {
    @Override
    protected NavigationList<User> getNavigationList(RequestManager requestManager, OnAllDataLoaded onAllDataLoaded) {
        return requestManager.getSentRequestsNavigationList(onAllDataLoaded);
    }
}
