package com.pq.fragments;

import com.pq.adapters.DialogsAdapter;
import com.pq.data.DialogInfo;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 2/21/2015.
 */
public class DialogsFragment extends NavigationListFragment<DialogInfo> {
    @Override
    protected ViewArrayAdapter<DialogInfo, ?> createAdapter(RequestManager requestManager) {
        return new DialogsAdapter(getActivity(), requestManager);
    }

    @Override
    protected NavigationList<DialogInfo> getNavigationList(RequestManager requestManager) {
        return requestManager.getDialogs();
    }

    @Override
    protected void onListItemClicked(DialogInfo item) {
        MessagesFragment messagesFragment = MessagesFragment.create(item.userId);
        replaceFragment(messagesFragment, Level.MESSAGES_NAVIGATION);
    }
}
