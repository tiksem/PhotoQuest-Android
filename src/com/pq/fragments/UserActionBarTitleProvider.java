package com.pq.fragments;

import com.pq.PhotoquestUtilities;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.navigation.ActionBarTitleProvider;

/**
 * Created by CM on 3/5/2015.
 */
public class UserActionBarTitleProvider implements ActionBarTitleProvider {
    private long userId;
    private RequestManager requestManager;
    private User user;
    private NavigationDrawerFragment fragment;
    private int whatId;

    public void updateUser() {
        if (userId > 0) {
            requestManager.getUserById(userId, new OnSuccess<User>() {
                @Override
                public void onSuccess(User result) {
                    user = result;
                    fragment.updateActionBarTitle();
                }
            });
        }
    }

    public UserActionBarTitleProvider(long userId, RequestManager requestManager,
                                      NavigationDrawerFragment fragment, int whatId) {
        this.userId = userId;
        this.requestManager = requestManager;
        this.fragment = fragment;
        this.whatId = whatId;
    }

    @Override
    public String getActionBarTitle() {
        if(user == null){
            return null;
        }

        return PhotoquestUtilities.of(user.getName(), whatId);
    }
}
