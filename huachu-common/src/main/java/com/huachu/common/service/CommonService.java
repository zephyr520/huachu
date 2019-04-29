package com.huachu.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huachu.common.constants.RedisConst;

/**
 * @author Administrator
 * @DATE 2018/8/27
 */
@Component
public class CommonService {

    @Autowired
    RedisService redisService;

    /***
     * @description 验证用户是否具有操作权限
     * @param userId 用户ID
     * @param permitIdentifier 操作权限码
     * @return
     */
    public boolean verifyUserOperAuthorized(Integer userId, String permitIdentifier) {
        boolean isAuthorized = Boolean.FALSE;
        if(userId == null) {
            return isAuthorized;
        }
        String key = RedisConst.USER_OPER_AUTH + userId + RedisConst.KEY_SPLITER + permitIdentifier;
        String code = redisService.get(key);
        if (code != null && code.equals(permitIdentifier)) {
            isAuthorized = Boolean.TRUE;
        }
        return isAuthorized;
    }
}
