package com.pq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pq.R;
import com.pq.data.Sorting;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.NextPrevCyclingViewPager;

/**
 * Created by CM on 2/22/2015.
 */
public abstract class PhotosPagerFragment extends Fragment {
    private static final String PHOTO_ID = "photoId";
    private static final String SORTING = "sorting";

    public static Bundle getArgs(long photoId, Sorting sorting) {
        Bundle args = new Bundle();
        args.putLong(PHOTO_ID, photoId);
        args.putInt(SORTING, sorting.ordinal());
        return args;
    }

    private class Adapter implements NextPrevCyclingViewPager.Adapter<AbstractPhotoFragment> {
        Sorting sorting = Sorting.values()[Fragments.getInt(PhotosPagerFragment.this, SORTING, 0)];

        @Override
        public AbstractPhotoFragment createFirstFragment(OnComplete onComplete) {
            long photoId = Fragments.getLong(PhotosPagerFragment.this, PHOTO_ID, -1);
            PhotoFragment fragment = PhotoFragment.create(photoId);
            fragment.setOnComplete(onComplete);
            return fragment;
        }

        @Override
        public AbstractPhotoFragment createNextFragment(AbstractPhotoFragment currentFragment,
                                                        OnComplete onComplete) {
            AbstractPhotoFragment fragment = createNextPrevFragment(currentFragment.getPhotoId(), true, sorting);
            fragment.setOnComplete(onComplete);
            return fragment;
        }

        @Override
        public AbstractPhotoFragment createPrevFragment(AbstractPhotoFragment currentFragment,
                                                        OnComplete onComplete) {
            AbstractPhotoFragment fragment = createNextPrevFragment(currentFragment.getPhotoId(), false, sorting);
            fragment.setOnComplete(onComplete);
            return fragment;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NextPrevCyclingViewPager viewPager = (NextPrevCyclingViewPager) view.findViewById(R.id.content);
        viewPager.setNextPrevAdapter(new Adapter());
    }

    protected abstract NextPrevPhotoFragment createNextPrevFragment(long currentPhotoId, boolean next,
                                                                    Sorting sorting);
}