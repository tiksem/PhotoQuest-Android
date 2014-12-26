package com.pq.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import com.pq.R;
import com.pq.adapters.UserListAdapter;
import com.pq.app.PhotoQuestApp;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utilsframework.android.json.OnAllDataLoaded;
import com.utilsframework.android.json.OnPageLoaded;

import java.util.List;

public class PeopleActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, PeopleActivity.class);
        context.startActivity(intent);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RequestManager requestManager = PhotoQuestApp.getInstance().getRequestManager();

        AbsListView usersListView = (AbsListView) findViewById(R.id.list);
        final UserListAdapter adapter = new UserListAdapter(this,
                requestManager);
        List<User> users = requestManager.getUsersNavigationList(new OnAllDataLoaded() {
            @Override
            public void onAllDataLoaded() {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setElements(users);
        usersListView.setAdapter(adapter);
    }
}
