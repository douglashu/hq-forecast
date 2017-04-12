package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.diego.gateway.dubbo.MchChannelDubboService;
import com.hq.diego.gateway.service.core.route.MchChannelService;
import com.hq.diego.model.req.channel.MchChannelReq;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by zhaoyang on 22/03/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.MchChannelDubboService", version = "1.0")
public class MchChannelDubboServiceImpl extends DubboBaseService implements MchChannelDubboService {

    @Autowired
    private MchChannelService mchChannelService;

    @Override
    public RespEntity updateCache(HqRequest request) {
        try {
            this.mchChannelService.updateCache(request.getMchId());
            return getSuccessResp("刷新成功", null);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity getMchChannels(HqRequest request) {
        try {
            List<TRudyMchChannel> mchChannels = this
                    .mchChannelService.getMchChannelsFromDB(request.getMchId());
            return getSuccessResp(mchChannels);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity saveMchChannel(HqRequest request) {
        try {
            MchChannelReq mchChannelReq = parseRequest(request, MchChannelReq.class);
            if (mchChannelReq != null) {
                mchChannelReq.setId(request.getMchId());
            }
            TRudyMchChannel mchChannel = this.mchChannelService.saveMchChannel(mchChannelReq, true);
            return getSuccessResp(mchChannel);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity updateMchChannel(HqRequest request) {
        try {
            MchChannelReq mchChannelReq = parseRequest(request, MchChannelReq.class);
            if (mchChannelReq != null) {
                mchChannelReq.setId(request.getMchId());
            }
            TRudyMchChannel mchChannel = this.mchChannelService.saveMchChannel(mchChannelReq, false);
            return getSuccessResp(mchChannel);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity deleteMchChannel(HqRequest request) {
        try {
            MchChannelReq mchChannelReq = parseRequest(request, MchChannelReq.class);
            if (mchChannelReq != null) {
                mchChannelReq.setId(request.getMchId());
            }
            this.mchChannelService.deleteMchChannel(
                    mchChannelReq.getId(), mchChannelReq.getChannel());
            return getSuccessResp("删除成功", null);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
