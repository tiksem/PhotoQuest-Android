package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.fragments.Fragments;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.GuiUtilities;

import java.io.IOException;

/**
 * Created by CM on 2/21/2015.
 */
public class PhotoFragment extends Fragment {
    private static final String PHOTO_ID = "photoId";
    private long photoId;
    private View noConnection;
    private View loading;
    private ImageView photo;
    private RequestManager requestManager;
    private IOErrorListener ioErrorListener;

    public static PhotoFragment create(long photoId) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putLong(PHOTO_ID, photoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        photoId = Fragments.getLong(this, PHOTO_ID, -1);
        requestManager = PhotoQuestApp.getInstance().getRequestManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noConnection = view.findViewById(R.id.no_connection);
        noConnection.setVisibility(View.INVISIBLE);
        loading = view.findViewById(R.id.loading);
        photo = (ImageView) view.findViewById(R.id.photo);
        photo.setVisibility(View.INVISIBLE);

        ioErrorListener = new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                loading.setVisibility(View.INVISIBLE);
                photo.setVisibility(View.INVISIBLE);
                noConnection.setVisibility(View.VISIBLE);
            }
        };
        requestManager.addIOErrorListener(ioErrorListener);

        Images.displayImage(requestManager.getImageUrl(photoId),photo, new OnComplete() {
            @Override
            public void onFinish() {
                loading.setVisibility(View.INVISIBLE);
                photo.setVisibility(View.VISIBLE);
            }
        }, ioErrorListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestManager.removeIOErrorListener(ioErrorListener);
    }
}
