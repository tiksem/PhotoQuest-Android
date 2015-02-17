package com.pq.network;

import com.jsonutils.ExceptionInfo;
import com.pq.data.Feed;
import com.pq.data.Photoquest;
import com.pq.data.User;
import com.utils.framework.Predicate;
import com.utils.framework.collections.NavigationList;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.json.*;
import com.utilsframework.android.threading.OnFinish;

import java.io.IOException;
import java.util.*;

/**
 * Created by CM on 12/21/2014.
 */
public class RequestManager implements ImageUrlProvider {
    private String rootUrl;
    private JsonHttpClient httpClient = new JsonHttpClient();
    //do not use directly, use getSignedInUser()
    private User _signedInUser;
    private Set<IOErrorListener> ioErrorListeners = new HashSet<IOErrorListener>();

    public RequestManager(String rootUrl) {
        this.rootUrl = rootUrl;
        httpClient.setDefaultCachingTime(5 * 60 * 1000);
        httpClient.setIoErrorListener(new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                for(IOErrorListener ioErrorListener : ioErrorListeners){
                    ioErrorListener.onIOError(error);
                }
            }
        });
    }

    @Override
    public String getImageUrl(long imageId) {
        return rootUrl + "/image/" + imageId + ".jpg";
    }

    @Override
    public String getThumbnailUrl(long imageId, int size) {
        return getImageUrl(imageId) + "?size=" + size;
    }

    private NavigationList<User> getUsers(String url) {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + url;
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<User> getUsers() {
        return getUsers("//users");
    }

    public NavigationList<User> getFriends() {
        return getUsers("//friends");
    }

    public NavigationList<User> getReceivedRequests() {
        return getUsers("//getReceivedFriendRequests");
    }

    public NavigationList<User> getSentRequests() {
        return getUsers("//getSentFriendRequests");
    }

    private GetNavigationListParams<Photoquest>
    initPhotoquestsNavigationListParams() {
        GetNavigationListParams<Photoquest> params = new GetNavigationListParams<Photoquest>();
        params.limit = 10;
        params.key = "quests";
        params.aClass = Photoquest.class;

        params.params = new TreeMap<String, Object>();
        params.params.put("userId", getSignedInUser().getId());

        return params;
    }

    public NavigationList<Photoquest> getPhotoquests(String url) {
        GetNavigationListParams<Photoquest> params = initPhotoquestsNavigationListParams();
        params.url = rootUrl + url;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Photoquest> getAllPhotoquests() {
        return getPhotoquests("//getPhotoquests");
    }

    public NavigationList<Photoquest> getCreatedPhotoquests() {
        return getPhotoquests( "//getCreatedPhotoquests");
    }

    public NavigationList<Photoquest> getPerformedPhotoquests() {
        return getPhotoquests("//getPerformedPhotoquests");
    }

    public NavigationList<Photoquest> getFollowingPhotoquests() {
        return getPhotoquests("//getFollowingPhotoquests");
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
                _signedInUser = result;
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

    private NavigationList<Feed> getFeed(Predicate<Feed> addElementPredicate,
                                         Map<String, Object> args) {
        GetNavigationListParams<Feed> params = new GetNavigationListParams<Feed>();
        params.url = rootUrl + "//getNews";
        params.limit = 10;
        params.key = "feeds";
        params.aClass = Feed.class;
        params.params = args;
        params.addElementPredicate = addElementPredicate;
        return httpClient.getNavigationList(params);
    }

    public NavigationList<Feed> getUserActivity(long userId) {
        final User signedInUser = getSignedInUser();
        Long signedInUserId = signedInUser.getId();
        if(userId < 0){
            userId = signedInUserId;
        }

        Predicate<Feed> addElementPredicate = null;
        if(userId == signedInUserId){
            addElementPredicate = new Predicate<Feed>() {
                @Override
                public boolean check(Feed item) {
                    item.setUserName(signedInUser.getName() + " " + signedInUser.getLastName());
                    item.setAvatarId(signedInUser.getAvatarId());
                    return true;
                }
            };
        }

        return getFeed(addElementPredicate,
                Collections.<String, Object>singletonMap("userId", userId));
    }

    public NavigationList<Feed> getUserActivity() {
        return getUserActivity(-1);
    }

    public NavigationList<Feed> getNews() {
        return getFeed(null, Collections.<String, Object>emptyMap());
    }

    public User getSignedInUser() {
        if(_signedInUser == null){
            httpClient.getIoErrorListener().onIOError(new IOException("User is not signed in"));
        }

        return _signedInUser;
    }

    public void getUserById(long userId, OnFinish<User> onFinish) {

    }

    public void removeIOErrorListener(IOErrorListener ioErrorListener) {
        ioErrorListeners.remove(ioErrorListener);
    }

    public void addIOErrorListener(IOErrorListener ioErrorListener) {
        ioErrorListeners.add(ioErrorListener);
    }
}
