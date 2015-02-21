package com.pq.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.pq.R;
import com.pq.adapters.holders.GalleryPhotoHolder;
import com.pq.data.GalleryPhoto;
import com.pq.network.ImageUrlProvider;
import com.pq.utils.Images;

/**
 * Created by CM on 2/18/2015.
 */
public class PhotoGalleryAdapter extends NavigationListAdapter<GalleryPhoto, GalleryPhotoHolder> {
    private ImageUrlProvider imageUrlProvider;

    public PhotoGalleryAdapter(Context context, ImageUrlProvider imageUrlProvider) {
        super(context);
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    protected int getRootLayoutId(int viewType) {
        return R.layout.photo_gallery_item;
    }

    @Override
    protected GalleryPhotoHolder createViewHolder(View view) {
        GalleryPhotoHolder holder = new GalleryPhotoHolder();
        holder.image = (ImageView) view.findViewById(R.id.photo);
        return holder;
    }

    @Override
    protected void reuseView(GalleryPhoto photo, GalleryPhotoHolder holder,
                             int position, View view) {
        Images.displayAvatar(imageUrlProvider, holder.image, photo.getId());
    }
}
