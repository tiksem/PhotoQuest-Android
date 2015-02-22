package com.pq.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.pq.R;
import com.pq.data.Photo;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.threading.OnComplete;

/**
 * Created by CM on 2/21/2015.
 */
public abstract class AbstractPhotoFragment extends LoadingFragment {
    private long photoId;
    private ImageView imageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.photo);
    }

    @Override
    protected void load(final RequestManager requestManager, final IOErrorListener ioErrorListener,
                        final OnComplete onComplete) {
        getPhoto(new OnSuccess<Photo>() {
            @Override
            public void onSuccess(Photo photo) {
                photoId = photo.id;
                Images.displayImage(requestManager.getImageUrl(photoId), imageView, onComplete, ioErrorListener);
            }
        }, requestManager);
    }

    protected abstract void getPhoto(OnSuccess<Photo> onSuccess, RequestManager requestManager);

    public long getPhotoId() {
        return photoId;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.photo;
    }

    @Override
    protected int getContentId() {
        return R.id.photo;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap.recycle();
    }
}
