package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.navigation.ActionBarTitleProvider;

/**
 * Created by CM on 1/20/2015.
 */
public class FriendsFragment extends UsersFragment implements ActionBarTitleProvider {
    private static final String USER_ID = "userId";
    private long userId;
    private UserActionBarTitleProvider titleProvider;

    public static FriendsFragment create(long userId) {
        FriendsFragment fragment = new FriendsFragment();
        if (userId > 0) {
            Bundle args = new Bundle();
            args.putLong(USER_ID, userId);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userId = Fragments.getLong(this, USER_ID, -1);
        titleProvider = new UserActionBarTitleProvider(userId, getRequestManager(), this, R.string.friends);
        titleProvider.updateUser();
    }

    @Override
    protected NavigationList<User> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getFriends(getSortMode(), filter, userId);
    }

    @Override
    public String getActionBarTitle() {
        return titleProvider.getActionBarTitle();
    }
}
