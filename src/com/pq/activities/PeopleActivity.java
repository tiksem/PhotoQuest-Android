package com.pq.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import com.pq.R;
import com.pq.adapters.UserListAdapter;
import com.pq.app.PhotoQuestApp;
import com.pq.data.User;
import com.pq.network.RequestManager;

import java.util.List;

public class PeopleActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RequestManager requestManager = PhotoQuestApp.getInstance().getRequestManager();

        AbsListView usersListView = (AbsListView) findViewById(R.id.list);
        UserListAdapter adapter = new UserListAdapter(this,
                requestManager);
        List<User> users = requestManager.getUsersNavigationList();
        adapter.setElements(users);
        usersListView.setAdapter(adapter);
    }
}
