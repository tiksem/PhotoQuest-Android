package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pq.R;
import com.pq.adapters.holders.FeedHolder;
import com.pq.app.UiUtilities;
import com.pq.data.Feed;
import com.pq.network.ImageUrlProvider;
import com.pq.utils.Images;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.resources.StringUtilities;

/**
 * Created by CM on 1/20/2015.
 */
public class FeedAdapter extends ViewArrayAdapter<Feed, FeedHolder> {
    private static final int AVATAR_SIZE = 50;
    private static final int IMAGE_SIZE = 300;

    private ImageUrlProvider imageUrlProvider;

    public FeedAdapter(Context context, ImageUrlProvider imageUrlProvider) {
        super(context);
        this.imageUrlProvider = imageUrlProvider;
    }

    public FeedAdapter(Context context, View header, ImageUrlProvider imageUrlProvider) {
        super(context, header);
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.feed_item;
    }

    @Override
    protected FeedHolder createViewHolder(View view) {
        FeedHolder holder = new FeedHolder();
        holder.avatar = (ImageView) view.findViewById(R.id.avatar);
        holder.date = (TextView) view.findViewById(R.id.date);
        holder.description = (TextView) view.findViewById(R.id.description);
        holder.image = (ImageView) view.findViewById(R.id.image);
        holder.likesCount = (TextView) view.findViewById(R.id.likesCount);
        holder.name = (TextView) view.findViewById(R.id.name);
        return holder;
    }

    private String getDescription(Context context, Feed feed) {
        int stringId = R.string.publish_photo_feed_description;
        if(feed.getPhotoId() == null){
            stringId = R.string.create_photoquest_feed_description;
        }

        return StringUtilities.getFormatString(context, stringId, feed.getPhotoquestName());
    }

    @Override
    protected void reuseView(Feed feed, FeedHolder holder, int position, View view) {
        Long photoId = feed.getPhotoId();
        if (photoId != null) {
            Images.displayAvatar(imageUrlProvider, holder.image, photoId,
                    IMAGE_SIZE);
            StringUtilities.setFormatText(holder.likesCount, R.string.like_it, feed.getLikesCount());
        } else {
            holder.image.setVisibility(View.GONE);
            holder.likesCount.setVisibility(View.GONE);
        }
        Images.displayAvatar(imageUrlProvider, holder.avatar, feed.getAvatarId(),
                AVATAR_SIZE);

        String date = UiUtilities.getDisplayDate(feed.getAddingDate());
        holder.date.setText(date);

        String description = getDescription(view.getContext(), feed);
        holder.description.setText(description);

        holder.name.setText(feed.getUserName());
    }

    @Override
    protected int getNullLayoutId() {
        return R.layout.person_list_null_item;
    }
}
