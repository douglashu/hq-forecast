package com.hq.order.sub;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.louis.model.req.MessagePushReq;
import com.hq.louis.model.req.PushMessage;
import com.hq.order.service.entity.request.MessageCustom;
import com.hq.redis.pubsub.AbstractTopicMessageListener;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeTypes;
import com.hq.scrati.common.enums.SystemEnum;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.AmountUtil;
import com.hq.scrati.framework.FrameworkInvoker;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @包名称：com.hq.scrati.cache.redis.pubsub
 * @创建人：yyx
 * @创建时间：17/1/10 下午1:04
 */
@Service("topicMessageWithOrderCenterListener")
public class TopicMessageWithOrderCenterListener extends AbstractTopicMessageListener {

    private Logger logger = Logger.getLogger();

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Resource(name = "frameworkInvoker")
    private FrameworkInvoker frameworkInvoker;

    @Value("${order.trade.expiryInMinutes}")
    private String expiryInMinutes;

    @Value("${order.trade.maxRetryTimes}")
    private String maxRetryTimes;

    private static final Integer MCH_OPERATORS_PUSH_LIST_EXPIRE_IN_DAYS = 1;

    @Override
    public String getQueueKey() {
        return RedisKeyConfigure.TRADE_CENTER_KEY;
    }

    @Override
    protected void doProcess(String queue) {
        com.hq.scrati.common.constants.Message mess = JSON
                .parseObject(queue, com.hq.scrati.common.constants.Message.class);
        if(!TradeTypes.SWIPE_CARD.getCode().equals(mess.getTradeType())) {
            Long pubTime = new DateTime(mess.getTimestamp()).plusMinutes(Integer.parseInt(expiryInMinutes)).getMillis();
            logger.info("传入超时时间：" + mess.getTimestamp());
            logger.info("设置超时时间：" + Integer.parseInt(expiryInMinutes));
            logger.info("传出超时时间：" + pubTime);
            if (DateTime.now().getMillis() < pubTime) {
                Map<String, Object> objMap = new HashMap<>();
                Map<String, Object> parameters = new HashMap<>();
                String mchId = mess.getMchId(); // 商户ID

                // 获取商户操作员信息列表
                String cacheKey = RedisKeyConfigure.SidMchOperatorsPushList(mchId);
                List<String> address = redisCacheDao.read(cacheKey, ArrayList.class);
                if (address == null) {
                    address = new ArrayList<>();
                    objMap.clear();
                    parameters.put("bizContent", mchId);
                    objMap.put("request", parameters);
                    RespEntity<List<HashMap>> operConfigs = (RespEntity) frameworkInvoker
                            .invoke("sid.oper.configsByMchId", "1.0", objMap);
                    if (null != operConfigs && null != operConfigs.getExt()) {
                        List<String> tempList = new ArrayList<>();
                        operConfigs.getExt().stream().forEach(o -> {
                            if ((Boolean) o.get("isPush")) {
                                tempList.add(SystemEnum.APP.getCode() + ":" + o.get("operId"));
                            }
                        });
                        address.addAll(tempList);
                        this.redisCacheDao.save(cacheKey, address
                                , MCH_OPERATORS_PUSH_LIST_EXPIRE_IN_DAYS * 24 * 60 * 60L);
                    }
                }
                if (address.size() > 0) {
                    MessageCustom custom = new MessageCustom();
                    custom.setType("diego.trade.success");
                    custom.setOrderId(mess.getOrderId());
                    MessagePushReq messagePushReq = new MessagePushReq();
                    messagePushReq.setAppId("001");
                    PushMessage message1 = new PushMessage();
                    message1.setMaxRetryTimes(Integer.parseInt(maxRetryTimes));
                    message1.setExpiryTime(pubTime);
                    message1.setCustom(JSONObject.toJSONString(custom));
                    Double money = AmountUtil.fen2Yuan(mess.getTotalAmount().longValue());
                    message1.setTitle("成功收款" + money + "元");
                    message1.setTicker(message1.getTitle());
                    message1.setContent("成功收款" + money + "元，来自"
                            + PayChannels.fromString(mess.getPayChannel()).getName());
                    message1.setAddress(address);
                    objMap.clear();
                    parameters.clear();
                    messagePushReq.setMessage(message1);
                    parameters.put("appId", "001");
                    parameters.put("bizContent", JSONObject.toJSONString(messagePushReq));
                    objMap.put("request", parameters);
                    frameworkInvoker.invoke("louis.message.push", "1.0", objMap);
                }
            }
        }
    }
}
