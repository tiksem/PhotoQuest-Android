package com.pq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.data.Sorting;
import com.pq.network.RequestManager;
import com.utils.framework.Destroyable;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.adapters.AdapterUtils;
import com.utilsframework.android.adapters.ListViewNavigationParams;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.menu.MenuManager;
import com.utilsframework.android.view.GuiUtilities;

import java.io.IOException;
import java.util.Queue;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class NavigationListFragment<T> extends NavigationDrawerFragment {
    private IOErrorListener ioErrorListener;
    private RequestManager requestManager;
    private ViewArrayAdapter<T, ?> adapter;
    protected AbsListView listView;
    private Destroyable navigation;
    private MenuManager menuManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(getRootLayout(), null);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = PhotoQuestApp.getInstance().getRequestManager();
        listView = (AbsListView) view.findViewById(getListResourceId());

        adapter = createAdapter(requestManager);

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

    public void updateNavigationList() {
        if (navigation != null) {
            navigation.destroy();
        }

        final NavigationList<T> elements = getNavigationList(requestManager);

        ListViewNavigationParams<T> params = new ListViewNavigationParams<T>();
        params.viewArrayAdapter = adapter;
        params.navigationList = elements;
        params.rootView = getView();
        params.listViewId = getListResourceId();
        params.loadingViewId = getLoadingResourceId();
        navigation = AdapterUtils.initListViewNavigation(params);
    }

    protected int getSortMenuId() {
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int sortMenuId = getSortMenuId();
        if (sortMenuId != 0) {
            inflater.inflate(sortMenuId, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
        menuManager = new MenuManager(menu);

        Fragments.executeWhenViewCreated(this, new GuiUtilities.OnViewCreated() {
            @Override
            public void onViewCreated(View view) {
                updateNavigationList();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isChecked()){
            return true;
        }

        if(item.getGroupId() == R.id.action_sort){
            item.setChecked(true);
            updateNavigationList();
        }

        return true;
    }

    public Sorting getSortMode() {
        int selectedSortingModeId = menuManager.getFirstCheckedItemOfGroup(R.id.action_sort).getItemId();
        if(selectedSortingModeId == R.id.sort_adding_date){
            return Sorting.newest;
        } else if(selectedSortingModeId == R.id.sort_by_rating) {
            return Sorting.rated;
        } else {
            return null;
        }
    }
}
