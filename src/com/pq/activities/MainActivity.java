package com.pq.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import com.pq.R;
import com.pq.fragments.*;
import com.utilsframework.android.navigation.NavigationDrawerActivity;

/**
 * Created by CM on 12/26/2014.
 */
public class MainActivity extends NavigationDrawerActivity {
    private static final int ALL_PHOTOQUESTS_TAB = 0;
    private static final int CREATED_PHOTOQUESTS_TAB = 1;
    private static final int PERFORMED_PHOTOQUESTS_TAB = 2;
    private static final int FOLLOWING_PHOTOQUESTS_TAB = 3;
    private static final int PHOTOQUEST_TABS_COUNT = 4;

    private static final int SENT_REQUESTS_TAB = 0;
    private static final int RECEIVED_REQUESTS_TAB = 1;
    private static final int REQUESTS_TABS_COUNT = 2;

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
    public Fragment createFragmentBySelectedItem(int selectedItemId, int tabIndex, int navigationLevel) {
        if(selectedItemId == R.id.people){
            return new PeopleFragment();
        } else if(selectedItemId == R.id.photoquests) {
            if (tabIndex == ALL_PHOTOQUESTS_TAB) {
                return new AllPhotoquestsFragment();
            } else if(tabIndex == CREATED_PHOTOQUESTS_TAB) {
                return new CreatedPhotoquestsFragment();
            } else if(tabIndex == PERFORMED_PHOTOQUESTS_TAB) {
                return new PerformedPhotoquestsFragment();
            } else if(tabIndex == FOLLOWING_PHOTOQUESTS_TAB) {
                return new FollowingPhotoquestsFragment();
            }
        } else if(selectedItemId == R.id.log_out) {
            return new LogoutFragment();
        } else if(selectedItemId == R.id.requests) {
            if(tabIndex == RECEIVED_REQUESTS_TAB) {
                return new ReceivedRequestsFragment();
            } else if(tabIndex == SENT_REQUESTS_TAB) {
                return new SentRequestsFragment();
            }
        } else if(selectedItemId == R.id.profile) {
            return ProfileFragment.create(-1);
        } else if(selectedItemId == R.id.friends) {
            return new FriendsFragment();
        } else if(selectedItemId == R.id.news) {
            return new NewsFragment();
        } else if(selectedItemId == R.id.dialogs) {
            return new DialogsFragment();
        } else if(selectedItemId == R.id.replies) {
            return new RepliesFragment();
        }

        return null;
    }

    @Override
    public void initTab(int currentSelectedItem, int tabIndex, int navigationLevel, ActionBar.Tab tab) {
        if(currentSelectedItem == R.id.photoquests){
            if(tabIndex == ALL_PHOTOQUESTS_TAB){
                tab.setText(R.string.all);
            } else if(tabIndex == CREATED_PHOTOQUESTS_TAB) {
                tab.setText(R.string.created);
            } else if(tabIndex == FOLLOWING_PHOTOQUESTS_TAB) {
                tab.setText(R.string.following);
            } else if(tabIndex == PERFORMED_PHOTOQUESTS_TAB) {
                tab.setText(R.string.performed);
            }
        } else if(currentSelectedItem == R.id.requests) {
            if(tabIndex == RECEIVED_REQUESTS_TAB) {
                tab.setText(R.string.received_requests);
            } else if(tabIndex == SENT_REQUESTS_TAB) {
                tab.setText(R.string.sent_requests);
            }
        }
    }

    @Override
    public int getTabsCount(int selectedItemId, int navigationLevel) {
        if (navigationLevel == 0) {
            if(selectedItemId == R.id.photoquests){
                return PHOTOQUEST_TABS_COUNT;
            } else if(selectedItemId == R.id.requests) {
                return REQUESTS_TABS_COUNT;
            }
        } else if(navigationLevel == Level.PHOTOS_NAVIGATION) {
            return 1;
        } else if(navigationLevel == Level.USER_NAVIGATION) {
            return 1;
        }

        return 1;
    }
}
