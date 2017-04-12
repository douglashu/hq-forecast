package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.hq.flood.rpc.dubbo.provider.PrdFacade;
import com.hq.flood.service.ProdService;
import com.hq.flood.service.entity.request.ProdCodeReq;
import com.hq.flood.service.entity.request.ProdPageReq;
import com.hq.flood.service.entity.request.ProdReq;
import com.hq.flood.service.entity.response.ProdRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:12
 */
@Service(version = "1.0", interfaceName = "com.hq.flood.rpc.dubbo.provider.PrdFacade")
public class PrdFacadeImpl implements PrdFacade{

    @Autowired
    private ProdService prodService;

    private Logger logger = Logger.getLogger();

    @Override
    public boolean insert(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        ProdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ProdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = prodService.saveProductTransactional(hqRequest.getUserId(), req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean delete(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }

        ProdCodeReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ProdCodeReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getPrdCode())) {
            throw new ParamValueException("参数【prdCode】不能为空");
        }
        try {
            Boolean flag = prodService.deleteProductTransactional(hqRequest.getUserId(), req.getPrdCode());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean update(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        ProdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ProdReq>() {
        });
        if (req == null || StringUtils.isBlank(req.getPrdCode())) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = prodService.updateProductTransactional(hqRequest.getUserId(), req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ProdRsp queryById(HqRequest hqRequest) {
        ProdCodeReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ProdCodeReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getPrdCode())) {
            throw new ParamValueException("参数【prdCode】不能为空");
        }
        try {
            ProdRsp rsp = prodService.queryProductById(req.getPrdCode());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PageInfo<ProdRsp> findProdByCondition(HqRequest hqRequest) {
        ProdPageReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ProdPageReq>() {
        });
        if (null == req || null == req.getPage()) {
            req.setPage(1);
        }
        if (null == req || null == req.getPageSize()) {
            req.setPageSize(20);
        }
        try {
            PageInfo<ProdRsp> page = prodService
                    .queryProductList(req.getPrdName(),  req.getPage(),req.getPageSize());
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
