package com.hq.sid.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dao.generate.TSidOperConfigMapper;
import com.hq.sid.dubbo.OperConfigRPCService;
import com.hq.sid.entity.generate.TSidOperConfig;
import com.hq.sid.entity.generate.TSidOperConfigExample;
import com.hq.sid.service.OperConfigService;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.entity.request.OperConfigIdReq;
import com.hq.sid.service.entity.request.OperConfigReq;
import com.hq.sid.service.entity.response.OperConfigRsp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hq.sid.service.entity.response.OperResp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.sid.dubbo.impl
 * @创建人：yyx
 * @创建时间：17/2/15 下午11:51
 */
@Service(version = "1.0",interfaceName = "com.hq.sid.dubbo.OperConfigRPCService")
public class OperConfigRPCServiceImpl implements OperConfigRPCService{
    private static Logger logger = Logger.getLogger();

    @Autowired
    private OperConfigService operConfigService;

    @Autowired
    private TSidOperConfigMapper tSidOperConfigMapper;

    @Autowired
    private OpertorService opertorService;

    @Override
    public boolean config(HqRequest hqRequest) {
        OperConfigReq req = JSON.parseObject(hqRequest.getBizContent(), OperConfigReq.class);
        return operConfigService.config(req, hqRequest.getMchId());
    }

    @Override
    public OperConfigRsp obtainConfig(HqRequest hqRequest) {
        OperConfigIdReq req = JSON.parseObject(hqRequest.getBizContent(), OperConfigIdReq.class);
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        return operConfigService.obtainConfig(req.getOperId());
    }

    @Override
    public List<OperConfigRsp> getOperConfigsByMchId(HqRequest hqRequest){
        String mchId = JSONObject.parseObject(hqRequest.getBizContent(), String.class);
        if (StringUtils.isBlank(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        List<OperResp> tSidOpers = opertorService.queryByMchId(mchId);
        if(tSidOpers != null && tSidOpers.size() > 0) {
            List<Integer> operIds = tSidOpers.stream()
                    .map(OperResp::getId).collect(Collectors.toList());
            TSidOperConfigExample example = new TSidOperConfigExample();
            example.createCriteria().andOperIdIn(operIds);
            List<TSidOperConfig> tSidOperConfigs = tSidOperConfigMapper.selectByExample(example);
            List<OperConfigRsp> rst = new ArrayList<>();
            tSidOperConfigs.forEach(t -> {
                OperConfigRsp rsp = new OperConfigRsp();
                BeanUtils.copyProperties(t, rsp, new String[] { "refundPassword" });
                rst.add(rsp);
            });
            return rst;
        }
        return new ArrayList<>();
    }
}
