package com.pq.fragments;

import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;

/**
 * Created by CM on 12/26/2014.
 */
public class PeopleFragment extends UsersFragment {
    @Override
    protected NavigationList<User> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getUsers(getSortMode(), filter);
    }
}
