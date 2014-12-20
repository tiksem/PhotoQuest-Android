package com.pq.network;

import android.os.Handler;
import com.pq.data.Json;
import com.pq.data.User;
import com.utils.framework.collections.NavigationList;
import com.utils.framework.io.Network;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.threading.OnFinish;
import com.utilsframework.android.threading.Threading;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CM on 12/21/2014.
 */
public class RequestManager implements ImageUrlProvider {
    private String rootUrl;
    private HttpClient httpClient;
    private IOErrorListener ioErrorListener;
    private Handler handler;

    public RequestManager(String rootUrl) {
        this.rootUrl = rootUrl;
        httpClient = new DefaultHttpClient();
    }

    @Override
    public String getImageUrl(long imageId) {
        return rootUrl + "/image/" + imageId;
    }

    @Override
    public String getThumbnailUrl(long imageId, int size) {
        return getImageUrl(imageId) + "?size=" + size;
    }

    private Map<String, Object> getLimitOffsetMap(long offset, long limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offset", offset);
        map.put("limit", limit);
        return map;
    }

    public IOErrorListener getIoErrorListener() {
        return ioErrorListener;
    }

    public void setIoErrorListener(IOErrorListener ioErrorListener) {
        this.ioErrorListener = ioErrorListener;
    }

    private void executeIoErrorListener(final IOException e) {
        if (ioErrorListener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ioErrorListener.onIOError(e);
                }
            });
        }
    }

    public List<User> getUsers(long offset, long limit) {
        String url = rootUrl + "//users";
        Map<String, Object> params = getLimitOffsetMap(offset, limit);
        try {
            String response = Network.executeGetRequest(httpClient, url, params);
            return Json.readList(response, "users", User.class);
        } catch (IOException e) {
            executeIoErrorListener(e);
            return new ArrayList<User>();
        }
    }

    public void getUsersAsync(final long offset, final long limit, OnFinish<List<User>> onFinish) {
        Threading.getResultAsync(new Threading.ResultProvider<List<User>>() {
            @Override
            public List<User> get() {
                return getUsers(offset, limit);
            }
        }, onFinish);
    }

    public NavigationList<User> getUsersNavigationList() {
        return new UsersNavigationList(this);
    }
}
