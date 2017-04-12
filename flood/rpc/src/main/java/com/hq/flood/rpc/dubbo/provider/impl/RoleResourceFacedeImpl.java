package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.flood.rpc.dubbo.provider.RoleResourceFacede;
import com.hq.flood.service.RoleResourceService;
import com.hq.flood.service.entity.request.OperResourcesReq;
import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:43
 */
@Service(version = "1.0",interfaceName = "com.hq.flood.rpc.dubbo.provider.RoleResourceFacede")
public class RoleResourceFacedeImpl implements RoleResourceFacede{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private RoleResourceService roleResourceService;

    @Override
    public List<ResourceRsp> queryByOperId(HqRequest hqRequest) {
        OperResourcesReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<OperResourcesReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getSystemId())) {
            throw new ParamValueException("参数【systemId】不能为空");
        }
        if ( null == req.getOperId()) {
            throw new ParamValueException("参数【operId】不能为空");
        }
        try {
            List<ResourceRsp> rsp = roleResourceService.queryByOperId(req.getSystemId(), req.getOperId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
