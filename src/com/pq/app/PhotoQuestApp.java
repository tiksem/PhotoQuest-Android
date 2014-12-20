package com.pq.app;

import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.network.RequestManager;

/**
 * Created by CM on 12/21/2014.
 */
public class PhotoQuestApp extends Application {
    private static PhotoQuestApp instance;

    public static PhotoQuestApp getInstance() {
        return instance;
    }

    private RequestManager requestManager = new RequestManager("http://192.168.0.105:8080");

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImageLoader.getInstance().init(ImageLoaderConfigFactory.getCommonImageLoaderConfig(this));
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
