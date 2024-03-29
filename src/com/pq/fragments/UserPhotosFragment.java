package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import com.pq.R;
import com.pq.data.GalleryPhoto;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.navigation.ActionBarTitleProvider;

/**
 * Created by CM on 2/25/2015.
 */
public class UserPhotosFragment extends PhotoGalleryFragment implements ActionBarTitleProvider {
    private static final String USER_ID = "userId";
    private long userId;
    private UserActionBarTitleProvider titleProvider;

    public static UserPhotosFragment create(long userId) {
        UserPhotosFragment fragment = new UserPhotosFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userId = Fragments.getLong(this, USER_ID, -1);
        titleProvider = new UserActionBarTitleProvider(userId, getRequestManager(), this, R.string.photos);
        titleProvider.updateUser();
    }

    @Override
    protected NavigationList<GalleryPhoto> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getPhotosOfUser(userId, getSortMode());
    }

    @Override
    protected void onListItemClicked(GalleryPhoto photo) {
        long photoId = photo.getId();
        Fragment fragment = UserPhotosPagerFragment.create(userId, photoId,
                getSortMode());
        replaceFragment(fragment, Level.USER_PHOTO);
    }

    @Override
    public String getActionBarTitle() {
        return titleProvider.getActionBarTitle();
    }
}
