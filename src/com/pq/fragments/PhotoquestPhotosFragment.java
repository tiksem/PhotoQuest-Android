package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import com.pq.data.GalleryPhoto;
import com.pq.data.PhotoCategory;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 2/18/2015.
 */
public class PhotoquestPhotosFragment extends PhotoGalleryFragment {
    private static final String PHOTOQUEST_ID = "photoquestId";
    private long photoquestId;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        photoquestId = Fragments.getLong(this, PHOTOQUEST_ID, -1);
    }

    public static PhotoquestPhotosFragment create(long photoquestId) {
        PhotoquestPhotosFragment fragment = new PhotoquestPhotosFragment();
        Bundle args = new Bundle();
        args.putLong(PHOTOQUEST_ID, photoquestId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected NavigationList<GalleryPhoto> getNavigationList(RequestManager requestManager) {
        return requestManager.getPhotosOfPhotoquest(photoquestId, getSortMode());
    }

    @Override
    protected void onListItemClicked(GalleryPhoto photo) {
        long photoId = photo.getId();
        Fragment fragment = PhotoquestPhotosPagerFragment.create(photoquestId, photoId, PhotoCategory.all);
        replaceFragment(fragment, Level.PHOTOQUEST_PHOTO);
    }
}
