package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.adapters.holders.MessageHolder;
import com.pq.data.Message;

/**
 * Created by CM on 2/21/2015.
 */
public class MessagesAdapter extends NavigationListAdapter<Message, MessageHolder> {
    private static final int SENT_MESSAGE_VIEW_TYPE = NORMAL_VIEW_TYPE;
    private static final int RECEIVED_MESSAGE_VIEW_TYPE = VIEW_TYPES_COUNT;

    public MessagesAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPES_COUNT + 1;
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        if (viewType == SENT_MESSAGE_VIEW_TYPE) {
            return R.layout.sent_message_item;
        } else {
            return R.layout.received_message_item;
        }
    }

    @Override
    protected int getItemViewTypeOfElement(Message message) {
        if(message.sent){
            return SENT_MESSAGE_VIEW_TYPE;
        } else {
            return RECEIVED_MESSAGE_VIEW_TYPE;
        }
    }

    @Override
    protected MessageHolder createViewHolder(View view) {
        MessageHolder holder = new MessageHolder();
        holder.message = (TextView) view.findViewById(R.id.message);
        holder.date = (TextView) view.findViewById(R.id.date);
        return holder;
    }

    @Override
    protected void reuseView(Message message, MessageHolder holder, int position, View view) {
        holder.message.setText(message.message);
        String date = PhotoquestUtilities.getDisplayDate(message.addingDate);
        holder.date.setText(date);
    }
}
