package com.pq.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.data.PhotoCategory;
import com.pq.fragments.*;
import com.pq.network.RequestManager;
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

    private static final int ALL_PHOTOS_TAB = 0;
    private static final int FRIENDS_PHOTOS_TAB = 1;
    private static final int MINE_PHOTOS_TAB = 2;
    private static final int PHOTOQUEST_PHOTOS_TABS_COUNT = 3;

    private RequestManager requestManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestManager = PhotoQuestApp.getInstance().getRequestManager();
        super.onCreate(savedInstanceState);
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
        if (navigationLevel == Level.ROOT) {
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
            } else if(selectedItemId == R.id.photos) {
                return UserPhotosFragment.create(requestManager.getSignedInUser().getId());
            }
        } else if(navigationLevel == Level.PHOTOQUEST_PHOTOS) {
            int photoLevel;
            PhotoCategory category;

            if(tabIndex == ALL_PHOTOS_TAB) {
                photoLevel = Level.PHOTOQUEST_ALL_PHOTO;
                category = PhotoCategory.all;
            } else if(tabIndex == FRIENDS_PHOTOS_TAB) {
                photoLevel = Level.PHOTOQUEST_FRIENDS_PHOTO;
                category = PhotoCategory.friends;
            } else if(tabIndex == MINE_PHOTOS_TAB) {
                photoLevel = Level.PHOTOQUEST_MINE_PHOTO;
                category = PhotoCategory.mine;
            } else {
                throw new RuntimeException("WTF?");
            }

            PhotoquestPhotosFragment fragment = (PhotoquestPhotosFragment) getCurrentFragment();
            long photoquestId = fragment.getPhotoquestId();
            return PhotoquestPhotosFragment.create(photoquestId, category, photoLevel);
        }

        return null;
    }

    @Override
    public void initTab(int currentSelectedItem, int tabIndex, int navigationLevel, ActionBar.Tab tab) {
        if (navigationLevel == Level.ROOT) {
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
        } else if(navigationLevel == Level.PHOTOQUEST_PHOTOS) {
            if(tabIndex == ALL_PHOTOS_TAB) {
                tab.setText(R.string.all);
            } else if(tabIndex == FRIENDS_PHOTOS_TAB) {
                tab.setText(R.string.friends_photos);
            } else if(tabIndex == MINE_PHOTOS_TAB) {
                tab.setText(R.string.mine);
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
        } else if(navigationLevel == Level.PHOTOQUEST_PHOTOS) {
            return PHOTOQUEST_PHOTOS_TABS_COUNT;
        } else if(navigationLevel == Level.USER) {
            return 1;
        }

        return 1;
    }
}
