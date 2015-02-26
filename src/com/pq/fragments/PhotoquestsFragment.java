package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import com.pq.R;
import com.pq.adapters.PhotoquestsAdapter;
import com.pq.data.PhotoCategory;
import com.pq.data.Photoquest;
import com.pq.network.RequestManager;
import com.utilsframework.android.adapters.ViewArrayAdapter;
import com.utilsframework.android.fragments.Fragments;

/**
 * Created by CM on 12/26/2014.
 */
public abstract class PhotoquestsFragment extends NavigationListFragment<Photoquest> {
    private static final String USER_ID = "userId";
    protected long userId;

    public static <T extends PhotoquestsFragment> T create(Context context, long userId,
                                                 Class<T> aClass) {
        Bundle bundle = new Bundle();
        bundle.putLong(USER_ID, userId);
        return (T) Fragment.instantiate(context, aClass.getCanonicalName(), bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userId = Fragments.getLong(this, USER_ID, -1);
    }

    @Override
    protected ViewArrayAdapter<Photoquest, ?> createAdapter(RequestManager requestManager) {
        return new PhotoquestsAdapter(getActivity(), requestManager);
    }

    @Override
    protected void onListItemClicked(Photoquest photoquest) {
        PhotoquestPhotosFragment fragment = PhotoquestPhotosFragment.create(photoquest.getId(), PhotoCategory.all,
                Level.PHOTOQUEST_ALL_PHOTO);
        replaceFragment(fragment, Level.PHOTOQUEST_PHOTOS);
    }

    @Override
    protected int getSortMenuId() {
        return R.menu.photo_sort;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    protected boolean hasSearchMenu() {
        return true;
    }
}
