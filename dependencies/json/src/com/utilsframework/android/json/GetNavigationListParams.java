package com.utilsframework.android.json;

import java.util.Map;

/**
 * Created by CM on 12/21/2014.
 */
public class GetNavigationListParams<T> {
    public String url;
    public Map<String, Object> params;
    public String key;
    public Class<T> aClass;
    public long cachingTime;
    public long offset;
    public long limit;
}
