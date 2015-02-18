package com.pq.utils;

import android.util.Log;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.R;
import com.pq.network.ImageUrlProvider;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.MeasureUtils;

/**
 * Created by CM on 12/26/2014.
 */
public class Images {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static void displayAvatar(final ImageUrlProvider imageUrlProvider,
                                     final ImageView imageView, final Long imageId) {
        Log.i("Images", "Displaying image " + imageId + "requested");
        GuiUtilities.executeWhenViewMeasuredUsingLoop(imageView, new Runnable() {
            @Override
            public void run() {
                int size = (int) MeasureUtils.convertPixelsToDp(imageView.getMeasuredWidth(), imageView.getContext());

                if (imageId != null) {
                    String avatar = imageUrlProvider.getThumbnailUrl(imageId, size);
                    imageLoader.displayImage(avatar, imageView);
                } else {
                    imageView.setImageResource(R.drawable.empty_avatar);
                }

                Log.i("Images", "Displaying image " + imageId + "finished");
            }
        });
    }
}
