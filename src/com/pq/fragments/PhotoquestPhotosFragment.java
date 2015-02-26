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
    private static final String CATEGORY = "category";
    private static final String PHOTO_LEVEL = "photoLevel";
    private long photoquestId;
    private PhotoCategory category;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        photoquestId = Fragments.getLong(this, PHOTOQUEST_ID, -1);
        category = PhotoCategory.values()[Fragments.getInt(this, CATEGORY, 0)];
    }

    public static PhotoquestPhotosFragment create(long photoquestId, PhotoCategory category, int photoLevel) {
        PhotoquestPhotosFragment fragment = new PhotoquestPhotosFragment();

        Bundle args = new Bundle();
        args.putLong(PHOTOQUEST_ID, photoquestId);
        args.putInt(CATEGORY, category.ordinal());
        args.putInt(PHOTO_LEVEL, photoLevel);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected NavigationList<GalleryPhoto> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getPhotosOfPhotoquest(photoquestId, category, getSortMode());
    }

    @Override
    protected void onListItemClicked(GalleryPhoto photo) {
        long photoId = photo.getId();
        int level = Fragments.getInt(this, PHOTO_LEVEL);
        Fragment fragment = PhotoquestPhotosPagerFragment.create(photoquestId, photoId, category,
                getSortMode());
        replaceFragment(fragment, level);
    }

    public long getPhotoquestId() {
        return photoquestId;
    }
}
