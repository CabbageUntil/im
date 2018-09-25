package com.csxx.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    /**
     * 生成一个Cookie
     * @param name Cookie名字
     * @param value Cookie值
     * @param expire Cookie过期时间（秒）
     * @return
     */
    public static Cookie buildCookie(String name, String value, Integer expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);

        return cookie;
    }

    /**
     * 生成一个Cookie
     * @param name Cookie名字
     * @param value Cookie值
     * @param expire Cookie过期时间（秒）
     * @param domain Cookie生效域名
     * @param path Cookie生效路径
     * @return
     */
    public static Cookie buildCookie(String name, String value, Integer expire, String domain, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        cookie.setDomain(domain);
        cookie.setPath(path);

        return cookie;
    }

    public static Cookie get(HttpServletRequest request,
                             String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        }else {
            return null;
        }
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
