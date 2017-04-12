package com.hq.crash.service.operator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hq.crash.model.auth.Role;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.operator.Operator;
import com.hq.crash.model.operator.OperatorCfg;
import com.hq.crash.service.auth.LoginService;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by zhaoyang on 13/02/2017.
 */
@Service
public class OperatorService extends CrashCommonService {

    private static final Pattern MOBILE_PATTERN_REG = Pattern.compile("^1\\d{10}$");

    @Autowired
    private LoginService loginService;

    public Integer createOperator(Operator operator, UserSession userSession) {
        if (operator == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (StringUtils.isEmpty(operator.getName())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "员工姓名不能为空");
        }
        if (StringUtils.isEmpty(operator.getMobile())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号不能为空");
        }
        if (!MOBILE_PATTERN_REG.matcher(operator.getMobile()).matches()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号格式有误");
        }
        if (operator.getRoleType() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "员工角色不能为空");
        }
        if (StringUtils.isEmpty(operator.getRefund())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "退款权限不能为空");
        }
        String refundAuth=operator.getRefund();
        // Check exists
        HqRequest checkOperExistsReq = getHqRequest(userSession);
        String loginId = operator.getMobile();
        checkOperExistsReq.setBizContent("{ loginId:\""  + loginId + "\" }");
        Boolean exists = invoke("sid.oper.checkexist", "1.0", checkOperExistsReq, Boolean.class);
        if (exists == null || exists) {
            throw new CommonException(CommonErrCode.BUSINESS, "该手机号已被注册");
        }
        // Create
        Map<String, Object> reqParams = MapBuilder
                .create("beloneCoreId", userSession.activeMchInfo().getCoreId())
                .add("operName", operator.getName())
                .add("operAlias", operator.getName())
                .add("mobilePhone", operator.getMobile())
                .add("roleType", operator.getRoleType())
                .add("refundAuth", refundAuth).get();
        HqRequest createOperReq = getHqRequest(userSession);
        createOperReq.setBizContent(JSON.toJSONString(reqParams));
        JSONObject jsonObject = invoke("sid.oper.createapp", "1.0", createOperReq, JSONObject.class);
        if (jsonObject.containsKey("operId")) {
            return jsonObject.getInteger("operId");
        }
        throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "操作员创建失败");
    }

    public Object getOperators(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        Map<String, Object> reqParams = MapBuilder
                .create("coreId", userSession.activeMchInfo().getCoreId())
                .add("pcoreId", userSession.getBelongCoreId())
                .add("roleId", userSession.getRole().getRoleId())
                .add("operId", Integer.valueOf(userSession.getUserId()))
                .get();
        request.setBizContent(JSON.toJSONString(reqParams));
        return invoke("sid.oper.queryByMchId", "1.0", request, JSONArray.class);
    }

    public void deleteOperator(Integer operatorId, UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        request.setBizContent("{id:" + operatorId + ", status:\"01\"}");
        invoke("sid.oper.update", "1.0", request, null);
        this.loginService.logout(userSession.getAppId(), String.valueOf(operatorId));
    }

    public void uptOperator(Operator operator, UserSession userSession) {
        if (operator.getId()==null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员id不能为空");
        }
        Map<String, Object> reqParams = MapBuilder
                .create("id", operator.getId()).get();
        if (!StringUtils.isEmpty(operator.getName())) {
            reqParams.put("operName", operator.getName());
            reqParams.put("operAlias", operator.getName());
        }
        if(!StringUtils.isEmpty(operator.getRoleType())){
            reqParams.put("roleType", operator.getRoleType());
        }
        if(!StringUtils.isEmpty(operator.getRoleType())){
            reqParams.put("refundAuth", operator.getRefund());
        }
        HqRequest request = getHqRequest(userSession);

        request.setBizContent(JSON.toJSONString(reqParams));
        invoke("sid.oper.update", "1.0", request, null);
    }

    public List<Role> getRoles(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        request.setBizContent("{roleId:\"" + userSession.getRole().getRoleId() + "\"}");
        JSONArray array = invoke(
                "flood.role.templates", "1.0", request, JSONArray.class);
        List<Role> roles = new ArrayList<>();
        if (array != null && array.size() > 0) {
            for(int i=0; i<array.size(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);
                Role role = new Role();
                role.setRoleType(jsonObj.getInteger("id"));
                role.setRoleName(jsonObj.getString("templateName"));
                role.setIntroduce(jsonObj.getString("describes"));
                roles.add(role);
            }
        }
        return roles;
    }

    public OperatorCfg getConfig(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        request.setBizContent("{operId:"
                + Integer.valueOf(userSession.getUserId()) + "}");
        JSONObject jsonObject = invoke(
                "sid.oper.obtainConfig", "1.0", request, JSONObject.class);
        OperatorCfg cfg = new OperatorCfg();
        cfg.setAcceptTradePush(jsonObject.getBoolean("isPush"));
        return cfg;
    }

    public void updateConfig(OperatorCfg cfg, UserSession userSession) {
        if (cfg == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (cfg.getAcceptTradePush() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "是否接收交易推送未配置");
        }
        Map<String, Object> reqParams = MapBuilder
                .create("operId", Integer.valueOf(userSession.getUserId()))
                .add("isPush", cfg.getAcceptTradePush()).get();
        HqRequest request = getHqRequest(userSession);
        request.setBizContent(JSON.toJSONString(reqParams));
        Boolean result = invoke("sid.oper.config", "1.0", request, Boolean.class);
        if (result == null || !result) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }
}
