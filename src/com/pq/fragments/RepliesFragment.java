package com.pq.fragments;

import com.pq.adapters.RepliesAdapter;
import com.pq.data.Reply;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 2/21/2015.
 */
public class RepliesFragment extends NavigationListFragment<Reply> {
    @Override
    protected ViewArrayAdapter<Reply, ?> createAdapter(RequestManager requestManager) {
        return new RepliesAdapter(getActivity(), requestManager);
    }

    @Override
    protected NavigationList<Reply> getNavigationList(RequestManager requestManager, String filter) {
        return requestManager.getReplies();
    }

    @Override
    protected void onListItemClicked(Reply item) {

    }
}
