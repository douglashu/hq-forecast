package com.hq.peaches.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.hq.peaches.rpc.dubbo.provider.MemberFacade;
import com.hq.peaches.service.MemberService;
import com.hq.peaches.service.entity.request.*;
import com.hq.peaches.service.entity.response.MemberRsp;
import com.hq.peaches.util.RpcReturnHelper;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.peaches.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：16/12/5 下午9:46
 */
@Service(version = "1.0", interfaceName = "com.hq.peaches.rpc.dubbo.provider.MemberFacade")
public class MemberFacadeImpl implements MemberFacade {

    @Autowired
    private MemberService memberService;

    private Logger logger = Logger.getLogger();

    @Override
    public boolean insert(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        MemberReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberReq>() {
        });
        if (null == req) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = memberService.addMemberTransactional(hqRequest.getUserId(), req);
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

        MemberIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getMemberId())) {
            throw new ParamValueException("参数【memberId】不能为空");
        }
        try {
            Boolean flag = memberService.delMemberTransactional(hqRequest.getUserId(), req.getMemberId());
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
        MemberReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberReq>() {
        });
        if (req == null || StringUtils.isBlank(req.getId())) {
            throw new ParamValueException("参数【id】不能为空");
        }
        try {
            Boolean flag = memberService.updateMemberTransactional(hqRequest.getUserId(), req.getId(), req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public MemberRsp queryById(HqRequest hqRequest) {

        MemberIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getMemberId())) {
            throw new ParamValueException("参数【memberId】不能为空");
        }
        try {
            MemberRsp rsp = memberService.findMemberById(req.getMemberId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean uniqueMemberInfo(HqRequest hqRequest) {
        MemberUniqueReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberUniqueReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getMchId())) {
            throw new ParamValueException("参数【mchId】不能为空");
        }
        if (StringUtils.isBlank(req.getPhone())) {
            throw new ParamValueException("参数【phone】不能为空");
        }
        try {
            Boolean flag = memberService.uniqueMemberInfo(req.getMchId(), req.getPhone());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean disabled(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }

        MemberIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getMemberId())) {
            throw new ParamValueException("参数【memberId】不能为空");
        }
        try {
            Boolean flag = memberService.disabledMember(hqRequest.getUserId(), req.getMemberId());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean enabled(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }

        MemberIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getMemberId())) {
            throw new ParamValueException("参数【memberId】不能为空");
        }
        try {
            Boolean flag = memberService.enabledMember(hqRequest.getUserId(), req.getMemberId());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PageInfo<MemberRsp> findMemberByCondition(HqRequest hqRequest) {
        MemberPageReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberPageReq>() {
        });
        if (null == req || null == req.getPage()) {
            req.setPage(1);
        }
        if (null == req || null == req.getPageSize()) {
            req.setPageSize(20);
        }
        try {
            PageInfo<MemberRsp> page = memberService
                    .findMemberByCondition(req.getMchId(), req.getName(), req.getPhone(), req.getPage(),
                            req.getPageSize());
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public MemberRsp findMemberByOpenId(HqRequest hqRequest) {
//        if (StringUtils.isBlank(hqRequest.getUserId())) {
//            return RpcReturnHelper.getRespEntity(RpcReturnHelper.RpcResult.REQUEST_PARAM_ERROR);
//        }

        MemberOpenIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<MemberOpenIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传入错误");
        }

        if (StringUtils.isBlank(req.getOpenId())) {
            throw new ParamValueException("参数【openId】不能为空");
        }
        try {
            MemberRsp rsp = memberService.findMemberByOpenId(req.getOpenId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
