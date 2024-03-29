package com.huachu.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import com.huachu.common.exception.BOException;
import com.huachu.common.util.IPUtil;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @DATE 2018/08/16
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleExceptions(HttpServletRequest request, Exception ex) {
        String simpleName = ex.getClass().getSimpleName();
        if ("ClientAbortException".equals(simpleName)) {
            log.debug(getRequestString(request)+ex.getMessage());
        } else {
            log.error(getRequestString(request),ex);
        }
        return new ApiResult<>(ApiResultCode.SERVICE_ERROR);
    }

    @ExceptionHandler (BOException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleBusinessExceptions(HttpServletRequest request,BOException ex) {
        log.info(getRequestString(request)+ex.getMessage());
        if(ex.getMsg()!=null) {
            return new ApiResult<>(ex.getCode(),ex.getMsg());
        }
        return new ApiResult<>(ex.getCode(), ex.getMsg());
    }


    @ExceptionHandler (MissingServletRequestParameterException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleMissingServletRequestParameterException(HttpServletRequest request,MissingServletRequestParameterException ex){
        log.error(getRequestString(request)+ex.getMessage());
        return new ApiResult<>(ApiResultCode.PARAM_ERROR.getCode(),"参数 '"+ex.getParameterName()+"'不能为空");
    }


    @ExceptionHandler (HttpMediaTypeNotSupportedException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,HttpMediaTypeNotSupportedException ex){
        log.error(getRequestString(request)+ex.getMessage());
        return new ApiResult<>(ApiResultCode.PARAM_ERROR.getCode(),"不支持的contentType '"+ex.getContentType()+"'");
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleMethodArgumentNotValidException(HttpServletRequest request,MethodArgumentNotValidException ex){
        log.error(getRequestString(request),ex);
        List<ObjectError> errors= ex.getBindingResult().getAllErrors();
        return new ApiResult<>(ApiResultCode.PARAM_ERROR.getCode(),errors.get(0).getDefaultMessage());
    }

    @ExceptionHandler (ResourceAccessException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleResourceAccessException(HttpServletRequest request,ResourceAccessException ex){
        log.error(getRequestString(request),ex);
        return new ApiResult<>(ApiResultCode.SERVICE_ERROR.getCode(),"服务器内部超时");
    }

    @ExceptionHandler (TypeMismatchException.class)
    @ResponseStatus (HttpStatus.OK)
    @ResponseBody
    public ApiResult<Void> handleTypeMismatchException(HttpServletRequest request,TypeMismatchException ex){
        log.error(getRequestString(request),ex);
        return new ApiResult<>(ApiResultCode.PARAM_ERROR.getCode(),"参数'"+ex.getValue()+"'类型错误");
    }

    private static String getRequestString(HttpServletRequest request){
        return getRequestParameter(request)+" 请求错误";
    }


    public static String getRequestParameter(HttpServletRequest request){
        StringBuffer sb = new StringBuffer();
        sb.append(IPUtil.getRemoteAddress(request)).append(" ")
                .append(request.getMethod()).append(" ")
                .append(request.getRequestURI()).append(" ");
        if(request.getContentType() !=null){
            sb.append(request.getContentType()).append(" ");
        }
        if(request.getParameterMap()!=null&&request.getParameterMap().size()!=0){
            sb.append(parameterMapToString(request.getParameterMap())).append(" ");
        }
        if(isMultipart(request)){
            sb.append(humanReadableByteCount(request.getContentLength())).append(" ");
        }
        return sb.toString();
    }

    private static boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType()!=null && request.getContentType().startsWith("multipart/form-data");
    }

    private static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = String.valueOf("KMGTPE".charAt(exp-1));
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private static String parameterMapToString(Map<String, String[]> map){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue()[0]+"&");
        }
        return sb.substring(0, sb.length()-1);
    }
}
