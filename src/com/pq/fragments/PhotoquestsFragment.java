package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.pq.R;
import com.pq.adapters.PhotoquestsAdapter;
import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.json.OnAllDataLoaded;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.tabs.FragmentFactory;
import com.utilsframework.android.view.tabs.FragmentTabInfo;
import com.utilsframework.android.view.tabs.Tabs;
import com.utilsframework.android.view.tabs.TabsDestroyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class PhotoquestsFragment extends NavigationListFragment<Photoquest> {
    @Override
    protected ViewArrayAdapter<Photoquest, ?> createAdapter(RequestManager requestManager) {
        return new PhotoquestsAdapter(getActivity(), requestManager);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.photoquests;
    }
}
