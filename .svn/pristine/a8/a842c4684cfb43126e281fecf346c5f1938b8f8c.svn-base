package com.huachu.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
public class CookieUtil {

    /***
     * 添加Cookie
     * @param name
     * @param value
     * @param expiry
     * @param response
     */
    public static void addCookie(String name,String value, int expiry, HttpServletResponse response) {
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(expiry > 0)
            cookie.setMaxAge(expiry);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /***
     * 删除Cookie
     * @param name
     * @param response
     */
    public static void removieCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /***
     * 根据cookie名称获取值
     * @param cookieName
     * @param request
     * @return
     */
    public static String getCookieValue(String cookieName,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(ArrayUtil.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if(cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
