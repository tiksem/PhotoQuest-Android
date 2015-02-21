package com.pq.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.adapters.holders.DialogHolder;
import com.pq.data.DialogInfo;
import com.pq.network.RequestManager;
import com.pq.utils.Images;

/**
 * Created by CM on 2/21/2015.
 */
public class DialogsAdapter extends NavigationListAdapter<DialogInfo, DialogHolder> {
    private RequestManager requestManager;

    public DialogsAdapter(Context context, RequestManager requestManager) {
        super(context);
        this.requestManager = requestManager;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.dialog_item;
    }

    @Override
    protected DialogHolder createViewHolder(View view) {
        DialogHolder holder = new DialogHolder();
        holder.avatar = (ImageView) view.findViewById(R.id.avatar);
        holder.smallAvatar = (ImageView) view.findViewById(R.id.smallAvatar);
        holder.date = (TextView) view.findViewById(R.id.date);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.message = (TextView) view.findViewById(R.id.message);
        return holder;
    }

    @Override
    protected void reuseView(DialogInfo dialogInfo, DialogHolder holder, int position, View view) {
        Images.displayAvatar(requestManager, holder.avatar, dialogInfo.avatarId);
        if (dialogInfo.sent) {
            Images.displayAvatar(requestManager, holder.smallAvatar, requestManager.getSignedInUser().getAvatarId());
            holder.smallAvatar.setVisibility(View.VISIBLE);
        } else {
            holder.smallAvatar.setVisibility(View.GONE);
        }

        holder.message.setText(dialogInfo.lastMessage);

        String date = PhotoquestUtilities.getDisplayDate(dialogInfo.lastMessageTime);
        holder.date.setText(date);

        holder.name.setText(dialogInfo.name + " " + dialogInfo.lastName);

        int colorId = dialogInfo.read ? R.color.read_message : R.color.unread_message;
        view.setBackgroundColor(view.getResources().getColor(colorId));
    }
}
