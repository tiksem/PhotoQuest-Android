package com.pq.fragments;

import com.pq.adapters.PhotoquestsAdapter;
import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class PhotoquestsFragment extends NavigationListFragment<Photoquest> {
    @Override
    protected ViewArrayAdapter<Photoquest, ?> createAdapter(RequestManager requestManager) {
        return new PhotoquestsAdapter(getActivity(), requestManager);
    }

    @Override
    protected void onListItemClicked(Photoquest photoquest) {
        PhotoquestPhotosFragment fragment = PhotoquestPhotosFragment.create(photoquest.getId());
        replaceFragment(fragment, Level.PHOTOS_NAVIGATION);
    }
}
