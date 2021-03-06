package com.dms.account_manager;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by boxfox on 2017-03-24.
 */
public class SessionUtil {

    public static String getRegistredSessionKey(RoutingContext context, String key) {
        String sessionKey = null;
        if (context.session() != null) {
            sessionKey = context.session().get(key);
        }
        if (sessionKey == null && context.getCookie(key) != null) {
            sessionKey = context.getCookie(key).getValue();
        }
        if (sessionKey != null && sessionKey.equals("null"))
            sessionKey = null;
        return sessionKey;
    }

    public static void registerSession(RoutingContext context, String key, String sessionKey) {
        context.session().put(key, sessionKey);
    }

    public static void registerCookie(RoutingContext context, String key, String sessionKey) {
        Cookie cookie = Cookie.cookie(key, sessionKey);
        String path = "/";
        cookie.setPath(path);
        cookie.setMaxAge(356 * 24 * 60 * 60);
        context.addCookie(cookie);
    }

    public static void removeCookie(RoutingContext context, String key) {
        if (context.getCookie(key) != null) {
            context.getCookie(key).setValue("null");
            registerCookie(context, key, "null");
        }
        context.session().remove(key);
    }
}
