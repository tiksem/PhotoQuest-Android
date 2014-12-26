package com.pq.fragments;

import android.app.Fragment;
import com.pq.R;
import com.pq.adapters.PhotoquestsAdapter;
import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.json.OnAllDataLoaded;

/**
 * Created by CM on 12/26/2014.
 */
public class PhotoquestsFragment extends NavigationListFragment<Photoquest> {
    @Override
    protected ViewArrayAdapter<Photoquest, ?> createAdapter(RequestManager requestManager) {
        return new PhotoquestsAdapter(getActivity(), requestManager);
    }

    @Override
    protected NavigationList<Photoquest> getNavigationList(RequestManager requestManager,
                                                           OnAllDataLoaded onAllDataLoaded) {
        return requestManager.getPhotoquestsNavigationList(onAllDataLoaded);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.photoquests;
    }
}
