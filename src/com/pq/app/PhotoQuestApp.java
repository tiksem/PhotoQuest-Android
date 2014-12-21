package com.pq.app;

import android.app.Application;
import android.os.Environment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.network.RequestManager;

import java.io.*;
import java.util.Properties;

/**
 * Created by CM on 12/21/2014.
 */
public class PhotoQuestApp extends Application {
    private static PhotoQuestApp instance;

    public static PhotoQuestApp getInstance() {
        return instance;
    }

    private RequestManager requestManager;

    private Properties getProperties() {
        File file = new File(Environment.getExternalStorageDirectory(), "PhotoQuest/config.properties");
        file.getParentFile().mkdirs();

        Properties properties = new Properties();
        if(!file.exists()){
            properties.setProperty("host", "http://192.168.0.1:8080");
            try {
                properties.store(new FileOutputStream(file), "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                properties.load(new FileInputStream(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return properties;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Properties properties = getProperties();
        String host = properties.getProperty("host");
        requestManager = new RequestManager(host);
        ImageLoader.getInstance().init(ImageLoaderConfigFactory.getCommonImageLoaderConfig(this));
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}
