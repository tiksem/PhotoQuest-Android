package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.Sorting;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 2/25/2015.
 */
public class UserPhotosPagerFragment extends PhotosPagerFragment {
    private static final String USER_ID = "userId";

    public static UserPhotosPagerFragment create(long userId, long currentPhotoId, Sorting sorting) {
        Bundle args = getArgs(currentPhotoId, sorting);
        UserPhotosPagerFragment fragment = new UserPhotosPagerFragment();
        args.putLong(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected NextPrevPhotoFragment createNextPrevFragment(long currentPhotoId, boolean next, Sorting sorting) {
        long userId = Fragments.getLong(this, USER_ID, -1);
        return NextPrevUserPhotoFragment.create(currentPhotoId, next, userId, sorting);
    }
}
