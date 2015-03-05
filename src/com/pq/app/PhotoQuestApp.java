package com.pq.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import com.mulya.PetrovichDeclinationMaker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pq.network.RequestManager;
import com.utilsframework.android.resources.StringUtilities;

import java.io.*;
import java.util.Locale;
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
    private Resources enStringResources;
    PetrovichDeclinationMaker petrovich;

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
        try {
            petrovich = PetrovichDeclinationMaker.getInstance(getAssets().open("rules.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enStringResources = StringUtilities.getStringResourcesInLocale(this, Locale.ENGLISH);
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public Resources getEnStringResources() {
        return enStringResources;
    }

    public PetrovichDeclinationMaker getPetrovich() {
        return petrovich;
    }
}
