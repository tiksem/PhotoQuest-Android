package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pq.R;
import com.pq.adapters.MessagesAdapter;
import com.pq.data.Message;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;

/**
 * Created by CM on 2/21/2015.
 */
public class MessagesFragment extends NavigationListFragment<Message> {
    private static final String USER_ID = "userId";
    private long userId;
    private EditText message;

    public static MessagesFragment create(long userId) {
        MessagesFragment fragment = new MessagesFragment();
        if (userId > 0) {
            Bundle args = new Bundle();
            args.putLong(USER_ID, userId);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userId = Fragments.getLong(this, USER_ID, -1);
    }

    @Override
    protected ViewArrayAdapter<Message, ?> createAdapter(RequestManager requestManager) {
        return new MessagesAdapter(getActivity());
    }

    @Override
    protected NavigationList<Message> getNavigationList(RequestManager requestManager) {
        return requestManager.getMessages(userId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        message = (EditText) view.findViewById(R.id.messageEditText);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private void send(String text) {
        getRequestManager().sendMessage(text, userId, new OnSuccess<Message>() {
            @Override
            public void onSuccess(Message result) {
                getAdapter().addElementToFront(result);
            }
        });
    }

    private void send() {
        String text = message.getText().toString();
        send(text);
    }

    @Override
    protected void onListItemClicked(Message item) {

    }

    @Override
    protected int getRootLayout() {
        return R.layout.messages;
    }
}
