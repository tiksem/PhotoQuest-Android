package com.pq.network;

import com.jsonutils.ExceptionInfo;
import com.pq.data.*;
import com.utils.framework.Predicate;
import com.utils.framework.Reflection;
import com.utils.framework.collections.NavigationList;
import com.utils.framework.strings.Strings;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.json.*;
import com.utilsframework.android.json.OnSuccess;

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

    public String getCaptchaUrl(long captchaId) {
        return rootUrl + "/captcha/" + captchaId;
    }

    private NavigationList<User> getUsers(String url, Sorting sorting) {
        GetNavigationListParams<User> params = new GetNavigationListParams<User>();
        params.url = rootUrl + url;
        params.limit = 10;
        params.key = "users";
        params.aClass = User.class;
        params.params = Collections.<String, Object>singletonMap("order", sorting);
        return httpClient.getNavigationList(params);
    }

    public NavigationList<User> getUsers(Sorting sorting) {
        return getUsers("//users", sorting);
    }

    public NavigationList<User> getFriends(Sorting sorting) {
        return getUsers("//friends", sorting);
    }

    public NavigationList<User> getReceivedRequests(Sorting sorting) {
        return getUsers("//getReceivedFriendRequests", sorting);
    }

    public NavigationList<User> getSentRequests(Sorting sorting) {
        return getUsers("//getSentFriendRequests", sorting);
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

    public NavigationList<Photoquest> getPhotoquests(String url, Sorting sorting, long userId, String filter) {
        if(userId < 0){
            userId = getSignedInUser().getId();
        }

        GetNavigationListParams<Photoquest> params = initPhotoquestsNavigationListParams();
        params.url = rootUrl + url;
        params.params.put("order", sorting);
        params.params.put("userId", userId);
        if (filter != null) {
            params.params.put("filter", filter);
        }

        return httpClient.getNavigationList(params);
    }

    public NavigationList<Photoquest> getAllPhotoquests(long userId, Sorting sorting, String filter) {
        return getPhotoquests("//getPhotoquests", sorting, userId, filter);
    }

    public NavigationList<Photoquest> getCreatedPhotoquests(long userId, Sorting sorting, String filter) {
        return getPhotoquests( "//getCreatedPhotoquests", sorting, userId, filter);
    }

    public NavigationList<Photoquest> getPerformedPhotoquests(long userId, Sorting sorting, String filter) {
        return getPhotoquests("//getPerformedPhotoquests", sorting, userId, filter);
    }

    public NavigationList<Photoquest> getFollowingPhotoquests(long userId, Sorting sorting, String filter) {
        return getPhotoquests("//getFollowingPhotoquests", sorting, userId, filter);
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

    public void getUserById(long userId, OnSuccess<User> onSuccess) {
        final GetParams<User> params = new GetParams<User>();
        params.aClass = User.class;
        params.cachingTime = 0;
        params.url = rootUrl + "//getUserById";
        params.onSuccess = onSuccess;

        params.params = new TreeMap<String, Object>();
        params.params.put("id", userId);
        httpClient.get(params);
    }

    private GetNavigationListParams<GalleryPhoto> getPhotosParams(String url, Sorting sorting) {
        GetNavigationListParams<GalleryPhoto> params = new GetNavigationListParams<GalleryPhoto>();
        params.url = rootUrl + url;
        params.limit = 10;
        params.key = "photos";
        params.aClass = GalleryPhoto.class;
        params.params = new HashMap<String, Object>();
        params.params.put("order", sorting);
        return params;
    }

    public NavigationList<GalleryPhoto> getPhotosOfPhotoquest(long photoquestId, PhotoCategory category,
                                                              Sorting sorting) {
        GetNavigationListParams<GalleryPhoto> params = getPhotosParams("//getPhotosOfPhotoquest", sorting);
        params.params.put("id", photoquestId);
        params.params.put("category", category);

        return httpClient.getNavigationList(params);
    }

    public NavigationList<GalleryPhoto> getPhotosOfUser(long userId, Sorting sorting) {
        GetNavigationListParams<GalleryPhoto> params = getPhotosParams("//getPhotosOfUser", sorting);
        params.params.put("userId", userId);

        return httpClient.getNavigationList(params);
    }

    public static class Captcha {
        public long id;
    }

    public void getCaptcha(OnSuccess<Captcha> onSuccess) {
        final GetParams<Captcha> params = new GetParams<Captcha>();
        params.aClass = Captcha.class;
        params.cachingTime = 0;
        params.url = rootUrl + "//getCaptcha";
        params.onSuccess = onSuccess;

        httpClient.get(params);
    }

    public void register(UserRegistration user, OnSuccess<User> onSuccess) {
        final GetParams<User> params = new GetParams<User>();
        params.aClass = User.class;
        params.cachingTime = 0;
        params.url = rootUrl + "//register";
        params.onSuccess = onSuccess;
        params.params = new TreeMap<String, Object>();
        Reflection.objectToPropertyMap(params.params, user);

        httpClient.get(params);
    }

    public List<Suggestion> getCountrySuggestions(String query) throws IOException {
        String url = rootUrl + "//getCountrySuggestions";
        Map<String, Object> params = Collections.<String, Object>singletonMap("query", query);
        return httpClient.getListSync(url, params, "suggestions", Suggestion.class);
    }

    public void getCountrySuggestions(String query, OnSuccess<List<Suggestion>> onSuccess) {
        final GetListParams<Suggestion> params = GetListParams.create(Suggestion.class);
        params.cachingTime = 0;
        params.url = rootUrl + "//getCountrySuggestions";
        params.onSuccess = onSuccess;
        params.params = new TreeMap<String, Object>();
        params.params.put("query", query);
        params.key = "suggestions";

        httpClient.getList(params);
    }

    public List<Suggestion> getCitySuggestions(String query, int countryId) throws IOException {
        String url = rootUrl + "//getCitySuggestions";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("query", query);
        params.put("countryId", countryId);
        return httpClient.getListSync(url, params, "suggestions", Suggestion.class);
    }

    public NavigationList<DialogInfo> getDialogs() {
        GetNavigationListParams<DialogInfo> params = GetNavigationListParams.create(DialogInfo.class);
        params.url = rootUrl + "//getDialogs";
        params.limit = 10;
        params.key = "dialogs";

        return httpClient.getNavigationList(params);
    }

    public NavigationList<Message> getMessages(long userId) {
        GetNavigationListParams<Message> params = GetNavigationListParams.create(Message.class);
        params.url = rootUrl + "//messages";
        params.limit = 10;
        params.key = "messages";
        params.params = Collections.<String, Object>singletonMap("userId", userId);

        return httpClient.getNavigationList(params);
    }

    public NavigationList<Reply> getReplies() {
        GetNavigationListParams<Reply> params = GetNavigationListParams.create(Reply.class);
        params.url = rootUrl + "//getReplies";
        params.limit = 10;
        params.key = "replies";

        return httpClient.getNavigationList(params);
    }

    public void getPhotoById(long photoId, OnSuccess<Photo> onSuccess) {
        final GetParams<Photo> params = GetParams.create(Photo.class);
        params.cachingTime = 0;
        params.url = rootUrl + "//getPhotoById";
        params.onSuccess = onSuccess;

        params.params = new TreeMap<String, Object>();
        params.params.put("id", photoId);
        httpClient.get(params);
    }

    private GetParams<Photo> getNextPrevPhotoParams(String url, long photoId, boolean next,
                                                    Sorting sorting,
                                                    OnSuccess<Photo> onSuccess) {
        final GetParams<Photo> params = GetParams.create(Photo.class);
        params.cachingTime = 0;
        params.url = rootUrl + url;
        params.onSuccess = onSuccess;

        params.params = new TreeMap<String, Object>();
        params.params.put("photoId", photoId);
        params.params.put("next", next);
        params.params.put("order", sorting);

        return params;
    }

    public void getNextPrevPhotoOfPhotoquest(long currentPhotoId, long photoquestId, boolean next,
                                             PhotoCategory category,
                                             Sorting sorting,
                                             OnSuccess<Photo> onSuccess) {
        final GetParams<Photo> params = getNextPrevPhotoParams("//getNextPrevPhotoOfPhotoquest",
                currentPhotoId, next, sorting, onSuccess);
        params.params.put("photoquestId", photoquestId);
        params.params.put("category", category);

        httpClient.get(params);
    }

    public void getNextPrevPhotoOfUser(long currentPhotoId, long userId, boolean next,
                                       Sorting sorting,
                                       OnSuccess<Photo> onSuccess) {
        final GetParams<Photo> params = getNextPrevPhotoParams("//getNextPrevPhotoOfUser",
                currentPhotoId, next, sorting, onSuccess);
        params.params.put("userId", userId);

        httpClient.get(params);
    }

    public void sendMessage(String message, long toUserId, OnSuccess<Message> onSuccess) {
        final GetParams<Message> params = GetParams.create(Message.class);
        params.cachingTime = 0;
        params.url = rootUrl + "//sendMessage";
        params.onSuccess = onSuccess;

        params.params = new TreeMap<String, Object>();
        params.params.put("toUserId", toUserId);
        params.params.put("message", message);
        httpClient.get(params);
    }

    public void removeIOErrorListener(IOErrorListener ioErrorListener) {
        ioErrorListeners.remove(ioErrorListener);
    }

    public void addIOErrorListener(IOErrorListener ioErrorListener) {
        ioErrorListeners.add(ioErrorListener);
    }
}
