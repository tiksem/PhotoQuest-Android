package com.pq.fragments;

import com.pq.adapters.FeedAdapter;
import com.pq.data.Feed;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 2/17/2015.
 */
public class NewsFragment extends NavigationListFragment<Feed> {
    @Override
    protected ViewArrayAdapter<Feed, ?> createAdapter(RequestManager requestManager) {
        return new FeedAdapter(getActivity(), requestManager);
    }

    @Override
    protected NavigationList<Feed> getNavigationList(RequestManager requestManager) {
        return requestManager.getNews();
    }

    @Override
    protected void onListItemClicked(Feed item) {

    }
}
