package com.pq.fragments;

import android.os.Bundle;
import com.pq.data.PhotoCategory;
import com.pq.data.Sorting;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 2/22/2015.
 */
public class PhotoquestPhotosPagerFragment extends PhotosPagerFragment {
    private static final String PHOTOQUEST_ID = "photoquestId";
    private static final String CATEGORY = "category";

    public static PhotoquestPhotosPagerFragment create(long photoquestId, long currentPhotoId,
                                                       PhotoCategory category, Sorting sorting) {
        Bundle args = getArgs(currentPhotoId, sorting);
        PhotoquestPhotosPagerFragment fragment = new PhotoquestPhotosPagerFragment();
        args.putLong(PHOTOQUEST_ID, photoquestId);
        args.putInt(CATEGORY, category.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected NextPrevPhotoFragment createNextPrevFragment(long currentPhotoId, boolean next, Sorting sorting) {
        PhotoCategory category = PhotoCategory.values()[Fragments.getInt(this, CATEGORY, 0)];
        long photoquestId = Fragments.getLong(this, PHOTOQUEST_ID, -1);
        return NextPrevPhotoOfPhotoquestFragment.create(currentPhotoId, next, photoquestId, sorting, category);
    }
}
