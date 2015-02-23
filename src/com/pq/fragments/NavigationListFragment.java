package com.pq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.adapters.AdapterUtils;
import com.utilsframework.android.adapters.ListViewNavigationParams;
import com.utilsframework.android.adapters.ViewArrayAdapter;

import java.io.IOException;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class NavigationListFragment<T> extends NavigationDrawerFragment {
    private IOErrorListener ioErrorListener;
    private RequestManager requestManager;
    private ViewArrayAdapter<T, ?> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getRootLayout(), null);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = PhotoQuestApp.getInstance().getRequestManager();

        adapter = createAdapter(requestManager);
        final NavigationList<T> elements = getNavigationList(requestManager);

        ListViewNavigationParams<T> params = new ListViewNavigationParams<T>();
        params.viewArrayAdapter = adapter;
        params.navigationList = elements;
        params.rootView = view;
        params.listViewId = getListResourceId();
        params.loadingViewId = getLoadingResourceId();
        AdapterUtils.initListViewNavigation(params);

        final AbsListView listView = (AbsListView) view.findViewById(getListResourceId());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T item = adapter.getElementOfView(view);
                if (item != null) {
                    onListItemClicked(item);
                }
            }
        });

        ioErrorListener = new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                listView.setVisibility(View.INVISIBLE);
                view.findViewById(getLoadingResourceId()).setVisibility(View.INVISIBLE);
                view.findViewById(getNoInternetConnectionViewId()).setVisibility(View.VISIBLE);
            }
        };
        requestManager.addIOErrorListener(ioErrorListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestManager.removeIOErrorListener(ioErrorListener);
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public ViewArrayAdapter<T, ?> getAdapter() {
        return adapter;
    }

    protected int getListResourceId() {
        return R.id.list;
    }

    protected int getLoadingResourceId() {
        return R.id.loading;
    }

    protected abstract ViewArrayAdapter<T, ? extends Object> createAdapter(RequestManager requestManager);
    protected abstract NavigationList<T> getNavigationList(RequestManager requestManager);

    protected abstract void onListItemClicked(T item);

    protected int getRootLayout() {
        return R.layout.navigation_list;
    }

    protected int getNoInternetConnectionViewId() {
        return R.id.no_connection;
    }
}
