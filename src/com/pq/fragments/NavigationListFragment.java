package com.pq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.json.OnAllDataLoaded;

import java.util.List;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class NavigationListFragment<T> extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getRootLayout(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestManager requestManager = PhotoQuestApp.getInstance().getRequestManager();

        AbsListView usersListView = (AbsListView) view.findViewById(getListResourceId());
        final ViewArrayAdapter<T, ?> adapter = createAdapter(requestManager);
        List<T> elements = getNavigationList(requestManager, new OnAllDataLoaded() {
            @Override
            public void onAllDataLoaded() {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setElements(elements);
        usersListView.setAdapter(adapter);
    }

    protected int getListResourceId() {
        return R.id.list;
    }

    protected abstract ViewArrayAdapter<T, ? extends Object> createAdapter(RequestManager requestManager);
    protected abstract NavigationList<T> getNavigationList(RequestManager requestManager,
                                                           OnAllDataLoaded onAllDataLoaded);
    protected abstract int getRootLayout();
}
