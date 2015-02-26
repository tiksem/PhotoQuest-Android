package com.pq.fragments;

import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;

/**
 * Created by CM on 12/26/2014.
 */
public class CreatedPhotoquestsFragment extends PhotoquestsFragment {
    @Override
    protected NavigationList<Photoquest> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getCreatedPhotoquests(userId, getSortMode(), filter);
    }
}
