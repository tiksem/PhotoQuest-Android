package com.pq.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import com.pq.R;
import com.pq.fragments.AllPhotoquestsFragment;
import com.pq.fragments.CreatedPhotoquestsFragment;
import com.pq.fragments.LogoutFragment;
import com.pq.fragments.PeopleFragment;
import com.utilsframework.android.navigation.NavigationDrawerActivity;

/**
 * Created by CM on 12/26/2014.
 */
public class MainActivity extends NavigationDrawerActivity {
    private static final int ALL_PHOTOQUESTS_TAB = 0;
    private static final int CREATED_PHOTOQUESTS_TAB = 1;
    private static final int PHOTOQUEST_TABS_COUNT = 2;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getNavigationLayoutId() {
        return R.layout.navigation;
    }

    @Override
    protected int getCurrentSelectedNavigationItemId() {
        return R.id.people;
    }

    @Override
    public Fragment createFragmentBySelectedItem(int selectedItemId, int tabIndex) {
        if(selectedItemId == R.id.people){
            return new PeopleFragment();
        } else if(selectedItemId == R.id.photoquests) {
            if (tabIndex == ALL_PHOTOQUESTS_TAB) {
                return new AllPhotoquestsFragment();
            } else if(tabIndex == CREATED_PHOTOQUESTS_TAB) {
                return new CreatedPhotoquestsFragment();
            }
        } else if(selectedItemId == R.id.log_out) {
            return new LogoutFragment();
        }

        return null;
    }

    @Override
    public void initTab(int currentSelectedItem, int tabIndex, ActionBar.Tab tab) {
        if(currentSelectedItem == R.id.photoquests){
            if(tabIndex == ALL_PHOTOQUESTS_TAB){
                tab.setText(R.string.all);
            } else if(tabIndex == CREATED_PHOTOQUESTS_TAB) {
                tab.setText(R.string.created);
            }
        }
    }

    @Override
    public int getTabsCount(int selectedItemId) {
        if(selectedItemId == R.id.photoquests){
            return PHOTOQUEST_TABS_COUNT;
        }

        return 1;
    }
}
