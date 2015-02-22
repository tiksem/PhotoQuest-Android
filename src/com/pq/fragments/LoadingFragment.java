package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.network.RequestManager;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.UiLoopEvent;
import com.utilsframework.android.threading.OnComplete;

import java.io.IOException;

/**
 * Created by CM on 2/22/2015.
 */
public abstract class LoadingFragment extends Fragment {
    private RequestManager requestManager;
    private View noConnection;
    private View loading;
    private View content;
    private IOErrorListener ioErrorListener;
    private OnComplete onComplete;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        requestManager = PhotoQuestApp.getInstance().getRequestManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getRootLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noConnection = view.findViewById(getNoConnectionResourceId());
        noConnection.setVisibility(View.INVISIBLE);
        loading = view.findViewById(getLoadingResourceId());
        content = view.findViewById(getContentId());
        content.setVisibility(View.INVISIBLE);

        ioErrorListener = new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                loading.setVisibility(View.INVISIBLE);
                content.setVisibility(View.INVISIBLE);
                noConnection.setVisibility(View.VISIBLE);
            }
        };
        requestManager.addIOErrorListener(ioErrorListener);

        load(requestManager, ioErrorListener, new OnComplete() {
            @Override
            public void onFinish() {
                loading.setVisibility(View.INVISIBLE);
                content.setVisibility(View.VISIBLE);
                noConnection.setVisibility(View.INVISIBLE);
                if(onComplete != null){
                    onComplete.onFinish();
                }
            }
        });

        new UiLoopEvent(this, 200).run(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestManager.removeIOErrorListener(ioErrorListener);
    }

    protected abstract void load(RequestManager requestManager, IOErrorListener ioErrorListener,
                                 OnComplete onComplete);

    protected abstract int getRootLayoutId();
    protected int getNoConnectionResourceId() {
        return R.id.no_connection;
    }
    protected int getLoadingResourceId() {
        return R.id.loading;
    }
    protected abstract int getContentId();

    public OnComplete getOnComplete() {
        return onComplete;
    }

    public void setOnComplete(OnComplete onComplete) {
        this.onComplete = onComplete;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
