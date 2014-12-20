package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.R;
import com.pq.adapters.holders.UserHolder;
import com.pq.data.User;
import com.pq.network.ImageUrlProvider;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 12/20/2014.
 */
public class UserListAdapter extends ViewArrayAdapter<User, UserHolder> {
    private static final int AVATAR_SIZE = 100;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageUrlProvider imageUrlProvider;

    public UserListAdapter(Context context, ImageUrlProvider imageUrlProvider) {
        super(context);
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.person_list_item;
    }

    @Override
    protected UserHolder createViewHolder(View view) {
        UserHolder holder = new UserHolder();
        holder.avatar = (ImageView) view.findViewById(R.id.avatar);
        holder.location = (TextView) view.findViewById(R.id.location);
        holder.name = (TextView) view.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected void reuseView(User user, UserHolder userHolder, int position, View view) {
        userHolder.name.setText(user.getName() + " " + user.getLastName());
        userHolder.location.setText(user.getCity() + ", " + user.getCountry());
        String avatar = imageUrlProvider.getThumbnailUrl(user.getAvatarId(), AVATAR_SIZE);
        imageLoader.displayImage(avatar, userHolder.avatar);
    }

    @Override
    protected int getNullLayoutId() {
        return R.layout.person_list_null_item;
    }
}
