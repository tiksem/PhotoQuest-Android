package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.R;
import com.pq.adapters.holders.PhotoquestHolder;
import com.pq.data.Photoquest;
import com.pq.network.ImageUrlProvider;
import com.pq.utils.Images;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 12/26/2014.
 */
public class PhotoquestsAdapter extends ViewArrayAdapter<Photoquest, PhotoquestHolder> {
    private static final int AVATAR_SIZE = 100;
    private ImageUrlProvider imageUrlProvider;

    public PhotoquestsAdapter(Context context, ImageUrlProvider imageUrlProvider) {
        super(context);
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.photoquest_list_item;
    }

    @Override
    protected int getNullLayoutId() {
        return R.layout.person_list_null_item;
    }

    @Override
    protected PhotoquestHolder createViewHolder(View view) {
        PhotoquestHolder holder = new PhotoquestHolder();
        holder.avatar = (ImageView) view.findViewById(R.id.avatar);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.userName = (TextView) view.findViewById(R.id.userName);
        holder.viewsCount = (TextView) view.findViewById(R.id.viewsCount);
        return holder;
    }

    @Override
    protected void reuseView(Photoquest photoquest, PhotoquestHolder holder, int position, View view) {
        holder.name.setText(photoquest.getName());
        holder.userName.setText(photoquest.getCreatedBy());
        holder.viewsCount.setText(String.valueOf(photoquest.getViewsCount()));
        Images.displayAvatar(imageUrlProvider, holder.avatar, photoquest.getAvatarId(),
                AVATAR_SIZE);
    }
}
