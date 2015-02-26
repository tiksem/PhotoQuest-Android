package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
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
import com.utilsframework.android.json.OnSuccess;

/**
 * Created by CM on 1/20/2015.
 */
public class ProfileFragment extends NavigationListFragment<Feed> {
    private static final String USER_ID = "userId";
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

    private void openPhotos() {
        UserPhotosFragment fragment = UserPhotosFragment.create(userId);
        replaceFragment(fragment, Level.USER_PHOTOS);
    }

    private void openPhotoquests() {
        Fragment fragment = PhotoquestsFragment.create(getActivity(), userId,
                CreatedPhotoquestsFragment.class);
        replaceFragment(fragment, Level.USER_PHOTOQUESTS);
    }

    private void addFriend() {

    }

    private void writeMessage() {
        Fragment fragment = MessagesFragment.create(userId);
        replaceFragment(fragment, Level.WRITE_MESSAGE);
    }

    private View createHeader(ImageUrlProvider imageUrlProvider, User user) {
        View header = View.inflate(getActivity(), R.layout.profile_header, null);
        ImageView avatar = (ImageView) header.findViewById(R.id.avatar);
        TextView userName = (TextView) header.findViewById(R.id.name);

        Images.displayAvatar(imageUrlProvider, avatar, user.getAvatarId());
        userName.setText(user.getName() + " " + user.getLastName());

        header.findViewById(R.id.photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotos();
            }
        });

        header.findViewById(R.id.photoquests).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoquests();
            }
        });

        header.findViewById(R.id.writeMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeMessage();
            }
        });

        header.findViewById(R.id.addFriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });

        return header;
    }

    @Override
    protected ViewArrayAdapter<Feed, ?> createAdapter(final RequestManager requestManager) {
        Activity activity = getActivity();
        final FeedAdapter feedAdapter = new FeedAdapter(activity, null, requestManager);

        final User signedInUser = requestManager.getSignedInUser();
        if (signedInUser.getId().equals(userId) || userId < 0) {
            View header = createHeader(requestManager, signedInUser);
            feedAdapter.setHeader(header);
        } else {
            requestManager.getUserById(userId, new OnSuccess<User>() {
                @Override
                public void onSuccess(User user) {
                    View header = createHeader(requestManager, user);
                    feedAdapter.setHeader(header);
                }
            });
        }

        return feedAdapter;
    }

    @Override
    protected NavigationList<Feed> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getUserActivity(userId);
    }

    @Override
    protected void onListItemClicked(Feed item) {

    }
}
