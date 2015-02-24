package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import com.pq.data.GalleryPhoto;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 2/25/2015.
 */
public class UserPhotosFragment extends PhotoGalleryFragment {
    private static final String USER_ID = "userId";
    private long userId;

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
    }

    @Override
    protected NavigationList<GalleryPhoto> getNavigationList(RequestManager requestManager) {
        return requestManager.getPhotosOfUser(userId, getSortMode());
    }

    @Override
    protected void onListItemClicked(GalleryPhoto item) {

    }
}
