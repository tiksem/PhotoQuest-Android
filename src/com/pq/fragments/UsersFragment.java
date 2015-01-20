package com.pq.fragments;

import com.pq.R;
import com.pq.adapters.UserListAdapter;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.json.OnAllDataLoaded;

/**
 * Created by CM on 1/20/2015.
 */
public abstract class UsersFragment extends NavigationListFragment<User> {
    @Override
    protected ViewArrayAdapter<User, ?> createAdapter(RequestManager requestManager) {
        return new UserListAdapter(getActivity(), requestManager);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.people;
    }
}
