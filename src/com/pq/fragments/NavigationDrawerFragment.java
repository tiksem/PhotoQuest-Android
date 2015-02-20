package com.pq.fragments;

import android.app.Fragment;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.navigation.NavigationDrawerActivity;

/**
 * Created by CM on 2/20/2015.
 */
public class NavigationDrawerFragment extends Fragment {
    public NavigationDrawerActivity getNavigationActivity() {
        return (NavigationDrawerActivity) getActivity();
    }

    public void replaceFragment(Fragment newFragment, int navigationLevel) {
        getNavigationActivity().replaceFragment(newFragment, navigationLevel);
    }
}
