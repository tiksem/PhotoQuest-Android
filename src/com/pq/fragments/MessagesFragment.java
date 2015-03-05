package com.pq.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.pq.R;
import com.pq.adapters.MessagesAdapter;
import com.pq.data.DialogInfo;
import com.pq.data.Message;
import com.pq.data.User;
import com.pq.data.UserInfo;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.navigation.ActionBarTitleProvider;

/**
 * Created by CM on 2/21/2015.
 */
public class MessagesFragment extends NavigationListFragment<Message> implements ActionBarTitleProvider {
    private static final String USER_INFO = "userInfo";
    private EditText message;
    private UserInfo userInfo;

    public static MessagesFragment create(User user) {
        return create(new UserInfo(user));
    }

    public static MessagesFragment create(DialogInfo dialogInfo) {
        return create(new UserInfo(dialogInfo));
    }

    public static MessagesFragment create(UserInfo dialogInfo) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_INFO, dialogInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userInfo = Fragments.getParcelable(this, USER_INFO);
    }

    @Override
    protected ViewArrayAdapter<Message, ?> createAdapter(RequestManager requestManager) {
        return new MessagesAdapter(getActivity());
    }

    @Override
    protected NavigationList<Message> getNavigationList(RequestManager requestManager, String filter) {
        final NavigationList<Message> messages = requestManager.getMessages(userInfo.id);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mini_avatar, menu);
        MenuItem miniAvatar = menu.findItem(R.id.mini_avatar);
        Images.displayIcon(getRequestManager(), miniAvatar, userInfo.avatarId, getActivity());
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void send(String text) {
        getRequestManager().sendMessage(text, userInfo.id, new OnSuccess<Message>() {
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

    @Override
    public String getActionBarTitle() {
        return userInfo.nameData;
    }
}
