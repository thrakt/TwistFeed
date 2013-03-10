package org.thrakt.twistfeed.service;

import java.util.List;
import java.util.Random;

import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;
import org.thrakt.twistfeed.model.AccessTokenModel;
import org.thrakt.twistfeed.model.RequestTokenModel;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterAccessService {

    private static final String ACCESS_TOKEN_LIST = "ACCESS_TOKEN_LIST";

    public static String getAuthURL(String userName) {
        Twitter twitter = TwitterFactory.getSingleton();
        String url = "";
        try {
            RequestToken token = twitter.getOAuthRequestToken();
            RequestTokenModel model = new RequestTokenModel();
            model
                .setKey(Datastore.createKey(RequestTokenModel.class, userName));
            model.setToken(token);
            Datastore.put(model);

            url = token.getAuthorizationURL();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static boolean twitterAuth(String userName, String verifier) {
        Twitter twitter = TwitterFactory.getSingleton();
        RequestTokenModel requestModel =
            Datastore.get(
                RequestTokenModel.class,
                Datastore.createKey(RequestTokenModel.class, userName));
        RequestToken requestToken = requestModel.getToken();
        Datastore.delete(requestModel.getKey());
        try {
            AccessToken accessToken =
                twitter.getOAuthAccessToken(requestToken, verifier);
            AccessTokenModel accessModel = new AccessTokenModel();
            accessModel.setKey(Datastore.createKey(
                AccessTokenModel.class,
                accessToken.getScreenName()));
            accessModel.setToken(accessToken);
            Datastore.put(accessModel);
            Memcache.delete(ACCESS_TOKEN_LIST);
        } catch (TwitterException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isAuthenticated(String userName) {
        return Datastore.getOrNull(Datastore.createKey(
            AccessTokenModel.class,
            userName)) != null;
    }

    public static Long getUserId(String name) {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthAccessToken(getAccessToken());
        try {
            return twitter.showUser(name).getId();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser(Long id) {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthAccessToken(getAccessToken());
        try {
            return twitter.showUser(id);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseList<Status> getTweetList(String id) {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthAccessToken(getAccessToken());
        try {
            return twitter.getUserTimeline(Long.valueOf(id));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static AccessToken getAccessToken() {
        List<AccessTokenModel> list = Memcache.get(ACCESS_TOKEN_LIST);
        if (list == null) {
            list = Datastore.query(AccessTokenModel.class).asList();
            Memcache.put(ACCESS_TOKEN_LIST, list);
        }
        return list
            .get((int) (list.size() * new Random().nextDouble()))
            .getToken();
    }
}
