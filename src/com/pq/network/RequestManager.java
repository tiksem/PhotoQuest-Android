package com.pq.network;

import com.pq.data.User;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.json.GetNavigationListParams;
import com.utilsframework.android.json.JsonHttpClient;

/**
 * Created by CM on 12/21/2014.
 */
public class RequestManager implements ImageUrlProvider {
    private String rootUrl;
    private JsonHttpClient httpClient = new JsonHttpClient();

    public RequestManager(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    public String getImageUrl(long imageId) {
        return rootUrl + "/image/" + imageId;
    }

    @Override
    public String getThumbnailUrl(long imageId, int size) {
        return getImageUrl(imageId) + "?size=" + size;
    }

    public NavigationList<User> getUsersNavigationList() {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + "//users";
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        return httpClient.getNavigationList(params);
    }
}
