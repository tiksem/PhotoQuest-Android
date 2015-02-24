package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.pq.R;
import com.pq.adapters.MessagesAdapter;
import com.pq.data.Message;
import com.pq.network.RequestManager;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.UiLoopEvent;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.view.GuiUtilities;

import java.util.List;

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
        final NavigationList<Message> messages = requestManager.getMessages(userId);
        messages.setManualPageLoading(true);
        messages.setOnPageLoadingRequested(new NavigationList.OnPageLoadingRequested() {
            boolean disabled = false;

            @Override
            public void onPageLoadingRequested(final int index) {
                if(disabled){
                    return;
                }

                if(listView.getFirstVisiblePosition() < 1){
                    final int before = messages.size();

                    disabled = true;
                    messages.loadNextPage(new NavigationList.OnPageLoadingFinished() {
                        @Override
                        public void onLoadingFinished() {
                            listView.smoothScrollToPositionFromTop(messages.size() - before, 0, 0);

                            listView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    disabled = false;
                                }
                            }, 200);
                        }
                    });
                }
            }
        });
        return messages;
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
                ViewArrayAdapter<Message, ?> adapter = getAdapter();
                adapter.addElementToFront(result);
                listView.smoothScrollToPositionFromTop(adapter.getCount() - 1, 0, 0);
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
