package com.pq.fragments;

import com.pq.R;
import com.pq.adapters.PhotoGalleryAdapter;
import com.pq.data.GalleryPhoto;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 2/18/2015.
 */
public abstract class PhotoGalleryFragment extends NavigationListFragment<GalleryPhoto> {
    @Override
    protected ViewArrayAdapter<GalleryPhoto, ?> createAdapter(RequestManager requestManager) {
        return new PhotoGalleryAdapter(getActivity(), requestManager);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.navigation_grid;
    }

    @Override
    protected void onListItemClicked(GalleryPhoto photo) {
        PhotoFragment fragment = PhotoFragment.create(photo.getId());
        replaceFragment(fragment, Level.PHOTOQUEST_PHOTO);
    }
}
