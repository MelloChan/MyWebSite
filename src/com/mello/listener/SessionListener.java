package com.mello.listener;

import com.mello.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/12.
 */
@WebListener(value = "监听session并记录为Hash表")
public class SessionListener implements HttpSessionListener {
    private static final Map<String, User> SESSIONS = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("Session destroyed");
    }

    public static void addUser(String email, User user) {
        SESSIONS.put(email, user);
    }

    public static Boolean hasSession(String email) {
        boolean flag = false;
        Set<String> keySet = SESSIONS.keySet();
        synchronized (keySet) {
            for (String string : keySet) {
                if (email.equals(string)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static String getUserName(String email) {
        User user = SESSIONS.get(email);
        String username = "";
        if (user != null) {
            username = user.getUsername();
        }
        return username;
    }

    public static void removeSession(String email) {
        SESSIONS.remove(email);
    }
}
