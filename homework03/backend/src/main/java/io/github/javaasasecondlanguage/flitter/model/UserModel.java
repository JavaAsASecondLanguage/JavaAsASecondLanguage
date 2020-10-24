package io.github.javaasasecondlanguage.flitter.model;

import io.github.javaasasecondlanguage.flitter.base.User;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Model class for user processing
 */
public class UserModel {

    private static ConcurrentHashMap<String, User> usersByToken = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, User> usersByName = new ConcurrentHashMap<>();

    public static boolean containsToken(String token) {
        return usersByToken.containsKey(token);
    }

    public static boolean containsUser(String user) {
        return usersByName.containsKey(user);
    }

    public static void registerUser(String username) {
        User user = new User(username);
        usersByToken.put(user.getUserToken(), user);
        usersByName.put(user.getUserName(), user);
    }

    public static List<String> getUserList() {
        return Collections.list(usersByName.keys());
    }

    public static User getUserByName(String username) {
        return usersByName.get(username);
    }

    public static User getUserByToken(String token) {
        return usersByToken.get(token);
    }

    public static void clear() {
        usersByToken.clear();
        usersByName.clear();
    }

}
