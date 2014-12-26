package com.pq.fragments;

import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.json.OnAllDataLoaded;

/**
 * Created by CM on 12/26/2014.
 */
public class CreatedPhotoquestsFragment extends PhotoquestsFragment {
    @Override
    protected NavigationList<Photoquest> getNavigationList(RequestManager requestManager,
                                                           OnAllDataLoaded onAllDataLoaded) {
        return requestManager.getCreatedPhotoquestsNavigationList(onAllDataLoaded);
    }
}
