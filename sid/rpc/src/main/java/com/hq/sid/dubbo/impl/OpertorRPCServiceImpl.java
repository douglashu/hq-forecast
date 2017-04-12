package com.hq.sid.dubbo.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.flood.service.entity.request.BatchOperRoleReq;
import com.hq.flood.service.entity.request.OperResourcesReq;
import com.hq.louis.model.req.SMSReq;
import com.hq.louis.model.resp.SMSResp;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.SystemUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import com.hq.scrati.framework.FrameworkInvoker;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dao.generate.TSidCoreMapper;
import com.hq.sid.dao.generate.TSidOperMapper;
import com.hq.sid.dubbo.OpertorRPCService;
import com.hq.sid.entity.generate.TSidCore;
import com.hq.sid.entity.generate.TSidOper;
import com.hq.sid.entity.generate.TSidOperExample;
import com.hq.sid.service.MercService;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.constant.SidConstants;
import com.hq.sid.service.entity.request.*;
import com.hq.sid.service.entity.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Zale on 2016/12/21.
 */
@Service(version = "1.0", interfaceClass = OpertorRPCService.class)
public class OpertorRPCServiceImpl implements OpertorRPCService {
    private Logger logger = Logger.getLogger();
    @Autowired
    private OpertorService opertorService;
    @Autowired
    private TSidOperMapper tSidOperMapper;
    @Autowired
    private MercService mercService;
    @Autowired
    private TSidCoreMapper tSidCoreMapper;
    @Autowired
    private FrameworkInvoker frameworkInvoker;
    @Autowired
    @Qualifier("JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Override
    public MercLoginResp mercLogin(HqRequest request) {
        TSidOper oper = null;
        try {
            LoginReq req = JSON.parse(request.getBizContent(), LoginReq.class);
            if ((oper = opertorService.verifyPwd(req.getUsername(), req.getPassword())) != null) {
                oper.setLoginTime(new Date());
                oper.setFailCnt(0);
                MercLoginResp resp = new MercLoginResp();
                resp.setUserId(String.valueOf(oper.getId()));
                resp.setUserName(oper.getOperAlias());
                resp.setAdmin(oper.getIsAdmin());
                resp.setMobile(oper.getMobilePhone());
                resp.setNeedChangePwd(oper.getNeedChangePwd());
                resp.setBelongCoreId(String.valueOf(oper.getBeloneCoreId()));
                List<Map<String, Object>> mchList = new ArrayList<>();
                resp.setMchs(mchList);
                MercResp merc = mercService.getMercByCoreId(oper.getBeloneCoreId());
                if (merc != null) {
                    mchList.add(buildMch(merc));
                } else {
                    MercSearchReq searchReq = new MercSearchReq();
                    searchReq.setPcoreId(oper.getBeloneCoreId());
                    PageInfo<MercResp> mercs = mercService.getMercList(searchReq, 1000, 0);
                    if (mercs.getList() != null) {
                        mercs.getList().forEach(m -> {
                            Map<String, Object> mch = new HashMap<>();
                            mch.put(MercLoginResp.MCHID_KEY, m.getMercId());
                            mch.put(MercLoginResp.COREID_KEY, m.getCoreId());
                            mch.put(MercLoginResp.MCHNAME_KEY, m.getMercName());
                            mch.put(MercLoginResp.MCHSHORTNAME_KEY, m.getMercShotName());
                            mch.put(MercLoginResp.STORENAME_KEY, m.getShopName());
                            mchList.add(mch);
                        });
                    }
                }
                HqRequest getRoleRequest = new HqRequest();
                getRoleRequest.setUserId(String.valueOf(oper.getId()));
                OperResourcesReq operResourcesReq = new OperResourcesReq();
                operResourcesReq.setSystemId(request.getAppId());
                operResourcesReq.setOperId(oper.getId());
                getRoleRequest.setBizContent(com.alibaba.fastjson.JSON.toJSONString(operResourcesReq));
                Map<String, Object> params = new HashMap<>();
                params.put("request", getRoleRequest);
                RespEntity<List<HashMap>> respEntity = (RespEntity) frameworkInvoker
                        .invoke("flood.oper.resources", "1.0", params);
                if (!"0000".equals(respEntity.getKey())) {
                    throw new BusinessException("登录出错" + respEntity.getMsg());
                }
                respEntity.getExt().forEach((r) -> {
                    RoleResp role = new RoleResp();
                    role.setRoleId((String) r.get("roleId"));
                    role.setRoleName((String) r.get("roleName"));
                    if (resp.getRole() == null) {
                        resp.setRole(new ArrayList<>());
                    }
                    resp.getRole().add(role);
                });
                return resp;
            } else {
                TSidOperExample example = new TSidOperExample();
                example.createCriteria().andOperAliasEqualTo(req.getUsername());
                example.or().andMobilePhoneEqualTo(req.getUsername());
                List<TSidOper> dboper = tSidOperMapper.selectByExample(example);
                if (dboper != null && dboper.size() > 0) {
                    oper = dboper.get(0);
                    if (oper.getFailCnt() == null) {
                        oper.setFailCnt(0);
                    }
                    oper.setFailCnt(oper.getFailCnt() + 1);
                }
                throw new CommonException(CommonErrCode.BUSINESS, "用户名或密码不正确");
            }
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.error(th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        } finally {
            if (oper != null && SidConstants.OperStatus.OK.getValue().equals(oper.getStatus())) {
                tSidOperMapper.updateByPrimaryKeySelective(oper);
            }
        }
    }

    @Override
    public String changeSelfPwd(HqRequest request) {
        try {
            ChangePwdReq req = JSON.parse(request.getBizContent(), ChangePwdReq.class);
            TSidOperExample operExample = new TSidOperExample();
            operExample.createCriteria().andIdEqualTo(Integer.parseInt(request.getUserId()))
                    .andOperPwdEqualTo(MD5Util.MD5Encode(req.getOldPwd(), "utf-8"));
            List<TSidOper> dbSidOpers = tSidOperMapper.selectByExample(operExample);
            if (updatePwd(req.getNewPwd(), dbSidOpers)) {
                return "更改成功";
            }
            throw new BusinessException("密码错误");
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("更改密码失败");
        }

    }

    @Override
    public String changePwdByCode(HqRequest request) {
        try {
            ForgetPwdReq req = JSON.parse(request.getBizContent(), ForgetPwdReq.class);
            String key = SidConstants.REDIS_KEY_PREFIX_FORGET_PWD+req.getLoginId();
            String cacheCode = redisCacheDao.read(key,String.class);

            if(StringUtils.isBlank(cacheCode)||StringUtils.isBlank(req.getCode())||!cacheCode.toUpperCase().equals(req.getCode().toUpperCase())){
                throw new BusinessException("短信验证码错误");
            }
            TSidOperExample operExample = new TSidOperExample();
            operExample.createCriteria().andLoginIdEqualTo(req.getLoginId());
            List<TSidOper> dbSidOpers = tSidOperMapper.selectByExample(operExample);
            if (updatePwd(req.getNewPwd(), dbSidOpers)) {
                redisCacheDao.delete(key,String.class);
                return "更改成功";
            }
            throw new BusinessException("密码错误");
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("更改密码失败");
        }

    }

    private boolean updatePwd(String newPwd, List<TSidOper> dbSidOpers) {
        if (dbSidOpers != null && dbSidOpers.size() > 0) {
            TSidOper changeOper = dbSidOpers.get(0);
            changeOper.setOperPwd(MD5Util.MD5Encode(newPwd, "utf-8"));
            changeOper.setNeedChangePwd(false);
            tSidOperMapper.updateByPrimaryKeySelective(changeOper);
            return true;
        }
        return false;
    }

    @Override
    public String sendForgetPwdCode(HqRequest hqRequest){
        try {
            ForgetPwdReq req = JSON.parse(hqRequest.getBizContent(), ForgetPwdReq.class);
            TSidOperExample operExample = new TSidOperExample();
            if(StringUtils.isBlank(req.getLoginId())){
                throw new BusinessException("用户名为空");
            }

            String canResend = redisCacheDao.read(SidConstants.REDIS_KEY_PREFIX_FORGET_PWD_SEND + req.getLoginId(), String.class);
            if(StringUtils.isNotBlank(canResend)){
                throw new BusinessException("禁止1分钟重复发送验证码");
            }
            //1分钟内不重复发短信
            redisCacheDao.save(SidConstants.REDIS_KEY_PREFIX_FORGET_PWD_SEND+req.getLoginId(), "1",60L);
            operExample.createCriteria().andLoginIdEqualTo(req.getLoginId()).andStatusNotEqualTo(SidConstants.OperStatus.DISABLE.getValue());
            List<TSidOper> sidOpers = tSidOperMapper.selectByExample(operExample);
            if(sidOpers==null||sidOpers.size()==0){
                throw new BusinessException("无效用户");
            }
            //调用短信接口发送code
            String code = redisCacheDao.read(SidConstants.REDIS_KEY_PREFIX_FORGET_PWD+ req.getLoginId(), String.class);
            if(StringUtils.isBlank(code)) {
                code = SystemUtil.genNumRandomCode(6);
                //验证码5分钟有效
                redisCacheDao.save(SidConstants.REDIS_KEY_PREFIX_FORGET_PWD+sidOpers.get(0).getLoginId(), code,300L);
            }

            String mobilePhone = sidOpers.get(0).getMobilePhone();
            HqRequest getRoleRequest = new HqRequest();
            getRoleRequest.setAppId(hqRequest.getAppId());
            SMSReq smsReq = new SMSReq();
            smsReq.setAddress(mobilePhone);
            smsReq.setTemplateId("SMS_47255009");
            Map<String, String> smsParams = new HashMap<>();
            smsParams.put("code", code);
            smsReq.setParams(smsParams);
            getRoleRequest.setBizContent(com.alibaba.fastjson.JSON.toJSONString(smsReq));
            Map<String, Object> params = new HashMap<>();
            params.put("request", getRoleRequest);
            RespEntity<SMSResp> respEntity = (RespEntity) frameworkInvoker
                    .invoke("louis.aliyun.sms", "1.0", params);
            if (!"0000".equals(respEntity.getKey())) {
                throw new BusinessException("发送短信" + respEntity.getMsg());
            }

            return mobilePhone;
//            return code;
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("发送验证码失败");
        }
    }


    @Override
    public SystemLoginResp systemLogin(HqRequest request) {
        TSidOper oper = null;
        try {
            LoginReq req = JSON.parse(request.getBizContent(), LoginReq.class);

            if ((oper = opertorService.verifyPwd(req.getUsername(), req.getPassword())) != null) {
                if (!SidConstants.OperStatus.OK.getValue().equals(oper.getStatus())) {
                    throw new BusinessException("操作员己禁用");
                }
                TSidCore core = tSidCoreMapper.selectByPrimaryKey(oper.getBeloneCoreId());
                if (!SidConstants.CoreType.SYSTEM.getValue().equals(core.getType())) {
                    throw new BusinessException("无效操作员");
                }

                oper.setLoginTime(new Date());
                oper.setFailCnt(0);
                SystemLoginResp resp = new SystemLoginResp();
                BeanUtils.copyProperties(oper, resp);
                return resp;
            } else {
                TSidOperExample example = new TSidOperExample();
                example.createCriteria().andLoginIdEqualTo(req.getUsername());
                List<TSidOper> dboper = tSidOperMapper.selectByExample(example);
                if (dboper != null && dboper.size() > 0) {
                    oper = dboper.get(0);
                    if (oper.getFailCnt() == null) {
                        oper.setFailCnt(0);
                    }
                    oper.setFailCnt(oper.getFailCnt() + 1);
                }

                throw new BusinessException("用户名或密码错误");
            }
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("登录失败");
        } finally {
            if (oper != null) {
                tSidOperMapper.updateByPrimaryKeySelective(oper);
            }
        }
    }

    @Override
    public CreateOperResp createSuperAdmin(HqRequest request) {

        try {
            CreateOperReq req = JSON.parse(request.getBizContent(), CreateOperReq.class);
            req.setCrtOperId(Integer.parseInt(request.getUserId()));
            req.setCrtTime(new Date());
            req.setAppId(request.getAppId());
            return opertorService.createSupperAdmin(req);
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("创建失败");
        }
    }

    @Override
    public CreateOperResp createAppAdmin(HqRequest request) {

        try {
            CreateOperReq req = JSON.parse(request.getBizContent(), CreateOperReq.class);
            req.setCrtOperId(Integer.parseInt(request.getUserId()));
            req.setCrtTime(new Date());
            req.setAppId(request.getAppId());
            return opertorService.createAppOper(req, Integer.parseInt(request.getUserId()));
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("创建失败");
        }
    }


    @Override
    public String updateOper(HqRequest hqRequest) {
        OperUpdateReq req = JSONObject.parseObject(
                hqRequest.getBizContent(), OperUpdateReq.class);
        this.opertorService.updateOper(hqRequest, req);
        return  "操作成功";
    }


    private Map<String, Object> buildMch(MercResp merc) {
        Map<String, Object> mch = new HashMap<>();
        mch.put(MercLoginResp.MCHID_KEY, merc.getMercId());
        mch.put(MercLoginResp.COREID_KEY, merc.getCoreId());
        mch.put(MercLoginResp.MCHNAME_KEY, merc.getMercName());
        mch.put(MercLoginResp.MCHSHORTNAME_KEY, merc.getMercShotName());
        mch.put(MercLoginResp.STORENAME_KEY, merc.getShopName());
        return mch;
    }

    @Override
    public OperResp getOperInfo(HqRequest request) {
        try {
            OperSearchReq req = JSON.parse(request.getBizContent(), OperSearchReq.class);
            TSidOper oper = opertorService.getOper(req);
            if (oper == null) {
                throw new BusinessException("无此操作员");
            }
            OperResp resp = new OperResp();
            BeanUtils.copyProperties(oper, resp);
            return resp;
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("查询失败");
        }
    }

    @Override
    public OperResp getSuperOperInfo(HqRequest request) {
        OperResp resp = this.getOperInfo(request);
        TSidCore tsidCore = tSidCoreMapper.selectByPrimaryKey(resp.getBeloneCoreId());
        if (!SidConstants.CoreType.HEAD_OFFICE.getValue().equals(tsidCore.getType())) {
            throw new BusinessException("该帐号不是总管帐号");
        }
        return resp;
    }

    @Override
    public boolean checkOperExist(HqRequest request) {
        try {
            CheckOperExistReq req = JSON.parse(request.getBizContent(), CheckOperExistReq.class);
            if (req == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            if(StringUtils.isBlank(req.getLoginId())
                    && StringUtils.isBlank(req.getOperAlias())){
                throw new CommonException(CommonErrCode.ARGS_INVALID, "员工登录账号不能为空");
            }
            TSidOperExample example = new TSidOperExample();
            TSidOperExample.Criteria  criteria = example.createCriteria();
            boolean checkLoginId = false;
            if(StringUtils.isNotBlank(req.getLoginId())){
                criteria.andLoginIdEqualTo(req.getLoginId());
                checkLoginId = true;
            }
            if(StringUtils.isNotBlank(req.getOperAlias())){
                if(checkLoginId){
                    criteria = example.or();
                }
                criteria.andOperAliasEqualTo(req.getOperAlias());
            }

            List<TSidOper> sidOper = tSidOperMapper.selectByExample(example);
            if(sidOper != null && sidOper.size() > 0) {
                for(TSidOper oper: sidOper) {
                    if(!"00".equals(oper.getStatus())) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Throwable th) {
            logger.error(th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    @Override
    public List<OperResp> queryAllOperByMchId(HqRequest request) {
        try {
            OpersMchReq req = JSON.parse(request.getBizContent(), OpersMchReq.class);
            if( null == req || null == req.getMchId()){
                throw new ParamValueException("参数传入错误");
            }
            List<OperResp> result = opertorService.queryByMchId(req.getMchId());
            return result ;
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("查询失败");
        }
    }

    @Override
    public List<OperResp> queryOperByMchId(HqRequest request) {
        try {
            OperMchReq req = JSON.parse(request.getBizContent(), OperMchReq.class);
            if (null == req || null == req.getCoreId()) {
                throw new ParamValueException("[coreId]参数传入错误");
            }

            if (StringUtils.isBlank( req.getRoleId() )) {
                throw new ParamValueException("参数【roleId】不能为空");
            }
            if ( null == req.getOperId()) {
                throw new ParamValueException("参数【operId】不能为空");
            }
            List<OperResp> result = opertorService.queryByOperId(req.getOperId(),req.getRoleId(),req.getPcoreId(),req.getCoreId());
            if(result!=null&&result.size()>0) {
                List<Integer> operIds = result.stream().map(OperResp::getId).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(operIds)) {
                    HqRequest roleRequest = new HqRequest();
                    request.setUserId(request.getUserId());
                    BatchOperRoleReq roleInitReq = new BatchOperRoleReq();
                    roleInitReq.setSystemId(request.getAppId());
                    roleInitReq.setOperIds(operIds);
                    request.setBizContent(com.alibaba.fastjson.JSON.toJSONString(roleInitReq));
                    Map<String, Object> params = new HashMap<>();
                    params.put("request", request);
                    RespEntity<List<HashMap>> respEntity = (RespEntity) frameworkInvoker
                            .invoke("flood.role.batch", "1.0", params);
                    if (!"0000".equals(respEntity.getKey())) {
                        throw new BusinessException("初始化角色异常" + respEntity.getMsg());
                    }
                    result.forEach(resp -> {
                        List<RoleResp> roleResps = new ArrayList<>();
                        respEntity.getExt().stream().filter(e -> e.get("operId").equals(resp.getId())).forEach(r -> {
                            RoleResp roleResp = new RoleResp();
                            roleResp.setRoleName((String) r.get("roleName"));
                            roleResp.setRoleId((String) r.get("roleId"));
                            roleResps.add(roleResp);
                        });
                        resp.setRole(roleResps);
                        resp.setOperPwd(null);
                    });
                }
            }
            return result;
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("查询失败");
        }
    }
}
