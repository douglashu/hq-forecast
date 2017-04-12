package com.hq.sid.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.flood.service.entity.request.OperResourcesReq;
import com.hq.flood.service.entity.request.OperRoleSubReq;
import com.hq.flood.service.entity.request.OperRoleTemplateReq;
import com.hq.flood.service.entity.request.RoleInitReq;
import com.hq.louis.model.req.SMSReq;
import com.hq.louis.model.resp.SMSResp;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.common.enums.SystemEnum;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.SystemUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import com.hq.scrati.framework.FrameworkInvoker;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dao.generate.TSidCoreMapper;
import com.hq.sid.dao.generate.TSidMercMapper;
import com.hq.sid.dao.generate.TSidOperConfigMapper;
import com.hq.sid.dao.generate.TSidOperMapper;
import com.hq.sid.dao.generate.custom.OperMercMapper;
import com.hq.sid.entity.generate.*;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.constant.SidConstants;
import com.hq.sid.service.entity.request.CreateOperReq;
import com.hq.sid.service.entity.request.OperSearchReq;
import com.hq.sid.service.entity.request.OperUpdateReq;
import com.hq.sid.service.entity.request.UptOperReq;
import com.hq.sid.service.entity.response.CreateOperResp;
import com.hq.sid.service.entity.response.OperResp;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Zale on 2016/12/13.
 */
@Service
public class OpertorServiceImpl implements OpertorService {
    private Logger logger = Logger.getLogger();

    @Autowired
    private TSidCoreMapper tSidCoreMapper;

    @Autowired
    private TSidMercMapper tSidMercMapper;

    @Autowired
    private TSidOperMapper tSidOperMapper;

    @Autowired
    private TSidOperConfigMapper tSidOperConfigMapper;

    @Autowired
    private OperMercMapper operMercMapper;

    @Autowired
    private FrameworkInvoker frameworkInvoker;
    private static final Map<String, SidConstants.RefundAuth> lookup = new HashMap<>();

