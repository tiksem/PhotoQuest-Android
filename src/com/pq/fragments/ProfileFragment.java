package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pq.R;
import com.pq.adapters.FeedAdapter;
import com.pq.data.Feed;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 1/20/2015.
 */
public class ProfileFragment extends NavigationListFragment<Feed> {
    private static final String USER_ID = "userId";
    private static final int AVATAR_SIZE = 100;

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

        View header = View.inflate(activity, R.layout.profile_header, null);
        ImageView avatar = (ImageView) header.findViewById(R.id.avatar);
        User signedInUser = requestManager.getSignedInUser();
        Images.displayAvatar(requestManager, avatar, signedInUser.getAvatarId());
        TextView userName = (TextView) header.findViewById(R.id.name);
        userName.setText(signedInUser.getName() + " " + signedInUser.getLastName());

        FeedAdapter feedAdapter = new FeedAdapter(activity, null, requestManager);
        feedAdapter.setHeader(header);
        return feedAdapter;
    }

    @Override
    protected NavigationList<Feed> getNavigationList(RequestManager requestManager) {
        long userId = Fragments.getLong(this, USER_ID, -1);
        return requestManager.getUserActivity(userId);
    }
}
