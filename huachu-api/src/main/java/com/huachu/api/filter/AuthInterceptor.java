package com.huachu.api.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Objects;
import com.huachu.common.constants.ClientType;
import com.huachu.common.constants.Constant;
import com.huachu.common.constants.RedisConst;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.dto.UserClient;
import com.huachu.common.exception.BOException;
import com.huachu.common.holder.HttpRequestHolder;
import com.huachu.common.service.RedisService;
import com.huachu.common.util.DateUtils;
import com.huachu.common.util.TokenUtil;
import com.huachu.common.web.ApiResultCode;

/**
 * @author Administrator
 * @DATE 2018/8/17
 * 校验是否登录拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private final static String XAUTH_TOKEN_HEADER_NAME = "x-auth-token";

    private final static String XAUTH_TOKEN_PARAMETER_NAME = "auth_token";
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authTokenHeader = request.getHeader(XAUTH_TOKEN_HEADER_NAME);
        String authTokenParameter = request.getParameter(XAUTH_TOKEN_PARAMETER_NAME);
        String authToken = authTokenHeader != null ? authTokenHeader : authTokenParameter != null ? authTokenParameter : null;
        if(Objects.equal(authToken, null)) {
            throw new BOException(ApiResultCode.NO_LOGIN);
        }
        UserClient uc = TokenUtil.getUserIdAndClientTypeFromAccessToken(authToken);
        if (Objects.equal(uc, null)) {
        	throw new BOException(ApiResultCode.TOKEN_INVALID);
        }
        boolean isExpire = false;
        Long genTokenTime = uc.getGenTime();
        logger.info("token生成时间：{}", DateUtils.ParseToString(new Date(genTokenTime)));
        Long remainTime = System.currentTimeMillis() - genTokenTime;
        logger.info("token生成到当前时间过了多久：{}", DateUtils.secondToTime(remainTime));
        if (ClientType.ANDROID.equals(uc.getClientType()) || ClientType.IOS.equals(uc.getClientType())) {
        	isExpire = remainTime > Constant.ACCESS_TOKEN_APP_EXPIRED_SECONDS;
        } else {
        	isExpire = remainTime > Constant.ACCESS_TOKEN_EXPIRED_SECONDS;
        }
        if(isExpire) {
            throw new BOException(ApiResultCode.TOKEN_EXPIRE);
        }
        AuthUserDTO authUser = redisService.get(RedisConst.LOGIN_USER_INFO + uc.getUserId(), AuthUserDTO.class);
        HttpRequestHolder.setContext(Constant.USER_CONTEXT, authUser);
        HttpRequestHolder.setContext(Constant.USER_CLIENT, uc);
        return super.preHandle(request, response, handler);
    }
}
