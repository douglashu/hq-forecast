package com.hq.diego.gateway.service.core.route;

import com.hq.diego.gateway.model.ChannelRoutes;
import com.hq.diego.model.req.channel.TradeChannelReq;
import com.hq.diego.repository.dao.generate.TRudyChannelRouteMapper;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyChannelRouteExample;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeTypes;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyang on 12/03/2017.
 */
@Service
public class ChannelRouteService {

    private static final Logger logger = Logger.getLogger(ChannelRouteService.class);

    private static final int CHANNEL_ROUTES_EXPIRE_IN_DAYS = 5;

    public static final List<String> CHANNEL_STATES = Arrays.asList("NORMAL", "DISABLE");

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TRudyChannelRouteMapper channelRouteMapper;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    public void refreshCache() {
        String cacheKey = RedisKeyConfigure.RudyChannelRoutesCacheKey();
        try {
            this.redisCacheDao.delete(cacheKey, ChannelRoutes.class);
        } catch (Throwable th) {
            logger.warn("<<< Delete Redis Cache Key(" + cacheKey + ") Fail", th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }

    public List<TRudyChannelRoute> getChannelRoutes() {
        String cacheKey = RedisKeyConfigure.RudyChannelRoutesCacheKey();
        ChannelRoutes routes = null;
        try {
            routes = this.redisCacheDao.read(cacheKey, ChannelRoutes.class);
        } catch (Throwable th) {
            logger.warn("<<< Read Channel Routes From Redis Fail", th);
        }
        if (routes == null) {
            List<TRudyChannelRoute> _routes = getNormalChannelRoutesFromDB();
            if (_routes != null && _routes.size() > 0) {
                try {
                    routes = new ChannelRoutes();
                    routes.setRoutes(_routes);
                    this.redisCacheDao.save(cacheKey, routes, CHANNEL_ROUTES_EXPIRE_IN_DAYS * 24 * 60 * 60L);
                } catch (Throwable th) {
                    logger.warn("<<< Save Channel Routes To Redis Fail", th);
                }
                return _routes;
            }
        } else {
            if (routes != null && routes.getRoutes() != null && routes.getRoutes().size() > 0) {
                return routes.getRoutes();
            }
        }
        return new ArrayList<>();
    }

    public String saveChannelRoute(TradeChannelReq tradeChannelReq, boolean createNew) {
        validateTradeChannelReq(tradeChannelReq);
        Integer payChannels = 0;
        for (String payChannel: tradeChannelReq.getPayChannels()) {
            PayChannels.PayChannel _payChannel = PayChannels.fromString(payChannel);
            payChannels |= _payChannel.getValue();
        }
        if (!createNew) {
            if (StringUtils.isEmpty(tradeChannelReq.getId())) {
                throw new CommonException(CommonErrCode.ARGS_INVALID, "通道id不能为空");
            }
            if (this.channelRouteMapper.selectByPrimaryKey(tradeChannelReq.getId()) == null) {
                throw new CommonException(CommonErrCode.ARGS_INVALID, "通道信息不存在");
            }
        } else {
            tradeChannelReq.setId(DateTime.now().toString("YYYYMMddHHmmss"));
        }
        TRudyChannelRoute channelRoute = new TRudyChannelRoute();
        channelRoute.setId(tradeChannelReq.getId());
        channelRoute.setName(tradeChannelReq.getName());
        channelRoute.setChannel(tradeChannelReq.getChannel());
        channelRoute.setPayChannel(payChannels);
        channelRoute.setTradeType(tradeChannelReq.getTradeType());
        channelRoute.setCostRate(tradeChannelReq.getCostRate());
        channelRoute.setGuidingRate(tradeChannelReq.getGuidingRate());
        channelRoute.setState(tradeChannelReq.getState());
        channelRoute.setTradeService(tradeChannelReq.getTradeService());
        channelRoute.setQueryService(tradeChannelReq.getQueryService());
        channelRoute.setCancelService(tradeChannelReq.getCancelService());
        channelRoute.setPriority(tradeChannelReq.getPriority());
        if (createNew) {
            this.channelRouteMapper.insert(channelRoute);
        } else {
            this.channelRouteMapper.updateByPrimaryKeySelective(channelRoute);
        }
        try {
            refreshCache();
            getChannelRoutes();
        } catch (Throwable th) {
            // ignore
        }
        return channelRoute.getId();
    }

    private List<TradeChannelReq> convert(List<TRudyChannelRoute> channelRoutes) {
        List<TradeChannelReq> channels = new ArrayList<>();
        if (channelRoutes == null || channelRoutes.size() == 0) return channels;
        for(TRudyChannelRoute channelRoute: channelRoutes) {
            TradeChannelReq channel = new TradeChannelReq();
            channel.setId(channelRoute.getId());
            channel.setChannel(channelRoute.getChannel());
            channel.setCostRate(channelRoute.getCostRate());
            channel.setGuidingRate(channelRoute.getGuidingRate());
            channel.setTradeType(channelRoute.getTradeType());
            channel.setName(channelRoute.getName());
            channel.setTradeService(channelRoute.getTradeService());
            channel.setQueryService(channelRoute.getQueryService());
            channel.setCancelService(channelRoute.getCancelService());
            channel.setPriority(channelRoute.getPriority());
            channel.setState(channelRoute.getState());
            List<String> payChannels = new ArrayList<>();
            for (PayChannels.PayChannel payChannel: PayChannels.ALL) {
                if ((payChannel.getValue() & channelRoute.getPayChannel()) != 0) {
                    payChannels.add(payChannel.getCode());
                }
            }
            channel.setPayChannels(payChannels);
            channels.add(channel);
        }
        return channels;
    }

    public List<TradeChannelReq> getChannelRoutesFromDB() {
        TRudyChannelRouteExample example = new TRudyChannelRouteExample();
        example.setOrderByClause("channel asc, trade_type asc, pay_channel asc");
        return convert(this.channelRouteMapper.selectByExample(example));
    }

    public List<TRudyChannelRoute> getNormalChannelRoutesFromDB() {
        TRudyChannelRouteExample example = new TRudyChannelRouteExample();
        example.createCriteria().andStateEqualTo("NORMAL");
        example.setOrderByClause("channel asc, trade_type asc, pay_channel asc");
        return this.channelRouteMapper.selectByExample(example);
    }

    private void validateTradeChannelReq(TradeChannelReq tradeChannelReq) {
        if (tradeChannelReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (StringUtils.isEmpty(tradeChannelReq.getName())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道名称不能为空[channel]");
        }
        if (StringUtils.isEmpty(tradeChannelReq.getChannel())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道代码不能为空[channel]");
        }
        if (tradeChannelReq.getPayChannels() == null
                || tradeChannelReq.getPayChannels().size() == 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "支付渠道不能为空[payChannels]");
        }
        for (String payChannel: tradeChannelReq.getPayChannels()) {
            PayChannels.PayChannel _payChannel = PayChannels.fromString(payChannel);
            if (_payChannel == null) {
                throw new CommonException(CommonErrCode.ARGS_INVALID
                        , "支付方式错误[payChannel=" + payChannel + "]");
            }
        }
        if (StringUtils.isEmpty(tradeChannelReq.getTradeType())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易类型不能为空[tradeType]");
        }
        if (TradeTypes.fromString(tradeChannelReq.getTradeType()) == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "交易类型错误[tradeType=" + tradeChannelReq.getTradeType() + "]");
        }
        if (tradeChannelReq.getCostRate() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道成本费率不能为空[costRate]");
        }
        if (tradeChannelReq.getCostRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "通道成本费率格式错误[costRate=" + tradeChannelReq.getCostRate() + "]");
        }
        if (tradeChannelReq.getGuidingRate() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道指导费率不能为空[guidingRate]");
        }
        if (tradeChannelReq.getGuidingRate().compareTo(tradeChannelReq.getCostRate()) < 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道指导费率不能低于成本费率");
        }
        if (StringUtils.isEmpty(tradeChannelReq.getTradeService())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易服务不能为空[tradeService]");
        }
        if (!hasBean(tradeChannelReq.getTradeService())) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , "交易服务不存在[tradeService=" + tradeChannelReq.getTradeService() + "]");
        }
        if (StringUtils.isEmpty(tradeChannelReq.getQueryService())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "查询服务不能为空[queryService]");
        }
        if (!hasBean(tradeChannelReq.getQueryService())) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , "查询服务不存在[queryService=" + tradeChannelReq.getQueryService() + "]");
        }
        if (StringUtils.isEmpty(tradeChannelReq.getCancelService())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "撤销服务不能为空[cancelService]");
        }
        if (!hasBean(tradeChannelReq.getCancelService())) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , "撤销服务不存在[cancelService=" + tradeChannelReq.getCancelService() + "]");
        }
        if (tradeChannelReq.getPriority() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道优先级不能为空[priority]");
        }
        if (StringUtils.isEmpty(tradeChannelReq.getState())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道状态不能为空[state]");
        }
        boolean found = false;
        for (String state: CHANNEL_STATES) {
            if (state.equals(tradeChannelReq.getState())) {
                found = true;
            }
        }
        if (!found) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "通道状态错误[state=" + tradeChannelReq.getState() + "]");
        }
    }

    private boolean hasBean(String beanName) {
        Object obj = null;
        try {
            obj = this.applicationContext.getBean(beanName);
        } catch (Throwable th) {
            // ignore
        }
        return obj != null;
    }

}
