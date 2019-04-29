package com.huachu.common.web;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
@ApiResponses({
        @ApiResponse(code=101, message="没有登录会话"),
        @ApiResponse(code=102, message="没有权限"),
        @ApiResponse(code=103, message="访问超时"),
        @ApiResponse(code=104, message="签名验证不通过"),
        @ApiResponse(code=105, message="签名过期"),
        @ApiResponse(code=200, message="SUCCESS"),
        @ApiResponse(code=300, message="请求参数错误"),
        @ApiResponse(code=500, message="系统异常"),
})
public interface BaseApi {
}
