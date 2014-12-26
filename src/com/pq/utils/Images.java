package com.pq.utils;

import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.R;
import com.pq.network.ImageUrlProvider;

/**
 * Created by CM on 12/26/2014.
 */
public class Images {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static void displayAvatar(ImageUrlProvider imageUrlProvider,
                                     ImageView imageView, Long imageId, int size) {
        if (imageId != null) {
            String avatar = imageUrlProvider.getThumbnailUrl(imageId, size);
            imageLoader.displayImage(avatar, imageView);
        } else {
            imageView.setImageResource(R.drawable.empty_avatar);
        }
    }
}
