package com.pq.adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.adapters.holders.ReplyHolder;
import com.pq.data.Reply;
import com.pq.fragments.Level;
import com.pq.fragments.PhotoFragment;
import com.pq.fragments.PhotoquestPhotosPagerFragment;
import com.pq.fragments.ProfileFragment;
import com.pq.network.RequestManager;
import com.pq.utils.Images;

/**
 * Created by CM on 2/21/2015.
 */
public abstract class RepliesAdapter extends NavigationListAdapter<Reply, ReplyHolder> {
    private RequestManager requestManager;
    private Context context;

    public RepliesAdapter(Context context, RequestManager requestManager) {
        super(context);
        this.context = context;
        this.requestManager = requestManager;
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.reply_item;
    }

    @Override
    protected ReplyHolder createViewHolder(View view) {
        ReplyHolder holder = new ReplyHolder();
        holder.action = (TextView) view.findViewById(R.id.action);
        holder.avatar = (ImageView) view.findViewById(R.id.avatar);
        holder.comment = (TextView) view.findViewById(R.id.comment);
        holder.date = (TextView) view.findViewById(R.id.date);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.photo = (ImageView) view.findViewById(R.id.photo);
        return holder;
    }

    private String getAction(int type) {
        if(type == Reply.COMMENT){
            return context.getString(R.string.comment_action);
        } else if(type == Reply.LIKE) {
            return context.getString(R.string.like_action);
        } else if(type == Reply.FRIEND_REQUEST_ACCEPTED) {
            return context.getString(R.string.accept_request_action);
        } else if(type == Reply.FRIEND_REQUEST_DECLINED) {
            return context.getString(R.string.decline_request_action);
        }

        throw new RuntimeException("Unknown type");
    }

    @Override
    protected void reuseView(final Reply reply, ReplyHolder holder, int position, View view) {
        holder.name.setText(reply.name);
        holder.date.setText(PhotoquestUtilities.getDisplayDate(reply.addingDate));
        holder.action.setText(getAction(reply.type));

        Images.displayAvatar(requestManager, holder.avatar, reply.avatarId);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = ProfileFragment.create(reply.userId);
                replaceFragment(fragment, Level.REPLY_PROFILE);
            }
        });

        if (reply.photoId != null) {
            Images.displayAvatar(requestManager, holder.photo, reply.photoId);
            holder.photo.setVisibility(View.VISIBLE);
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoFragment fragment = PhotoFragment.create(reply.photoId);
                    replaceFragment(fragment, Level.REPLY_PHOTO);
                }
            });
        } else {
            holder.photo.setVisibility(View.GONE);
        }

        if (reply.comment != null) {
            holder.comment.setText(reply.comment);
            holder.comment.setVisibility(View.VISIBLE);
        } else {
            holder.comment.setVisibility(View.GONE);
        }
    }

    protected abstract void replaceFragment(Fragment newFragment, int navigationLevel);
}
