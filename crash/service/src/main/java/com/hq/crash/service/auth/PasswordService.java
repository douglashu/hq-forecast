package com.hq.crash.service.auth;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.auth.req.UpdatePwdReq;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by zhaoyang on 13/02/2017.
 */
@Service
public class PasswordService extends CrashCommonService {

    private static final Pattern ALL_NUMBERS_PATTERN = Pattern.compile("\\d*");
    private static final Pattern ALL_LETTERS_PATTERN = Pattern.compile("[a-zA-Z]*");
    private static final Pattern MOBILE_PATTERN_REG = Pattern.compile("^1\\d{10}$");

    public String updateByPassword(UpdatePwdReq updatePwdReq, UserSession userSession) {
        if (updatePwdReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(updatePwdReq.getOldPassword())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "原密码不能为空");
        }
        if (StringUtils.isEmpty(updatePwdReq.getNewPassword())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码不能为空");
        }
        if (updatePwdReq.getNewPassword().length() < 6 ||
                ALL_NUMBERS_PATTERN.matcher(updatePwdReq.getNewPassword()).matches() ||
                ALL_LETTERS_PATTERN.matcher(updatePwdReq.getNewPassword()).matches()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码过于简单,请输入不少于6为字母数字的组合密码");
        }
        if (updatePwdReq.getNewPassword().length() > 16) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码长度不能超过16位");
        }
        if (updatePwdReq.getNewPassword().equalsIgnoreCase(updatePwdReq.getOldPassword())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "新旧密码不能相同");
        }
        HqRequest request = getHqRequest(userSession);
        Map<String, Object> reqParams = MapBuilder
                .create("oldPwd", updatePwdReq.getOldPassword())
                .add("newPwd", updatePwdReq.getNewPassword()).get();
        request.setBizContent(JSON.toJSONString(reqParams));
        return invoke("sid.oper.changeselfpwd", "1.0", request, String.class);
    }

    public void sendPasscodeForResetPwd(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号不能为空");
        }
        if (!MOBILE_PATTERN_REG.matcher(mobile).matches()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号格式有误");
        }
        HqRequest request = new HqRequest();
        request.setBizContent("{loginId:\"" + mobile + "\"}");
        invoke("sid.oper.sendforgetcode", "1.0", request, null);
    }

    public void updateByPasscode(UpdatePwdReq updatePwdReq) {
        if (updatePwdReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (StringUtils.isEmpty(updatePwdReq.getMobile())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号不能为空");
        }
        if (!MOBILE_PATTERN_REG.matcher(updatePwdReq.getMobile()).matches()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "手机号格式有误");
        }
        if (StringUtils.isEmpty(updatePwdReq.getPasscode())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "短信验证码不能为空");
        }
        if (StringUtils.isEmpty(updatePwdReq.getNewPassword())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码不能为空");
        }
        if (updatePwdReq.getNewPassword().length() < 6 ||
                ALL_NUMBERS_PATTERN.matcher(updatePwdReq.getNewPassword()).matches() ||
                ALL_LETTERS_PATTERN.matcher(updatePwdReq.getNewPassword()).matches()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码过于简单,请输入不少于6为字母数字的组合密码");
        }
        if (updatePwdReq.getNewPassword().length() > 16) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "密码长度不能超过16位");
        }
        HqRequest request = new HqRequest();
        Map<String, Object> reqParams = MapBuilder
                .create("loginId", updatePwdReq.getMobile())
                  .add("code", updatePwdReq.getPasscode())
                  .add("newPwd", updatePwdReq.getNewPassword()).get();
        request.setBizContent(JSON.toJSONString(reqParams));
        invoke("sid.oper.forgetpwd", "1.0", request, null);
    }

}
