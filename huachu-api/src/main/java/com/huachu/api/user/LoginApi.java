package com.huachu.api.user;

import javax.servlet.http.HttpServletResponse;

import com.huachu.common.exception.BOException;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huachu.common.constants.Constant;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.holder.HttpRequestHolder;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.dto.request.LoginReqDTO;
import com.huachu.dto.response.LoginRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author Administrator
 * @DATE 2018/8/20
 */
@Api(description = "登录登出用户信息获取接口")
@RestController
public class LoginApi extends AbstractApi {

    @Autowired
    private LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApiResult<LoginRespDTO> login(@RequestBody @Validated LoginReqDTO loginReqDTO, BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
        return new ApiResult<>(loginService.login(loginReqDTO));
    }

    @ApiOperation("获取用户登录信息")
    @GetMapping("/user/info")
    public ApiResult<AuthUserDTO> userInfo() {
        AuthUserDTO authUser = getUser();
        return new ApiResult<>(authUser);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public ApiResult<Boolean> logout(HttpServletResponse response) {
        HttpRequestHolder.setContext(Constant.USER_CONTEXT, null);
        HttpRequestHolder.setContext(Constant.USER_CLIENT, null);
        return new ApiResult<>(Boolean.TRUE);
    }
}
