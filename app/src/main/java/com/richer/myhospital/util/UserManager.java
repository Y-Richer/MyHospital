package com.richer.myhospital.util;

import com.richer.myhospital.login.model.User;

public class UserManager {

    private static User user;
    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static void setUser(User u) {
        user  = u;
    }

}
