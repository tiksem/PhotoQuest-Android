package com.pq.network;

import com.pq.data.Photoquest;
import com.pq.data.User;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.json.*;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by CM on 12/21/2014.
 */
public class RequestManager implements ImageUrlProvider {
    private String rootUrl;
    private JsonHttpClient httpClient = new JsonHttpClient();
    private User signedInUser;

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

    public NavigationList<User> getUsersNavigationList(OnAllDataLoaded onAllDataLoaded) {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + "//users";
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        params.cachingTime = 5 * 60 * 1000;
        params.onAllDataLoaded = onAllDataLoaded;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Photoquest> getPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        GetNavigationListParams<Photoquest> params = new GetNavigationListParams<Photoquest>();
        params.url = rootUrl + "//getPhotoquests";
        params.limit = 10;
        params.key = "quests";
        params.aClass = Photoquest.class;
        params.cachingTime = 5 * 60 * 1000;
        params.onAllDataLoaded = onAllDataLoaded;
        return httpClient.getNavigationList(params);
    }

    public interface LoginListener {
        void onLoginRequestFinished();
        void onLoginRequestError(IOException e, ExceptionInfo info);
        void onLoginSuccess(User user, String login, String password);
    }

    public void login(final String login, final String password, final LoginListener loginListener) {
        final GetParams<User> params = new GetParams<User>();
        params.aClass = User.class;
        params.cachingTime = 0;
        params.url = rootUrl + "//login";

        params.params = new TreeMap<String, Object>();
        params.params.put("login", login);
        params.params.put("password", password);

        params.onFinish = new OnFinished() {
            @Override
            public void onFinished() {
                loginListener.onLoginRequestFinished();
            }
        };

        params.onError = new OnRequestError() {
            @Override
            public void onError(IOException e, ExceptionInfo info) {
                loginListener.onLoginRequestError(e, info);
            }
        };

        params.onSuccess = new OnSuccess<User>() {
            @Override
            public void onSuccess(User result) {
                signedInUser = result;
                loginListener.onLoginSuccess(result, login, password);
            }
        };

        httpClient.get(params);
    }

    public User getSignedInUser() {
        return signedInUser;
    }
}
