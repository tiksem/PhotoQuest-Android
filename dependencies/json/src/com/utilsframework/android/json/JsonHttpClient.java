package com.utilsframework.android.json;

import android.os.Handler;
import com.utils.framework.collections.NavigationList;
import com.utils.framework.collections.cache.LruCache;
import com.utils.framework.io.Network;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.threading.OnFinish;
import com.utilsframework.android.threading.Threading;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.*;

/**
 * Created by CM on 12/21/2014.
 */
public class JsonHttpClient {
    private LruCache<String, CacheResult> cache = new LruCache<String, CacheResult>(5 * 1024 * 1024);
    private long defaultCachingTime = 60 * 1000;
    private int defaultEntitySize = 500;
    private HttpClient httpClient;
    private IOErrorListener ioErrorListener;
    private Handler handler = new Handler();

    public JsonHttpClient() {
        cache = new LruCache<String, CacheResult>(5 * 1024 * 1024){
            @Override
            public int sizeOf(String key, CacheResult value) {
                int result = 64;
                result += key.length() * 2.5 + 32;
                int entitySize = getEntitySize(value.aClass);
                if(entitySize < 0){
                    entitySize = defaultEntitySize;
                }

                if(value.value instanceof Collection){
                    result += 64;
                    result += entitySize * ((Collection) value.value).size();
                } else {
                    result += entitySize;
                }

                return result;
            }
        };

        httpClient = new DefaultHttpClient();
    }

    public long getDefaultCachingTime() {
        return defaultCachingTime;
    }

    public void setDefaultCachingTime(long defaultCachingTime) {
        this.defaultCachingTime = defaultCachingTime;
    }

    public int getDefaultEntitySize() {
        return defaultEntitySize;
    }

    public void setDefaultEntitySize(int defaultEntitySize) {
        this.defaultEntitySize = defaultEntitySize;
    }

    protected int getEntitySize(Class aClass) {
        return -1;
    }

    private void addDeleteFromCacheCallback(long cachingTime, CacheResult cacheResult, final String finalUrl) {
        long time = cachingTime;
        if(time < 0){
            time = getDefaultCachingTime();
        }

        Runnable deleteCallback = cacheResult.deleteCallback = new Runnable() {
            @Override
            public void run() {
                cache.remove(finalUrl);
            }
        };
        handler.postDelayed(deleteCallback, time);
    }

    public <T> void getList(GetListParams<T> params) {
        get(params.url, params.params, params.aClass, params.key, params.cachingTime,
                params.onFinish, true);
    }

    public <T> void get(GetParams<T> params) {
        get(params.url, params.params, params.aClass, null,
                params.cachingTime, params.onFinish, false);
    }

    private void get(String url,
                            SortedMap<String, Object> params,
                            final Class aClass,
                            final String key,
                            final long cachingTime,
                            final OnFinish onFinish,
                            final boolean list) {
        try {
            url = Network.getUrl(url, params);
        } catch (IOException e) {
            executeIoErrorListener(e);
            if (!list) {
                onFinish.onFinish(null);
            } else {
                onFinish.onFinish(new ArrayList<Object>());
            }
        }
        final String finalUrl = url;
        CacheResult cacheResult = cache.get(url);
        if(cacheResult != null){
            onFinish.onFinish(cacheResult.value);
            handler.removeCallbacks(cacheResult.deleteCallback);

            addDeleteFromCacheCallback(cachingTime, cacheResult, finalUrl);

            return;
        }

        Threading.getResultAsync(new Threading.ResultProvider<Object>() {
            @Override
            public Object get() {
                try {
                    String json = Network.executeGetRequest(httpClient, finalUrl);
                    if (!list) {
                        return Json.read(json, aClass);
                    } else {
                        return Json.readList(json, key, aClass);
                    }
                } catch (IOException e) {
                    executeIoErrorListener(e);
                    return null;
                }
            }
        }, new OnFinish<Object>() {
            @Override
            public void onFinish(Object result) {
                if (result != null) {
                    CacheResult cacheResult = new CacheResult();
                    cacheResult.value = result;
                    cacheResult.aClass = aClass;

                    addDeleteFromCacheCallback(cachingTime, cacheResult, finalUrl);
                } else if(list) {
                    result = new ArrayList<Object>();
                }

                onFinish.onFinish(result);
            }
        });
    }

    private SortedMap<String, Object> getLimitOffsetMap(long offset, long limit) {
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        map.put("offset", offset);
        map.put("limit", limit);
        return map;
    }

    public <T> NavigationList<T> getNavigationList(final GetNavigationListParams<T> params) {
        return new NavigationList<T>(500) {
            @Override
            protected void getElementsOfPage(int pageNumber,
                                             final OnPageLoadingFinished<T> onPageLoadingFinished) {
                long offset = params.offset + params.limit * pageNumber;
                SortedMap<String, Object> urlParams = getLimitOffsetMap(offset, params.limit);
                if(params.params != null){
                    urlParams.putAll(params.params);
                }

                JsonHttpClient.this.get(params.url, urlParams, params.aClass,
                        params.key, params.cachingTime, new OnFinish() {
                            @Override
                            public void onFinish(Object result) {
                                List<T> list = (List<T>) result;
                                onPageLoadingFinished.onLoadingFinished(list, list.size() < params.limit);
                            }
                        }, true);
            }
        };
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

    public IOErrorListener getIoErrorListener() {
        return ioErrorListener;
    }

    public void setIoErrorListener(IOErrorListener ioErrorListener) {
        this.ioErrorListener = ioErrorListener;
    }
}
