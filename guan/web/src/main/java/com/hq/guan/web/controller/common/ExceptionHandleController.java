package com.hq.guan.web.controller.common;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by young on 6/11/15.
 */
@ControllerAdvice
public class ExceptionHandleController extends BaseController {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        RespEntity entity = new RespEntity(CommonErrCode.ARGS_INVALID.getCode()
                , "找不到处理器[" + ex.getHttpMethod().toUpperCase() + ": " + ex.getRequestURL() + "]");
        return getJsonResp(entity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        StringBuffer supportedMethods = new StringBuffer();
        if(ex.getSupportedMethods() != null && ex.getSupportedMethods().length > 0) {
            for(int i=0; i<ex.getSupportedMethods().length; i++) {
                if(i != 0) {
                    supportedMethods.append(",");
                }
                supportedMethods.append(ex.getSupportedMethods()[i]);
            }
        }
        RespEntity entity = new RespEntity(CommonErrCode.ARGS_INVALID.getCode(),
                "不支持的Http " + ex.getMethod().toUpperCase() + " 方法, 请尝试使用[" + supportedMethods.toString() + "]");
        return getJsonResp(entity, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        RespEntity entity = new RespEntity(CommonErrCode.ARGS_INVALID.getCode(),
                "不支持的Media Type[" + ex.getContentType().toString() + "]");
        return getJsonResp(entity, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        StringBuffer supportsList = new StringBuffer();
        if(ex.getSupportedMediaTypes() != null && ex.getSupportedMediaTypes().size() > 0) {
            for(int i=0; i<ex.getSupportedMediaTypes().size(); i++) {
                if(i != 0) supportsList.append(",");
                supportsList.append(ex.getSupportedMediaTypes().get(i).toString());
            }
        }
        RespEntity entity = new RespEntity(CommonErrCode.ARGS_INVALID.getCode(),
                "不支持的Media Type, 请尝试使用[" + supportsList.toString() + "]");
        return getJsonResp(entity, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(),
                "URL参数不能为空[" + ex.getParameterName() + "]");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleServletRequestBindingException(ServletRequestBindingException ex) {
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(), "请求参数绑定错误");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> results = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors != null && fieldErrors.size() > 0) {
            for(int i=0; i<fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                String value = ((fieldError.getRejectedValue() == null)? "null" : fieldError.getRejectedValue().toString());
                String reason = ((fieldError.getDefaultMessage() == null)? "" : fieldError.getDefaultMessage());
                String errorFieldMessage = "Value=" + value + "&Reason=" + reason + "";
                results.put(fieldError.getField(), errorFieldMessage);
            }
        }
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(), "请求参数格式错误", results);
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(),
                "Request Part不能为空[" + ex.getRequestPartName() + "]");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(), "请求数据解析出错");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleConversionNotSupportedException(ConversionNotSupportedException ex) {
        RespEntity entity = new RespEntity(
                CommonErrCode.ARGS_INVALID.getCode(),
                "类型转换出错[" + ex.getPropertyName() + "]");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleCommonException(CommonException ex) {
        HttpStatus httpStatus;
        if(CommonErrCode.ARGS_INVALID.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if(CommonErrCode.AUTH_TOKEN_INVALID.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if(CommonErrCode.NO_PERMISSION.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if(CommonErrCode.NO_DATA_FOUND.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if(CommonErrCode.BUSINESS.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        RespEntity entity = new RespEntity(
                ex.getErrCode(), ex.getErrMsg(), ex.getExt());
        return getJsonResp(entity, httpStatus);
    }

    @ExceptionHandler(value = { RuntimeException.class, Exception.class, Throwable.class })
    @ResponseBody
    public ResponseEntity<RespEntity> handleException(Throwable th) {
        RespEntity entity = new RespEntity(
                CommonErrCode.INTERNAL_SERVER_ERROR.getCode(), th.getMessage());
        return getJsonResp(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}