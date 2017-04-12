package com.hq.peaches.sub;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.brooke.model.WxUserInfo;
import com.hq.brooke.model.view.req.WxUserInfoReq;
import com.hq.ellie.service.entity.request.CustomerRequest;
import com.hq.ellie.service.entity.response.CustomerResponse;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.peaches.service.constant.OpenIdSubscribeType;
import com.hq.peaches.service.constant.SylviaThirdConfig;
import com.hq.peaches.service.entity.request.MemberOpenIdReq;
import com.hq.peaches.service.entity.request.MemberReq;
import com.hq.peaches.service.entity.request.OrderQueryReq;
import com.hq.peaches.service.entity.response.MemberRsp;
import com.hq.peaches.service.entity.response.TradeOrderRsp;
import com.hq.redis.pubsub.AbstractTopicMessageListener;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.FrameworkInvoker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @包名称：com.hq.scrati.cache.redis.pubsub
 * @创建人：yyx
 * @创建时间：17/1/10 下午1:04
 */
@Service("topicMessageWithUserCenterListener")
public class TopicMessageWithUserCenterListener extends AbstractTopicMessageListener {

    private Logger logger = Logger.getLogger();

    @Resource(name = "frameworkInvoker")
    private FrameworkInvoker frameworkInvoker;

    @Override
    public String getQueueKey() {
        return RedisKeyConfigure.USER_CENTER_KEY;
    }

