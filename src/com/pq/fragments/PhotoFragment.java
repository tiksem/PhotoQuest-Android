package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.Photo;
import com.pq.network.RequestManager;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.threading.OnFinish;

/**
 * Created by CM on 2/22/2015.
 */
public class PhotoFragment extends AbstractPhotoFragment {
    private static final String PHOTO_ID = "photoId";

    public static PhotoFragment create(long photoId) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putLong(PHOTO_ID, photoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getPhoto(OnSuccess<Photo> onSuccess, RequestManager requestManager) {
        long photoId = Fragments.getLong(this, PHOTO_ID, -1);
        requestManager.getPhotoById(photoId, onSuccess);
    }
}
