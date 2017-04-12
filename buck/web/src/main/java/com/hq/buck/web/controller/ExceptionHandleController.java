package com.hq.buck.web.controller;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandleController.class);

    private final static String NOT_FOUND = "NOT_FOUND";

    private final static String HTTP_METHOD_NOT_SUPPORTED = "HTTP_METHOD_NOT_SUPPORTED";

    private final static String MEDIA_TYPE_NOT_SUPPORTED = "MEDIA_TYPE_NOT_SUPPORTED";

    private final static String MEDIA_TYPE_NOT_ACCEPTABLE = "MEDIA_TYPE_NOT_ACCEPTABLE";

    private final static String REQUEST_PARAM_REQUIRED = "REQUEST_PARAM_REQUIRED";

    private final static String REQUEST_PART_REQUIRED = "REQUEST_PART_REQUIRED";

    private final static String REQUEST_BINDING_ERROR = "REQUEST_BINDING_ERROR";

    private final static String HTTP_MESSAGE_NOT_READABLE = "HTTP_MESSAGE_NOT_READABLE";

    private final static String CONVERSION_NOT_SUPPORTED = "CONVERSION_NOT_SUPPORTED";

    private final static String METHOD_ARGUMENT_NOT_VALID = "METHOD_ARGUMENT_NOT_VALID";

    private final static String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        RespEntity entity = new RespEntity(NOT_FOUND, "找不到["
                + ex.getHttpMethod().toUpperCase() + " " + ex.getRequestURL() + "]对应的处理器");
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
        RespEntity entity = new RespEntity(HTTP_METHOD_NOT_SUPPORTED, "不支持的Http Method["
                + ex.getMethod().toUpperCase() + "],请尝试使用[" + supportedMethods.toString() + "]");
        return getJsonResp(entity, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        RespEntity entity = new RespEntity(MEDIA_TYPE_NOT_SUPPORTED,
                "不支持的Http Media Type[" + ex.getContentType().toString() + "]");
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
        RespEntity entity = new RespEntity(MEDIA_TYPE_NOT_ACCEPTABLE,
                "不支持的Http Media Type,支持列表[" + supportsList.toString() + "]");
        return getJsonResp(entity, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<RespEntity>
    handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        RespEntity entity = new RespEntity(REQUEST_PARAM_REQUIRED
                , "URL参数[" + ex.getParameterName() + "]不能为空");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleServletRequestBindingException(ServletRequestBindingException ex) {
        RespEntity entity = new RespEntity(REQUEST_BINDING_ERROR, "请求参数绑定错误");
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
        RespEntity entity = new RespEntity(METHOD_ARGUMENT_NOT_VALID, "请求参数格式错误");
        entity.setExt(results);
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        RespEntity entity = new RespEntity(REQUEST_PART_REQUIRED
                , "Request Part[" + ex.getRequestPartName() + "]不能为空");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        RespEntity entity = new RespEntity(HTTP_MESSAGE_NOT_READABLE, "请求数据解析出错");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleConversionNotSupportedException(ConversionNotSupportedException ex) {
        RespEntity entity = new RespEntity(CONVERSION_NOT_SUPPORTED
                , "类型[" + ex.getPropertyName() + "]转换出错");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public ResponseEntity<RespEntity> handleCommonException(CommonException ex) {
        RespEntity entity = new RespEntity(ex.getErrCode(), ex.getErrMsg());
        entity.setExt(ex.getExt());
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { RuntimeException.class, Exception.class, Throwable.class })
    @ResponseBody
    public ResponseEntity<RespEntity> handleException(Throwable th) {
        logger.error("<<< Internal Server Error", th);
        RespEntity entity = new RespEntity(INTERNAL_SERVER_ERROR, th.getMessage());
        return getJsonResp(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}