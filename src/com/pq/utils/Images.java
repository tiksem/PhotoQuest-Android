package com.pq.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pq.R;
import com.pq.network.ImageUrlProvider;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.GuiUtilities;
import com.utilsframework.android.view.MeasureUtils;

import java.io.IOException;

/**
 * Created by CM on 12/26/2014.
 */
public class Images {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static void displayImage(String url, ImageView imageView, final OnComplete onComplete,
                                    final IOErrorListener ioErrorListener) {
        DisplayImageOptions options = DisplayImageOptions.createSimple();
        imageLoader.displayImage(url, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                if(ioErrorListener != null){
                    FailReason.FailType type = failReason.getType();
                    if (type == FailReason.FailType.NETWORK_DENIED ||
                            type == FailReason.FailType.IO_ERROR) {
                        ioErrorListener.onIOError((IOException) failReason.getCause());
                    }
                }
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if(onComplete != null){
                    onComplete.onFinish();
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public static void displayAvatar(final ImageUrlProvider imageUrlProvider,
                                     final ImageView imageView,
                                     final Long imageId) {
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
