package com.huachu.common.holder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huachu.common.constants.Constant;
import com.huachu.common.dto.AuthUserDTO;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
public class HttpRequestHolder {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHolder.class);

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        logger.debug("requestAttributes = " + requestAttributes);
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    public static String getHeaderAttr(String key) {
        return getRequest().getHeader(key);
    }

    public static String getParam(String key) {
        return getRequest().getParameter(key);
    }

    public static void setContext(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    public static Object getContext(String key) {
        return getRequest().getAttribute(key);
    }

    public static String getCookie(String key) {
        Cookie[] cookies = getRequest().getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(key.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    public static AuthUserDTO getAuthUser() {
        AuthUserDTO authUser = (AuthUserDTO)getContext(Constant.USER_CONTEXT);
        return authUser;
    }
}
