package com.huachu.common.exception;

import com.huachu.common.web.ApiResultCode;

/**
 * 业务逻辑异常类
 * @author Administrator
 */
public class BOException extends RuntimeException {

    private static final long serialVersionUID = 1851078737997960406L;

    private int code;
    private String msg;

    public BOException(ApiResultCode resultCode){
        super("业务异常返回----code:"+resultCode.getCode()+"----message:"+resultCode.getMessage());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public BOException(String urlStr, ApiResultCode resultCode) {
        super("业务异常返回----url:"+urlStr+"----code:"+resultCode.getCode()+"----message:"+resultCode.getMessage());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public BOException(ApiResultCode resultCode,String msg){
        super("业务异常返回----code:"+resultCode.getCode()+"----message:"+msg);
        this.code = resultCode.getCode();
        this.msg = msg;
    }

    public BOException(int code, String msg) {
        super("业务异常返回----code:"+ code +"----message:"+msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
