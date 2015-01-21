package com.pq.network;

import com.pq.data.Feed;
import com.pq.data.Photoquest;
import com.pq.data.User;
import com.utils.framework.Predicate;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.json.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
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

    private NavigationList<User> getUsers(OnAllDataLoaded onAllDataLoaded, String url) {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + url;
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        params.onAllDataLoaded = onAllDataLoaded;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<User> getUsers(OnAllDataLoaded onAllDataLoaded) {
        return getUsers(onAllDataLoaded, "//users");
    }

    public NavigationList<User> getFriends(OnAllDataLoaded onAllDataLoaded) {
        return getUsers(onAllDataLoaded, "//friends");
    }

    public NavigationList<User> getReceivedRequests(OnAllDataLoaded onAllDataLoaded) {
        return getUsers(onAllDataLoaded, "//getReceivedFriendRequests");
    }

    public NavigationList<User> getSentRequests(OnAllDataLoaded onAllDataLoaded) {
        return getUsers(onAllDataLoaded, "//getSentFriendRequests");
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

    public NavigationList<Photoquest> getPhotoquests(OnAllDataLoaded onAllDataLoaded,
                                                     String url) {
        GetNavigationListParams<Photoquest> params = initPhotoquestsNavigationListParams(onAllDataLoaded);
        params.url = rootUrl + url;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Photoquest> getAllPhotoquests(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquests(onAllDataLoaded, "//getPhotoquests");
    }

    public NavigationList<Photoquest> getCreatedPhotoquests(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquests(onAllDataLoaded, "//getCreatedPhotoquests");
    }

    public NavigationList<Photoquest> getPerformedPhotoquests(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquests(onAllDataLoaded, "//getPerformedPhotoquests");
    }

    public NavigationList<Photoquest> getFollowingPhotoquests(OnAllDataLoaded onAllDataLoaded) {
        return getPhotoquests(onAllDataLoaded, "//getFollowingPhotoquests");
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
        params.params.put("mobile", "true");

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

    private NavigationList<Feed> getFeed(OnAllDataLoaded onAllDataLoaded,
                                         Predicate<Feed> addElementPredicate,
                                         Map<String, Object> args) {
        GetNavigationListParams<Feed> params = new GetNavigationListParams<Feed>();
        params.url = rootUrl + "//getNews";
        params.limit = 10;
        params.key = "feeds";
        params.aClass = Feed.class;
        params.params = args;
        params.onAllDataLoaded = onAllDataLoaded;
        params.addElementPredicate = addElementPredicate;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Feed> getUserActivity(OnAllDataLoaded onAllDataLoaded, long userId) {
        Long signedInUserId = signedInUser.getId();
        if(userId < 0){
            userId = signedInUserId;
        }

        Predicate<Feed> addElementPredicate = null;
        if(userId == signedInUserId){
            addElementPredicate = new Predicate<Feed>() {
                @Override
                public boolean check(Feed item) {
                    item.setUserName(signedInUser.getName());
                    item.setAvatarId(signedInUser.getAvatarId());
                    return true;
                }
            };
        }

        return getFeed(onAllDataLoaded,
                addElementPredicate,
                Collections.<String, Object>singletonMap("userId", userId));
    }

    public NavigationList<Feed> getUserActivity(OnAllDataLoaded onAllDataLoaded) {
        return getUserActivity(onAllDataLoaded, -1);
    }

    public NavigationList<Feed> getNews(OnAllDataLoaded onAllDataLoaded) {
        return getFeed(onAllDataLoaded, null, Collections.<String, Object>emptyMap());
    }

    public User getSignedInUser() {
        return signedInUser;
    }
}
