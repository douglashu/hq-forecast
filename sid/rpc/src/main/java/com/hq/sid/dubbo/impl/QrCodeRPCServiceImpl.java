package com.hq.sid.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dubbo.QrCodeRPCService;
import com.hq.sid.service.QrCodeService;
import com.hq.sid.service.entity.request.QrCodeIdReq;
import com.hq.sid.service.entity.request.QrCodePageReq;
import com.hq.sid.service.entity.request.QrCodeReq;
import com.hq.sid.service.entity.request.QrCodeStateReq;
import com.hq.sid.service.entity.response.QrCodeRsp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.sid.dubbo.impl
 * @创建人：yyx
 * @创建时间：17/1/26 下午5:08
 */
@Service(interfaceName = "com.hq.sid.dubbo.QrCodeRPCService", version = "1.0")
public class QrCodeRPCServiceImpl implements QrCodeRPCService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private QrCodeService codeService;

    @Override
    public boolean insert(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        QrCodeReq req = JSONObject.parseObject(
                hqRequest.getBizContent(), new TypeReference<QrCodeReq>() {
                });
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        try {
            Boolean flag = codeService.insertQrCodeTransactional(req);
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
        QrCodeIdReq req = JSONObject.parseObject(
                hqRequest.getBizContent(), new TypeReference<QrCodeIdReq>() {});
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        try {
            Boolean flag = codeService.deleteQrCodeTransactional(req.getCodeId());
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
        QrCodeReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<QrCodeReq>() {});
        try {
            Boolean flag = codeService.updateQrCodeTransactional(req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public QrCodeRsp queryById(HqRequest hqRequest) {
        QrCodeIdReq req = JSONObject.parseObject(
                hqRequest.getBizContent(), new TypeReference<QrCodeIdReq>() { });
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        QrCodeRsp codeRsp;
        try {
            codeRsp = this.codeService.queryById(req.getCodeId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
        if (codeRsp == null) throw new CommonException(CommonErrCode.NO_DATA_FOUND, "二维码信息不存在");
        return codeRsp;
    }

    @Override
    public PageInfo<QrCodeRsp> queryByCondition(HqRequest hqRequest) {
        QrCodePageReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<QrCodePageReq>() {
        });
        if (null == req || null == req.getPage()) {
            req.setPage(1);
        }
        if (null == req || null == req.getPageSize()) {
            req.setPageSize(20);
        }
        try {
            PageInfo<QrCodeRsp> page = codeService
                    .queryByCondition(req.getCoreId(),req.getName(), req.getType(), req.getPage(),
                            req.getPageSize());
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean updateQrCodeState(HqRequest hqRequest) {
        QrCodeStateReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<QrCodeStateReq>() {});
        try {
            Boolean flag = codeService.updateQrCodeState(req.getCodeId(),req.getState());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
