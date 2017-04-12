package com.hq.diego.gateway.service.core.route;

import com.hq.diego.gateway.model.MchChannels;
import com.hq.diego.model.req.channel.MchChannelReq;
import com.hq.diego.model.req.channel.TradeChannelReq;
import com.hq.diego.repository.dao.generate.TRudyMchChannelMapper;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.diego.repository.model.generate.TRudyMchChannelExample;
import com.hq.diego.repository.model.generate.TRudyMchChannelKey;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@Service
public class MchChannelService {

    private static final Logger logger = Logger.getLogger(MchChannelService.class);

    private static final Integer MCH_CHANNELS_EXPRE_IN_DAYS = 3;

    public static final List<String> MCH_CHANNEL_STATES = Arrays.asList("NORMAL", "DISABLE");

    @Autowired
    private TRudyMchChannelMapper mchChannelMapper;

    @Autowired
    private ChannelRouteService channelRouteService;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    public void updateCache(String mchId) {
        if (StringUtils.isEmpty(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        String cacheKey = RedisKeyConfigure.RudyMchChannelsCacheKey(mchId);
        try {
            this.redisCacheDao.delete(cacheKey, MchChannels.class);
        } catch (Throwable th) {
            logger.warn("<<< Delete Redis Cache Key(" + cacheKey + ") Fail", th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }

    public TRudyMchChannel getMchChannel(String mchId, String channel) {
        if (StringUtils.isEmpty(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        if (StringUtils.isEmpty(channel)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道不能为空[channel]");
        }
        List<TRudyMchChannel> mchChannels = getMchChannels(mchId);
        if (mchChannels == null || mchChannels.isEmpty()) return null;
        for (TRudyMchChannel mchChannel: mchChannels) {
            if (mchChannel.getChannel().equals(channel)) return mchChannel;
        }
        return null;
    }

    public List<TRudyMchChannel> getMchChannels(String mchId) {
        if (StringUtils.isEmpty(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        String cacheKey = RedisKeyConfigure.RudyMchChannelsCacheKey(mchId);
        MchChannels channels = null;
        try {
            channels = this.redisCacheDao.read(cacheKey, MchChannels.class);
        } catch (Throwable th) {
            logger.warn("<<< Read Mch Channels From Redis Fail", th);
        }
        if (channels == null) {
            List<TRudyMchChannel> _channels = getMchNormalChannelsFromDB(mchId);
            if (_channels != null && _channels.size() > 0) {
                try {
                    channels = new MchChannels();
                    channels.setChannels(_channels);
                    this.redisCacheDao.save(cacheKey, channels, MCH_CHANNELS_EXPRE_IN_DAYS * 24 * 60 * 60L);
                } catch (Throwable th) {
                    logger.warn("<<< Save Mch Channels To Redis Fail", th);
                }
                return _channels;
            }
        } else {
            if (channels != null
                    && channels.getChannels() != null
                    && channels.getChannels().size() > 0) {
                return channels.getChannels();
            }
        }
        return new ArrayList<>();
    }

    public void deleteMchChannel(String mchId, String channel) {
        if (StringUtils.isEmpty(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        if (StringUtils.isEmpty(channel)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道类型不能为空[channel]");
        }
        TRudyMchChannelKey key = new TRudyMchChannelKey();
        key.setId(mchId);
        key.setChannel(channel);
        this.mchChannelMapper.deleteByPrimaryKey(key);
        updateCache(mchId);
    }

    public TRudyMchChannel saveMchChannel(MchChannelReq mchChannelReq, boolean createNew) {
        if (mchChannelReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(mchChannelReq.getId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[id]");
        }
        if (StringUtils.isEmpty(mchChannelReq.getChannel())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "通道类型不能为空[channel]");
        }
        List<TradeChannelReq> channelRoutes = this.channelRouteService.getChannelRoutesFromDB();
        if (channelRoutes == null || channelRoutes.size() == 0) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , "通道类型不存在[channel=" + mchChannelReq.getChannel() + "]");
        }
        boolean channelFound = false;
        for (TradeChannelReq channelRoute: channelRoutes) {
            if (channelRoute.getChannel().equals(mchChannelReq.getChannel())) {
                channelFound = true;
                break;
            }
        }
        if (!channelFound) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , "通道类型不存在[channel=" + mchChannelReq.getChannel() + "]");
        }
        if (StringUtils.isEmpty(mchChannelReq.getMchId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "第三方商户号不能为空[mchId]");
        }
        if (mchChannelReq.getCodePayRate() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "扫码支付费率不能为空[codePayRate]");
        }
        if (mchChannelReq.getCodePayRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "扫码支付费率格式错误[codePayRate=" + mchChannelReq.getCodePayRate() + "]");
        }
        if (mchChannelReq.getH5PayRate() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "H5支付费率不能为空[h5PayRate]");
        }
        if (mchChannelReq.getH5PayRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "H5支付费率格式错误[h5PayRate=" + mchChannelReq.getH5PayRate() + "]");
        }
        if (StringUtils.isEmpty(mchChannelReq.getState())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户通道状态不能为空[state]");
        }
        boolean found = false;
        for (String state: MCH_CHANNEL_STATES) {
            if (state.equals(mchChannelReq.getState())) {
                found = true;
            }
        }
        if (!found) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "商户通道状态错误[state=" + mchChannelReq.getState() + "]");
        }
        TRudyMchChannel mchChannel = new TRudyMchChannel();
        mchChannel.setId(mchChannelReq.getId());
        mchChannel.setChannel(mchChannelReq.getChannel());
        mchChannel.setMchId(mchChannelReq.getMchId());
        mchChannel.setCodePayRate(mchChannelReq.getCodePayRate());
        mchChannel.setH5PayRate(mchChannelReq.getH5PayRate());
        mchChannel.setState(mchChannelReq.getState());
        mchChannel.setAppId(mchChannelReq.getAppId());
        mchChannel.setApiKey(mchChannelReq.getApiKey());
        if (createNew) {
            this.mchChannelMapper.insert(mchChannel);
        } else {
            this.mchChannelMapper.updateByPrimaryKey(mchChannel);
        }
        updateCache(mchChannelReq.getId());
        return mchChannel;
    }

    public List<TRudyMchChannel> getMchNormalChannelsFromDB(String mchId) {
        TRudyMchChannelExample example = new TRudyMchChannelExample();
        example.createCriteria().andIdEqualTo(mchId).andStateEqualTo("NORMAL");
        return this.mchChannelMapper.selectByExample(example);
    }

    public List<TRudyMchChannel> getMchChannelsFromDB(String mchId) {
        TRudyMchChannelExample example = new TRudyMchChannelExample();
        example.createCriteria().andIdEqualTo(mchId);
        return this.mchChannelMapper.selectByExample(example);
    }

}
