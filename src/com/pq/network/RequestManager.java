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
        httpClient.setDefaultCachingTime(5 * 60 * 1000);
    }

    @Override
    public String getImageUrl(long imageId) {
        return rootUrl + "/image/" + imageId;
    }

    @Override
    public String getThumbnailUrl(long imageId, int size) {
        return getImageUrl(imageId) + "?size=" + size;
    }

    public NavigationList<User> getUsersNavigationList(OnAllDataLoaded onAllDataLoaded, String url) {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + url;
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        params.onAllDataLoaded = onAllDataLoaded;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<User> getUsersNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getUsersNavigationList(onAllDataLoaded, "//users");
    }

    public NavigationList<User> getFriendsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getUsersNavigationList(onAllDataLoaded, "//friends");
    }

    public NavigationList<User> getReceivedRequestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getUsersNavigationList(onAllDataLoaded, "//getReceivedFriendRequests");
    }

    public NavigationList<User> getSentRequestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getUsersNavigationList(onAllDataLoaded, "//getSentFriendRequests");
    }

    private GetNavigationListParams<Photoquest>
    initPhotoquestsNavigationListParams(OnAllDataLoaded onAllDataLoaded) {
        GetNavigationListParams<Photoquest> params = new GetNavigationListParams<Photoquest>();
        params.limit = 10;
        params.key = "quests";
        params.aClass = Photoquest.class;
        params.onAllDataLoaded = onAllDataLoaded;

        params.params = new TreeMap<String, Object>();
        params.params.put("userId", signedInUser.getId());

        return params;
    }

    public NavigationList<Photoquest> getPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded,
                                                                   String url) {
        GetNavigationListParams<Photoquest> params = initPhotoquestsNavigationListParams(onAllDataLoaded);
        params.url = rootUrl + url;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Photoquest> getAllPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquestsNavigationList(onAllDataLoaded, "//getPhotoquests");
    }

    public NavigationList<Photoquest> getCreatedPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquestsNavigationList(onAllDataLoaded, "//getCreatedPhotoquests");
    }

    public NavigationList<Photoquest> getPerformedPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquestsNavigationList(onAllDataLoaded, "//getPerformedPhotoquests");
    }

    public NavigationList<Photoquest> getFollowingPhotoquestsNavigationList(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquestsNavigationList(onAllDataLoaded, "//getFollowingPhotoquests");
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

    public void logout(OnFinished onFinished) {
        final GetParams<Object> params = new GetParams<Object>();
        params.aClass = Object.class;
        params.onFinish = onFinished;
        params.cachingTime = 0;
        params.url = rootUrl + "//logout";

        httpClient.get(params);
    }

    public User getSignedInUser() {
        return signedInUser;
    }
}
