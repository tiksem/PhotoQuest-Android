package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import com.pq.R;
import com.pq.adapters.FeedAdapter;
import com.pq.data.Feed;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utils.framework.collections.OnAllDataLoaded;

/**
 * Created by CM on 1/20/2015.
 */
public class ProfileFragment extends NavigationListFragment<Feed> {
    private static final String USER_ID = "userId";

    public ProfileFragment() {
    }

    public static ProfileFragment create(long userId) {
        ProfileFragment fragment = new ProfileFragment();
        if (userId > 0) {
            Bundle args = new Bundle();
            args.putLong(USER_ID, userId);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    protected ViewArrayAdapter<Feed, ?> createAdapter(RequestManager requestManager) {
        Activity activity = getActivity();
        //View header = View.inflate(activity, R.layout.profile_header, null);
        return new FeedAdapter(activity, null, requestManager);
    }

    @Override
    protected NavigationList<Feed> getNavigationList(RequestManager requestManager) {
        long userId = Fragments.getLong(this, USER_ID, -1);
        return requestManager.getUserActivity(userId);
    }
}
