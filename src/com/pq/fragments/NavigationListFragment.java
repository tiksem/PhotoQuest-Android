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
import com.utilsframework.android.adapters.AdapterUtils;
import com.utilsframework.android.adapters.ListViewNavigationParams;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utils.framework.collections.OnAllDataLoaded;

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

        final ViewArrayAdapter<T, ?> adapter = createAdapter(requestManager);
        NavigationList<T> elements = getNavigationList(requestManager);

        ListViewNavigationParams<T> params = new ListViewNavigationParams<T>();
        params.viewArrayAdapter = adapter;
        params.navigationList = elements;
        params.rootView = view;
        params.listViewId = getListResourceId();
        params.loadingViewId = getLoadingResourceId();
        AdapterUtils.initListViewNavigation(params);
    }

    protected int getListResourceId() {
        return R.id.list;
    }

    protected int getLoadingResourceId() {
        return R.id.loading;
    }

    protected abstract ViewArrayAdapter<T, ? extends Object> createAdapter(RequestManager requestManager);
    protected abstract NavigationList<T> getNavigationList(RequestManager requestManager);

    protected int getRootLayout() {
        return R.layout.navigation_list;
    }
}
