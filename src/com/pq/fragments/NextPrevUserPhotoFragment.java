package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.Photo;
import com.pq.data.Sorting;
import com.pq.network.RequestManager;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.json.OnSuccess;

/**
 * Created by CM on 2/25/2015.
 */
public class NextPrevUserPhotoFragment extends NextPrevPhotoFragment {
    private static final String USER_ID = "userId";

    public static NextPrevUserPhotoFragment create(long currentPhotoId,
                                                           boolean next,
                                                           long userId,
                                                           Sorting sorting) {
        NextPrevUserPhotoFragment fragment = new NextPrevUserPhotoFragment();
        Bundle args = getArgs(currentPhotoId, next, sorting);
        args.putLong(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getPhoto(long currentPhotoId, boolean next, Sorting sorting, final OnSuccess<Photo> onSuccess,
                            RequestManager requestManager) {
        long userId = Fragments.getLong(this, USER_ID, -1);

        requestManager.getNextPrevPhotoOfUser(currentPhotoId, userId,
                next, sorting, onSuccess);
    }
}
