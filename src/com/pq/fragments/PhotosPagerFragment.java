package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pq.R;
import com.pq.network.RequestManager;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.NextPrevCyclingViewPager;

/**
 * Created by CM on 2/22/2015.
 */
public abstract class PhotosPagerFragment extends Fragment {
    private static final String PHOTO_ID = "photoId";

    public static Bundle getArgs(long photoId) {
        Bundle args = new Bundle();
        args.putLong(PHOTO_ID, photoId);
        return args;
    }

    private class Adapter implements NextPrevCyclingViewPager.Adapter<AbstractPhotoFragment> {
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
            AbstractPhotoFragment fragment = createNextPrevFragment(currentFragment.getPhotoId(), true);
            fragment.setOnComplete(onComplete);
            return fragment;
        }

        @Override
        public AbstractPhotoFragment createPrevFragment(AbstractPhotoFragment currentFragment,
                                                        OnComplete onComplete) {
            AbstractPhotoFragment fragment = createNextPrevFragment(currentFragment.getPhotoId(), false);
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

    protected abstract NextPrevPhotoFragment createNextPrevFragment(long currentPhotoId, boolean next);
}