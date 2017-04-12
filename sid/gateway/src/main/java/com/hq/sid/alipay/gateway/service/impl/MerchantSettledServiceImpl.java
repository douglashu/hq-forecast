package com.hq.sid.alipay.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.domain.AntMerchantExpandEnterpriseApplyModel;
import com.alipay.api.domain.AntMerchantExpandMapplyorderQueryModel;
import com.alipay.api.domain.AntMerchantExpandPersonalApplyModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.log.Logger;
import com.hq.sid.alipay.gateway.service.MerchantSettledService;
import com.hq.sid.alipay.gateway.service.common.*;
import com.hq.sid.alipay.gateway.service.executor.AliServiceExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.impl
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:10
 */
@Service
public class MerchantSettledServiceImpl implements MerchantSettledService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private IAntMerchantExpandEnterpriseApplyValidate antMerchantExpandEnterpriseApplyValidate;

    @Autowired
    private IAntMerchantExpandImageUploadValidate antMerchantExpandImageUploadValidate;

    @Autowired
    private IAntMerchantExpandMapplyorderQueryValidate antMerchantExpandMapplyorderQueryValidate;

    @Autowired
    private IAntMerchantExpandPersonalApplyValidate antMerchantExpandPersonalApplyValidate;


    @Autowired
    private AliServiceExecutor executor;

    private AlipayClient alipayClient;

    @Override
    public AlipayOpenAuthTokenAppResponse  alipayOpenAuthToken(AlipayOpenAuthTokenAppRequest request) {

        logger.info("使用auth_code换取接口access_token及用户userId接口请求参数：" + JSONObject.toJSONString(request));
        // 创建支付宝公共请求对象
        alipayClient = executor.getClient();

        AlipayOpenAuthTokenAppResponse response = null;

        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("使用auth_code换取接口access_token及用户userId接口异常");
        }

        // 记录调用日志
        if (response.isSuccess()) {
            logger.info("使用auth_code换取接口access_token及用户userId调用成功:" + JSONObject.toJSONString(response));
        } else {
            logger.info("使用auth_code换取接口access_token及用户userId调用失败:" + JSONObject.toJSONString(response));
        }
        return response;
    }

    @Override
    public AntMerchantExpandMapplyorderQueryResponse antMerchantExpandMapplyorderQuery(
            AntMerchantExpandMapplyorderQueryModel model) {

        antMerchantExpandMapplyorderQueryValidate.validate(model);

        // 转成json字符串
        String bizContent = JSONObject.toJSONString(model);

        logger.info("商户入驻单查询接口请求参数：" + bizContent);

        // 创建支付宝公共请求对象
        alipayClient = executor.getClient();

        // 设置商户入驻单查询接口对象值
        AntMerchantExpandMapplyorderQueryRequest request = new AntMerchantExpandMapplyorderQueryRequest();
        request.setBizContent(bizContent);
        AntMerchantExpandMapplyorderQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("商户入驻查询接口异常");
        }

        // 记录调用日志
        if (response.isSuccess()) {
            logger.info("商户入驻查询接口调用成功:" + JSONObject.toJSONString(response));
        } else {
            logger.info("商户入驻查询接口调用失败:" + JSONObject.toJSONString(response));
        }
        return response;
    }

    @Override
    public AntMerchantExpandPersonalApplyResponse antMerchantExpandPersonalApply(AntMerchantExpandPersonalApplyModel model) {
        antMerchantExpandPersonalApplyValidate.validate(model);

        // 转成json字符串
        String bizContent = JSONObject.toJSONString(model);

        logger.info("个体工商户入驻接口请求参数：" + bizContent);

        // 创建支付宝公共请求对象
        alipayClient = executor.getClient();

        // 设置商户入驻单查询接口对象值
        AntMerchantExpandPersonalApplyRequest request = new AntMerchantExpandPersonalApplyRequest();
        request.setBizContent(bizContent);
        AntMerchantExpandPersonalApplyResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("个体工商户入驻接口异常");
        }
        if (response.isSuccess()) {
            System.out.println("个体工商户入驻接口调用成功:" + JSONObject.toJSONString(response));
        } else {
            System.out.println("个体工商户入驻接口调用失败:" + JSONObject.toJSONString(response));
        }

        return response;
    }

    @Override
    public AntMerchantExpandEnterpriseApplyResponse antMerchantExpandEnterpriseApply(
            AntMerchantExpandEnterpriseApplyModel model) {

        antMerchantExpandEnterpriseApplyValidate.validate(model);

        // 转成json字符串
        String bizContent = JSONObject.toJSONString(model);
        logger.info("企业级商户入驻接口请求参数：" + bizContent);

        // 创建支付宝公共请求对象
        alipayClient = executor.getClient();

        // 设置商户入驻单查询接口对象值
        AntMerchantExpandEnterpriseApplyRequest request = new AntMerchantExpandEnterpriseApplyRequest();
        request.setBizContent(bizContent);
        AntMerchantExpandEnterpriseApplyResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("企业级商户入驻接口异常");
        }
        if (response.isSuccess()) {
            System.out.println("企业级商户入驻接口调用成功:" + JSONObject.toJSONString(response));
        } else {
            System.out.println("企业级商户入驻接口调用失败:" + JSONObject.toJSONString(response));
        }

        return response;
    }

    @Override
    public AntMerchantExpandImageUploadResponse antMerchantExpandImageUpload(FileItem fileItem) {
        antMerchantExpandImageUploadValidate.validate(fileItem);

        logger.info("isv图片上传接口请求参数：" + JSONObject.toJSONString(fileItem));

        // 创建支付宝公共请求对象
        alipayClient = executor.getClient();

        // 设置商户入驻单查询接口对象值
        AntMerchantExpandImageUploadRequest request = new AntMerchantExpandImageUploadRequest();
        request.setImageType("JPG");
        request.setImageContent(fileItem);
        AntMerchantExpandImageUploadResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("isv图片上传接口异常");
        }
        if (response.isSuccess()) {
            System.out.println("isv图片上传接口调用成功:" + JSONObject.toJSONString(response));
        } else {
            System.out.println("isv图片上传接口调用失败:" + JSONObject.toJSONString(response));
        }
        return response;
    }
}