    @Override
    protected void doProcess(String queue) {
        com.hq.scrati.common.constants.Message mess = JSON.parseObject(queue, com.hq.scrati.common.constants.Message.class);

        //2、获取订单数据，根据订单Id查询对应的支付宝或者微信的openId
        Map<String, Object> objMap = new HashMap<String, Object>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        OrderQueryReq orderQueryReq = new OrderQueryReq();
        orderQueryReq.setId(mess.getOrderId());
        orderQueryReq.setType("order");
        parameters.put("bizContent", JSONObject.toJSONString(orderQueryReq));
        objMap.put("request", parameters);
        RespEntity orderRst = (RespEntity) frameworkInvoker.invoke("diego.trade.queryone", "1.0", objMap);
        if (null != orderRst && null != orderRst.getExt() && StringUtils.isNotBlank(orderRst.getExt().toString())) {
            TradeOrderRsp orderRsp = JSONObject.parseObject(JSONObject.toJSONString(orderRst.getExt()), new TypeReference<TradeOrderRsp>() {
                    });

            String mchId = orderRsp.getMchId(); // 商户ID

            String openId = orderRsp.getSubOpenId(); // openId
            String isOpenIdSuccess = orderRsp.getSubIsSubscribe();
            Boolean platform = Boolean.FALSE;
            if (StringUtils.isBlank(openId)) {
                openId = orderRsp.getOpenId();
                isOpenIdSuccess = orderRsp.getIsSubscribe();
                platform = Boolean.TRUE;
            }
            String pay_channel = orderRsp.getPayChannel(); // 支付方式
            Integer plat_type = null;
            if (pay_channel.equals(PayChannels.ALI_PAY.getCode())) {
                plat_type = PayChannels.ALI_PAY.getValue();
            } else if (pay_channel.equals(PayChannels.WEIXIN_PAY.getCode())) {
                plat_type = PayChannels.WEIXIN_PAY.getValue();
            }

            //3、根据openId查询会员信息，
            objMap.clear();
            parameters.clear();
            MemberOpenIdReq req = new MemberOpenIdReq();
            req.setOpenId(openId);
            parameters.put("bizContent", JSONObject.toJSONString(req));
            objMap.put("request", parameters);
            RespEntity memberRst = (RespEntity) frameworkInvoker.invoke("peaches.member.queryByOpenId", "1.0", objMap);
            if (null != memberRst && null != memberRst.getExt()) {
                MemberRsp rsp = JSONObject.parseObject(JSONObject.toJSONString(memberRst.getExt()), new TypeReference<MemberRsp>() {
                        });

                objMap.clear();
                parameters.clear();
                MemberReq memberReq = new MemberReq();
                BeanUtils.copyProperties(rsp, memberReq);
                memberReq.setVisit_num((memberReq.getVisit_num() == null ? 0 : memberReq.getVisit_num()) + 1);

                parameters.put("bizContent", JSONObject.toJSONString(memberReq));
                parameters.put("userId", orderRsp.getOperatorId());
                objMap.put("request", parameters);

                RespEntity updateMmemberRst = (RespEntity) frameworkInvoker.invoke("peaches.member.update", "1.0", objMap);
                //                    if( null != memberRst && null != memberRst.getExt() ){
                //                        Boolean flag = Boolean.valueOf(memberRst.getExt());
                //                    }
            } else {
                //4、根据openId查询顾客信息，没有的话做资料保存操作。
                CustomerRequest customerReq = new CustomerRequest();
                RespEntity custromerRst = (RespEntity) frameworkInvoker.invoke("ellie.customer.queryByOpenId", "1.0", objMap);
                if (null != custromerRst && null != custromerRst.getExt()) {
                    CustomerResponse customerRsp = JSONObject
                            .parseObject(JSONObject.toJSONString(custromerRst.getExt()), new TypeReference<CustomerResponse>() {
                            });
                    BeanUtils.copyProperties(customerRsp, customerReq);

                    objMap.clear();
                    parameters.clear();
                    parameters.put("bizContent", JSONObject.toJSONString(customerReq));
                    objMap.put("request", parameters);
                    frameworkInvoker.invoke("ellie.customer.afterPaySuccessed", "1.0", objMap);

                } else {
                    // 构建客户对象
                    customerReq.setPlat_type(plat_type);
                    customerReq.setOpenid(openId);
                    customerReq.setMchId(mchId);

                    // 调用微信接口获取头像信息
                    if (!StringUtils.isBlank(isOpenIdSuccess) && isOpenIdSuccess.equals(OpenIdSubscribeType.SUBSCRIBE_Y.getValue()) && plat_type == PayChannels.WEIXIN_PAY
                            .getValue()) {
                        objMap.clear();
                        WxUserInfoReq wxUserInfoReq = new WxUserInfoReq();
                        wxUserInfoReq.setOpenId(openId);
                        if (platform) {
                            wxUserInfoReq.settAppId(SylviaThirdConfig.WX_APP_ID);
                            wxUserInfoReq.settAppSecret(SylviaThirdConfig.WX_APP_SECRET);
                        } else {
                            // TODO 以后要根据商户的Id查询appId和secret
                            wxUserInfoReq.settAppId(SylviaThirdConfig.WX_APP_ID);
                            wxUserInfoReq.settAppSecret(SylviaThirdConfig.WX_APP_SECRET);
                        }
                        objMap.clear();
                        parameters.clear();
                        parameters.put("bizContent", JSONObject.toJSONString(wxUserInfoReq));
                        objMap.put("request", parameters);
                        RespEntity wxUserInfo = (RespEntity) frameworkInvoker.invoke("brooke.wx.user.info", "1.0", objMap);
                        if (null != wxUserInfo && null != wxUserInfo.getExt()) {
                            WxUserInfo userInfo = JSONObject.parseObject(JSONObject.toJSONString(wxUserInfo.getExt()), new TypeReference<WxUserInfo>() {
                                    });
                            customerReq.setNickname(userInfo.getNickname());
                            customerReq.setProfile_photo(userInfo.getHeadimgurl());
                            customerReq.setSex(userInfo.getSex().toString());
                        }
                    }

                    objMap.clear();
                    parameters.clear();
                    parameters.put("bizContent", JSONObject.toJSONString(customerReq));
                    objMap.put("request", parameters);
                    frameworkInvoker.invoke("ellie.customer.afterPaySuccessed", "1.0", objMap);

                }

            }
        }

    }
}
