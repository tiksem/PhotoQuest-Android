package com.pq.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import com.pq.R;
import com.pq.fragments.LogoutFragment;
import com.pq.fragments.PeopleFragment;
import com.pq.fragments.PhotoquestsFragment;
import com.utilsframework.android.navigation.NavigationDrawerActivity;

/**
 * Created by CM on 12/26/2014.
 */
public class MainActivity extends NavigationDrawerActivity {
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
    public Fragment createFragmentBySelectedItem(int selectedItemId) {
        if(selectedItemId == R.id.people){
            return new PeopleFragment();
        } else if(selectedItemId == R.id.photoquests) {
            return new PhotoquestsFragment();
        } else if(selectedItemId == R.id.log_out) {
            return new LogoutFragment();
        }

        return null;
    }
}
