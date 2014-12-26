package com.utilsframework.android.json;

import java.util.List;
import java.util.SortedMap;

/**
* Created by CM on 12/21/2014.
*/
public class GetListParams<T> {
    public String url;
    public SortedMap<String, Object> params;
    public String key;
    public Class<T> aClass;
    public long cachingTime = -1;
    public OnFinished onFinish;
    public OnRequestError onError;
    public OnSuccess<List<T>> onSuccess;
}
