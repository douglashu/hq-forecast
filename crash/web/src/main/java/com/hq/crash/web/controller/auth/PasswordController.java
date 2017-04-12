package com.hq.crash.web.controller.auth;

import com.hq.crash.model.auth.req.UpdatePwdReq;
import com.hq.crash.service.auth.PasswordService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 11/02/2017.
 */
@RestController
public class PasswordController extends BaseController {

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ResponseEntity<RespEntity<String>> updatePwd(
            HttpServletRequest request, @RequestBody UpdatePwdReq updatePwdReq) {
        this.passwordService.updateByPassword(updatePwdReq, getUserSession(request));
        RespEntity<String> respEntity = new RespEntity<>();
        respEntity.setKey(CommonErrCode.NONE.getCode());
        respEntity.setMsg("修改密码成功");
        return getJsonResp(respEntity, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/pwd", method = RequestMethod.PUT)
    public ResponseEntity<RespEntity> resetPwd(@RequestBody UpdatePwdReq updatePwdReq) {
        this.passwordService.updateByPasscode(updatePwdReq);
        return getMessageResp("重置密码成功");
    }

    @RequestMapping(value = "/passcode", method = RequestMethod.GET)
    public ResponseEntity<RespEntity> sendPasscode(@RequestParam String mobile) {
        this.passwordService.sendPasscodeForResetPwd(mobile);
        return getMessageResp("验证码已发送");
    }

}
