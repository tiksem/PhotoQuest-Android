package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.Photo;
import com.pq.data.Sorting;
import com.pq.network.RequestManager;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;

/**
 * Created by CM on 2/22/2015.
 */
public abstract class NextPrevPhotoFragment extends AbstractPhotoFragment {
    protected static final String CURRENT_PHOTO_ID = "currentPhotoId";
    protected static final String NEXT = "next";
    protected static final String SORTING = "sorting";

    protected static Bundle getArgs(long currentPhotoId, boolean next, Sorting sorting) {
        Bundle args = new Bundle();
        args.putLong(CURRENT_PHOTO_ID, currentPhotoId);
        args.putBoolean(NEXT, next);
        args.putInt(SORTING, sorting.ordinal());
        return args;
    }

    @Override
    protected void getPhoto(OnSuccess<Photo> onSuccess, RequestManager requestManager) {
        long currentPhotoId = Fragments.getLong(this, CURRENT_PHOTO_ID, -1);
        boolean next = Fragments.getBoolean(this, NEXT);
        Sorting sorting = Sorting.values()[Fragments.getInt(this, SORTING, 0)];
        getPhoto(currentPhotoId, next, sorting, onSuccess, requestManager);
    }

    protected abstract void getPhoto(long currentPhotoId, boolean next,
                                     Sorting sorting, OnSuccess<Photo> onSuccess, RequestManager requestManager);
}
