package com.huachu.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huachu.common.annotation.ApiAuthorized;
import com.huachu.common.constants.Constant;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.exception.BOException;
import com.huachu.common.holder.HttpRequestHolder;
import com.huachu.common.service.CommonService;
import com.huachu.common.web.ApiResultCode;

/**
 * @author Administrator
 * @DATE 2018/8/24
 * @description 操作权限码的注解验权
 */
@Aspect
@Component
public class AuthorizedAspect {

    @Autowired
    CommonService commonService;

    @Around(value = "@annotation(com.huachu.common.annotation.ApiAuthorized)")
    public Object executeAuthorized(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        AuthUserDTO authUser = HttpRequestHolder.getAuthUser();
        if (authUser == null) {
            throw new BOException(ApiResultCode.NO_LOGIN);
        }
        // 超级管理员直接放行
        if (authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
        	return joinPoint.proceed();
        }
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        ApiAuthorized apiAuthorized = signature.getMethod().getAnnotation(ApiAuthorized.class);
        if (apiAuthorized != null) {
            String permitIdentifier = apiAuthorized.value();
            Boolean isHasPermission = commonService.verifyUserOperAuthorized(authUser.getUserId(), permitIdentifier);
            if (!isHasPermission) {
                throw new BOException(ApiResultCode.NO_AUTH, "没有操作权限");
            }
        }
        result = joinPoint.proceed();
        return result;
    }
}
