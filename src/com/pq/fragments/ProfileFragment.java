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
import com.pq.network.ImageUrlProvider;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.threading.Threading;

/**
 * Created by CM on 1/20/2015.
 */
public class ProfileFragment extends NavigationListFragment<Feed> {
    private static final String USER_ID = "userId";
    private static final int AVATAR_SIZE = 100;
    private long userId;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userId = Fragments.getLong(this, USER_ID, -1);
    }

    private View createHeader(ImageUrlProvider imageUrlProvider, User user) {
        View header = View.inflate(getActivity(), R.layout.profile_header, null);
        ImageView avatar = (ImageView) header.findViewById(R.id.avatar);
        TextView userName = (TextView) header.findViewById(R.id.name);

        Images.displayAvatar(imageUrlProvider, avatar, user.getAvatarId());
        userName.setText(user.getName() + " " + user.getLastName());

        return header;
    }

    @Override
    protected ViewArrayAdapter<Feed, ?> createAdapter(RequestManager requestManager) {
        Activity activity = getActivity();
        FeedAdapter feedAdapter = new FeedAdapter(activity, null, requestManager);

        User signedInUser = requestManager.getSignedInUser();
        if (signedInUser.getId().equals(userId)) {
            View header = createHeader(requestManager, signedInUser);
            feedAdapter.setHeader(header);
        } else {

        }

        return feedAdapter;
    }

    @Override
    protected NavigationList<Feed> getNavigationList(RequestManager requestManager) {
        return requestManager.getUserActivity(userId);
    }

    @Override
    protected void onListItemClicked(Feed item) {

    }
}
