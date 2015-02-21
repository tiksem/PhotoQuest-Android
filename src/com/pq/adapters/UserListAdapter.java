package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pq.R;
import com.pq.adapters.holders.UserHolder;
import com.pq.data.User;
import com.pq.network.ImageUrlProvider;
import com.pq.utils.Images;

/**
 * Created by CM on 12/20/2014.
 */
public class UserListAdapter extends NavigationListAdapter<User, UserHolder> {
    private ImageUrlProvider imageUrlProvider;

    public UserListAdapter(Context context, ImageUrlProvider imageUrlProvider) {
        super(context);
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    protected int getRootLayoutId(int viewType) {
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
        Images.displayAvatar(imageUrlProvider, userHolder.avatar, user.getAvatarId()
        );
    }
}