    static {
        for (SidConstants.RefundAuth refundAuth : EnumSet.allOf(SidConstants.RefundAuth.class)) {
            lookup.put(refundAuth.getValue(), refundAuth);
        }
    }
    @Override
    @Transactional
    public CreateOperResp createSupperAdmin(CreateOperReq req) {
        req.setCrtTime(new Date());
        logger.debug("step(1) insert core begin");
        TSidCore tSidCore = new TSidCore();
        tSidCore.setName("超级管理员");
        tSidCore.setType(SidConstants.CoreType.HEAD_OFFICE.getValue());
        tSidCore.setCrtOperId(req.getCrtOperId());
        tSidCore.setCrtTime(req.getCrtTime());
        tSidCoreMapper.insertSelective(tSidCore);
        logger.debug("step(1) insert core end," + tSidCore);
        logger.debug("step(2) insert oper begin");
        req.setBeloneCoreId(tSidCore.getId());
        req.setRefundAuth(SidConstants.RefundAuth.REFUND_ALL.getValue());
        CreateOperResp resp = saveAdminOper(req,true);
        logger.debug("step(2) insert oper end,resp="+resp);
        //初始化角色
        HqRequest request = new HqRequest();
        request.setUserId(String.valueOf(resp.getOperId()));
        RoleInitReq roleInitReq = new RoleInitReq();
        roleInitReq.setCoreId(tSidCore.getId());
        roleInitReq.setIsAdmin(true);
        roleInitReq.setSystemId(SystemEnum.APP.getCode());

        request.setBizContent(JSON.toJSONString(roleInitReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request",request);
        RespEntity respEntity = (RespEntity) frameworkInvoker.invoke("flood.role.init","1.0",params);
        if(!"0000".equals(respEntity.getKey())){
            throw new BusinessException("初始化角色异常" + respEntity.getMsg());
        }
         //绑定管理员角色
        bindRole(req, tSidCore.getId(), resp,1);
        return resp;
    }

    private void bindRole(CreateOperReq req, Integer coreId, CreateOperResp resp,Integer tempId) {
        OperRoleTemplateReq operRoleTemplateReq = new OperRoleTemplateReq();
        operRoleTemplateReq.setOperId(resp.getOperId());
        operRoleTemplateReq.setCoreId(String.valueOf(coreId));
        operRoleTemplateReq.setTemplateId(tempId);
        HqRequest request = new HqRequest();
        request.setUserId(String.valueOf(req.getCrtOperId()));
        request.setBizContent(JSON.toJSONString(operRoleTemplateReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request", request);
        RespEntity respEntity = (RespEntity) frameworkInvoker.invoke("flood.oper.bindRoleTemplate", "1.0", params);
        if(!"0000".equals(respEntity.getKey())) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, respEntity.getMsg());
        }
    }

    private CreateOperResp saveAdminOper(CreateOperReq req,boolean isSuper){
        req.setIsAdmin(true);
        String password = SystemUtil.genRandomCode(6);
        logger.info("super admin password for "+req.getBeloneCoreId()+"is: "+password);
        req.setOperPwd(MD5Util.MD5Encode(password,"utf-8"));
        req.setNeedChangePwd(true);

        TSidOper tSidOper = new TSidOper();
        BeanUtils.copyProperties(req,tSidOper);
        if (!StringUtils.isBlank(req.getDomain())) {
            tSidOper.setLoginId(tSidOper.getMobilePhone() + "@" + req.getDomain());
        } else {
            tSidOper.setLoginId(tSidOper.getMobilePhone());
        }
        // 判断是否是做新增还是更新
        TSidOperExample example = new TSidOperExample();
        TSidOperExample.Criteria criteria = example.createCriteria();
        criteria.andLoginIdEqualTo(tSidOper.getLoginId());
        criteria.andStatusEqualTo(SidConstants.OperStatus.DISABLE.getValue());
        List<TSidOper> operList = tSidOperMapper.selectByExample(example);
        if( CollectionUtils.isEmpty(operList)){
            this.tSidOperMapper.insertSelective(tSidOper);
        }else{
            TSidOper oper = operList.get( 0 );
            tSidOper.setId( oper.getId() );
            BeanUtils.copyProperties(tSidOper,oper,new String[]{"id","status","loginId"});
            oper.setStatus( SidConstants.OperStatus.OK.getValue() );
            tSidOperMapper.updateByPrimaryKeySelective(oper);
        }

        CreateOperResp resp = new CreateOperResp();
        resp.setOperId(tSidOper.getId());
        // 设置默认操作员的信息
        TSidOperConfig config = new TSidOperConfig();
        config.setOperId(tSidOper.getId());
        config.setIsPush(Boolean.TRUE);
        config.setRefundAuth(req.getRefundAuth());
        if( CollectionUtils.isEmpty(operList)) {
            tSidOperConfigMapper.insert(config);
        }else{
            tSidOperConfigMapper.updateByPrimaryKeySelective(config);
        }


        HqRequest getRoleRequest = new HqRequest();
        getRoleRequest.setAppId(req.getAppId());
        SMSReq smsReq = new SMSReq();
        smsReq.setAddress(req.getMobilePhone());
        Map<String, String> smsParams = new HashMap<>();
        if(isSuper) {
            smsReq.setTemplateId("SMS_47350023");
            smsParams.put("name", tSidOper.getOperName());
            smsParams.put("loginId", tSidOper.getLoginId());
            smsParams.put("password", password);


        }else{
            TSidMerc merc = tSidMercMapper.selectByPrimaryKey(tSidOper.getBeloneCoreId());
            smsReq.setTemplateId("SMS_47375137");
            smsParams.put("mchName",merc.getMercName());
            smsParams.put("loginId", tSidOper.getLoginId());
            smsParams.put("password", password);
        }
        smsReq.setParams(smsParams);
        getRoleRequest.setBizContent(com.alibaba.fastjson.JSON.toJSONString(smsReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request", getRoleRequest);
        RespEntity<SMSResp> respEntity = (RespEntity) frameworkInvoker
                .invoke("louis.aliyun.sms", "1.0", params);
        if (!"0000".equals(respEntity.getKey())) {
            throw new BusinessException("发送短信" + respEntity.getMsg());
        }
        return resp;
    }
    

    @Override
    public CreateOperResp createAdmin(CreateOperReq req) {
        CreateOperResp resp =  saveAdminOper(req,false);
        bindRole(req, req.getBeloneCoreId(), resp, req.getRoleType());
        return resp;
    }

    @Override
    @Transactional
    public CreateOperResp createAppOper(CreateOperReq req, Integer operId) {
        if(req.getBeloneCoreId() == null){
            throw new CommonException(CommonErrCode.ARGS_INVALID, "客户id不能为空");
        }
        if(org.springframework.util.StringUtils.isEmpty(req.getRoleType())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "角色类型不能为空");
        }
        HqRequest getRoleRequest = new HqRequest();
        getRoleRequest.setUserId(String.valueOf(operId));
        OperResourcesReq operResourcesReq = new OperResourcesReq();
        operResourcesReq.setSystemId(SystemEnum.APP.getCode());
        operResourcesReq.setOperId(operId);
        getRoleRequest.setBizContent(com.alibaba.fastjson.JSON.toJSONString(operResourcesReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request", getRoleRequest);
        RespEntity<List<HashMap>> respEntity = (RespEntity) frameworkInvoker
                .invoke("flood.oper.resources", "1.0", params);
        if (!"0000".equals(respEntity.getKey())) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, respEntity.getMsg());
        }
        Integer templateId = (Integer) respEntity.getExt().stream().min(Comparator.comparing(o -> ((Integer) o.get("templateId")))).get().get("templateId");
        if (templateId >= req.getRoleType()) {
            throw new CommonException(CommonErrCode.NO_PERMISSION, "您没有权限创建该角色");
        }
        if(com.hq.scrati.common.util.StringUtils.isBlank(req.getRefundAuth()) ||
                !lookup.containsKey(req.getRefundAuth())) {
            req.setRefundAuth(SidConstants.RefundAuth.NO_REFUND.getValue());
        }
        CreateOperResp resp =  saveAdminOper(req,false);
        bindRole(req, req.getBeloneCoreId(), resp, req.getRoleType());
        return resp;
    }

    @Override
    public void changePwd(Integer id,String password) {
        TSidOper dbSidOper = new TSidOper();
        dbSidOper.setId(id);
        dbSidOper.setNeedChangePwd(false);
        dbSidOper.setOperPwd(MD5Util.MD5Encode(password,"utf-8"));
        tSidOperMapper.updateByPrimaryKeySelective(dbSidOper);
    }

    @Override
    @Transactional
    public void updateOper(HqRequest hqRequest, OperUpdateReq req) {
        if (req == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (req.getId() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "员工id不能为空");
        }
        TSidOper dbSidOper = this.tSidOperMapper.selectByPrimaryKey(req.getId());
        if (dbSidOper == null) {
            throw new CommonException(CommonErrCode.NO_DATA_FOUND, "员工信息不存在");
        }
        if(hqRequest.getUserId().equals(String.valueOf(dbSidOper.getId()))){
            req.setRefundAuth(null);
            req.setRoleType(null);
            req.setStatus(null);
        }else{
            TSidMerc merc = tSidMercMapper.selectByPrimaryKey(dbSidOper.getBeloneCoreId());
            if(merc==null) {
                throw new BusinessException("商户不合法");
            }
            List<OperResp> subOpers = this.queryByMchId(merc.getMercId());
            if(!subOpers.stream().anyMatch(o ->o.getId().equals(req.getId()))){
                throw new BusinessException("商户不能操作该员工");
            }
        }
        TSidOper sidOper = new TSidOper();
        BeanUtils.copyProperties(req,sidOper);
        sidOper.setMdfOperId(Integer.valueOf(hqRequest.getUserId()));
        sidOper.setMdfTime(Calendar.getInstance().getTime());
        this.tSidOperMapper.updateByPrimaryKeySelective(sidOper);
        if (req.getRoleType() != null) {
            OperRoleTemplateReq operRoleTemplateReq = new OperRoleTemplateReq();
            operRoleTemplateReq.setOperId(req.getId());
            operRoleTemplateReq.setCoreId(String.valueOf(dbSidOper.getBeloneCoreId()));
            operRoleTemplateReq.setTemplateId(req.getRoleType());
            HqRequest request = new HqRequest();
            request.setUserId(hqRequest.getUserId());
            request.setAppId(hqRequest.getAppId());
            request.setBizContent(com.alibaba.fastjson.JSON.toJSONString(operRoleTemplateReq));
            Map<String, Object> params = new HashMap<>();
            params.put("request", request);
            RespEntity respEntity = (RespEntity) this.frameworkInvoker
                    .invoke("flood.oper.bindRoleTemplate", "1.0", params);
            if(!"0000".equals(respEntity.getKey())){
                throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, respEntity.getMsg());
            }
        }
        if(com.hq.scrati.common.util.StringUtils.isNotBlank(req.getRefundAuth())&&lookup.containsKey(req.getRefundAuth())){
            TSidOperConfig config = new TSidOperConfig();
            config.setOperId(req.getId());
            config.setRefundAuth(req.getRefundAuth());
            tSidOperConfigMapper.updateByPrimaryKeySelective(config);
        }
    }
    @Override
    public TSidOper verifyPwd(String un, String password) {
        if(StringUtils.isBlank(un)){
            throw new CommonException(CommonErrCode.ARGS_INVALID, "用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码不能为空");
        }
        TSidOperExample operExample = new TSidOperExample();
        operExample.createCriteria().andLoginIdEqualTo(un);
        List<TSidOper> dbSidOpers = this.tSidOperMapper.selectByExample(operExample);
        TSidOper sidOper = CollectionUtils.findFirst(dbSidOpers);
        if (sidOper == null || !SidConstants.OperStatus.OK.getValue().equals(sidOper.getStatus())) {
            throw new CommonException(CommonErrCode.NO_DATA_FOUND, "账号不存在");
        }
        if (sidOper.getOperPwd().equals(MD5Util.MD5Encode(password,"utf-8"))) {
            return sidOper;
        }
        return null;
    }

    @Override
    public void uptOper(UptOperReq req) {
        if(req.getOperId() == null){
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员ID不能为空");
        }
        TSidOper sidOper = new TSidOper();
        BeanUtils.copyProperties(req,sidOper);
        tSidOperMapper.updateByPrimaryKeySelective(sidOper);

    }

    @Override
    public TSidOper getOper(OperSearchReq oper) {
        TSidOper dbOper;
        if(oper.getId()!=null){
            dbOper = this.tSidOperMapper.selectByPrimaryKey(oper.getId());
        }else{
            List<TSidOper> opers = getOperList(oper, 1, 0);
            dbOper = CollectionUtils.findFirst(opers);
        }
        return dbOper;
    }

    @Override
    public List<TSidOper> getOperList(OperSearchReq oper,int pageSize,int page) {
        TSidOperExample operExample = new TSidOperExample();
        TSidOperExample.Criteria criteria = operExample.createCriteria();
        if(oper.getAdmin()!=null){
            criteria.andIsAdminEqualTo(oper.getAdmin());
        }
        if(oper.getNeedChangePwd()!=null){
            criteria.andNeedChangePwdEqualTo(oper.getNeedChangePwd());
        }
        if(oper.getBeloneCoreId()!=null){
            criteria.andBeloneCoreIdEqualTo(oper.getBeloneCoreId());
        }
        if(oper.getCrtOperId()!=null){
            criteria.andCrtOperIdEqualTo(oper.getCrtOperId());
        }
        if(oper.getsCrtTime()!=null||oper.geteCrtTime()!=null){
            if(oper.getsCrtTime()!=null){
                criteria.andCrtTimeGreaterThanOrEqualTo(oper.getsCrtTime());
            }
            if(oper.geteCrtTime()!=null){
                criteria.andCrtTimeLessThanOrEqualTo(oper.geteCrtTime());
            }
        }
        if(oper.getMdfOperId()!=null){
            criteria.andMdfOperIdEqualTo(oper.getMdfOperId());
        }
        if(oper.getsMdfTime()!=null||oper.geteMdfTime()!=null){
            if(oper.getsMdfTime()!=null){
                criteria.andMdfTimeGreaterThanOrEqualTo(oper.getsMdfTime());
            }
            if(oper.geteMdfTime()!=null){
                criteria.andMdfTimeLessThanOrEqualTo(oper.geteMdfTime());
            }
        }
        if (oper.getFailCnt() != null) {
            criteria.andFailCntLessThanOrEqualTo(oper.getFailCnt());
        }
        if(oper.getOperAlias()!=null){
            criteria.andOperAliasLike(oper.getOperAlias());
        }
        if (oper.getMobilePhone() != null) {
            criteria.andMobilePhoneLike(oper.getMobilePhone());
        }
        if (oper.getOperName() != null) {
            criteria.andOperNameLike(oper.getOperName());
        }
        if (oper.getStatus() != null) {
            criteria.andStatusEqualTo(oper.getStatus());

        }
        if(oper.getSloginTime()!=null||oper.getEloginTime()!=null){
            if(oper.getSloginTime()!=null){
                criteria.andLoginTimeGreaterThanOrEqualTo(oper.getSloginTime());
            }
            if(oper.geteMdfTime()!=null){
                criteria.andLoginTimeLessThanOrEqualTo(oper.getEloginTime());
            }
        }

        return tSidOperMapper.selectByExampleWithRowbounds(operExample,new RowBounds(page,pageSize));
    }

    @Override
    public List<OperResp> queryByMchId(String mchId) {
        if (StringUtils.isBlank(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        List<TSidOper> opers = this.operMercMapper.selectOperByMchId( mchId );
        List<OperResp> rsps = new ArrayList<>();
        CollectionUtils.copyCollections(opers,rsps,OperResp.class);
        return rsps;
    }

    @Override
    public List<OperResp> queryByOperId(Integer operId, String roleId, Integer pcoreId,Integer coreId) {
        if (operId == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员id不能为空[operId]");
        }
        if (StringUtils.isBlank(roleId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "角色id不能为空[roleId]");
        }
        if (coreId == null ){
            throw new CommonException(CommonErrCode.ARGS_INVALID, "[coreId]不能为空");
        }
        OperRoleSubReq subReq = new OperRoleSubReq();
        subReq.setRoleId(roleId);
        subReq.setPcoreId(pcoreId);
        subReq.setCoreId(coreId);
        HqRequest request = new HqRequest();
        request.setBizContent(JSON.toJSONString(subReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request", request);
        RespEntity<List<HashMap>> respEntity = (RespEntity) frameworkInvoker.invoke("flood.oper.subOpers", "1.0", params);
        if(!"0000".equals(respEntity.getKey())){
            throw new CommonException(CommonErrCode.BUSINESS, "查询角色对应的下级操作员绑定信息异常" + respEntity.getMsg());
        }
        List<Integer> opers = respEntity.getExt().stream().map(o -> (Integer)o.get("operId")).collect(Collectors.toList());
        List<OperResp> rsps = new ArrayList<>();
        if( CollectionUtils.isNotEmpty(opers) ) {
            rsps = operMercMapper.selectOpersConfig(opers);
        }
        return rsps;
    }
}
