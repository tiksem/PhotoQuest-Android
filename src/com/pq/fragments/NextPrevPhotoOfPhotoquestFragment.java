package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.Photo;
import com.pq.data.PhotoCategory;
import com.pq.network.RequestManager;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;

/**
 * Created by CM on 2/22/2015.
 */
public class NextPrevPhotoOfPhotoquestFragment extends NextPrevPhotoFragment {
    private static final String PHOTOQUEST_ID = "photoquestId";
    private static final String CATEGORY = "category";

    public static NextPrevPhotoOfPhotoquestFragment create(long currentPhotoId,
                                                           boolean next,
                                                           long photoquestId,
                                                           PhotoCategory category) {
        NextPrevPhotoOfPhotoquestFragment fragment = new NextPrevPhotoOfPhotoquestFragment();
        Bundle args = getArgs(currentPhotoId, next);
        args.putLong(PHOTOQUEST_ID, photoquestId);
        args.putInt(CATEGORY, category.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getPhoto(long currentPhotoId, boolean next, final OnSuccess<Photo> onSuccess,
                            RequestManager requestManager) {
        long photoquestId = Fragments.getLong(this, PHOTOQUEST_ID, -1);
        PhotoCategory category = PhotoCategory.values()[Fragments.getInt(this, CATEGORY, 0)];

        requestManager.getNextPrevPhotoOfPhotoquest(currentPhotoId, photoquestId,
                next, category, onSuccess);
    }
}
