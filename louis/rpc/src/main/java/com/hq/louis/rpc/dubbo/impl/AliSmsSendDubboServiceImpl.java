package com.hq.louis.rpc.dubbo.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.hq.louis.model.req.SMSReq;
import com.hq.louis.model.resp.SMSResp;
import com.hq.louis.rpc.dubbo.AliSmsSendDubboService;
import com.hq.louis.service.MobileMessageService;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zale on 2017/2/15.
 */
@Service(version = "1.0", interfaceClass = AliSmsSendDubboService.class)
public class AliSmsSendDubboServiceImpl implements AliSmsSendDubboService {
    private Logger logger = Logger.getLogger();
    @Value("${ali.access.key}")
    private String aliAccessKey;
    @Value("${ali.access.key.secret}")
    private String aliAccessKeySecret;
    @Value("${sms.sign.name}")
    private String signName;
    @Autowired
    private MobileMessageService mobileMessageService;
    @PostConstruct
    public void init(){
        System.out.println(signName);
    }
    @Override
    public SMSResp send(HqRequest hqRequest) {
        try {
            SMSReq req = JSON.parse(hqRequest.getBizContent(), SMSReq.class);
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliAccessKey, aliAccessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(signName);//控制台创建的签名名称
            request.setTemplateCode(req.getTemplateId());//控制台创建的模板CODE
            request.setParamString(com.alibaba.fastjson.JSON.toJSONString(req.getParams()));//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
            //request.setParamString("{}");
            request.setRecNum(req.getAddress());//接收号码
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            logger.debug("send sms through aliyun sms request id is "+httpResponse.getRequestId());
        } catch (ServerException e) {
            logger.error(e);
            throw new BusinessException("发送失败");
        } catch (ClientException e) {
            logger.error(e);
            throw new BusinessException("发送失败");
//
//
//            mobileMessageService.sendMobile(req.getAddress(),req.getParams().get("code"));
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("发送失败");
        }
        return null;
    }
}
