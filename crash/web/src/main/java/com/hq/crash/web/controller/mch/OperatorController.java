package com.hq.crash.web.controller.mch;

import com.hq.crash.model.auth.Role;
import com.hq.crash.model.operator.Operator;
import com.hq.crash.model.operator.OperatorCfg;
import com.hq.crash.service.operator.OperatorService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.util.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhaoyang on 11/02/2017.
 */
@RestController
public class OperatorController extends BaseController {

    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity getRoles(HttpServletRequest request) {
        List<Role> roles = this.operatorService
                .getRoles(getUserSession(request));
        return getJsonResp(roles, HttpStatus.OK);
    }

    @RequestMapping(value = "/operators", method = RequestMethod.GET)
    public ResponseEntity getOperators(HttpServletRequest request) {
        Object resp = this.operatorService.getOperators(getUserSession(request));
        return getJsonResp(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/operators", method = RequestMethod.POST)
    public ResponseEntity createOperator(
            HttpServletRequest request, @RequestBody Operator operator) {
        Integer operatorId = this.operatorService
                .createOperator(operator, getUserSession(request));
        return getJsonResp(MapBuilder.create("operatorId", operatorId).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/operators", method = RequestMethod.PUT)
    public ResponseEntity uptOperator(
            HttpServletRequest request, @RequestBody Operator operator) {
        this.operatorService
                .uptOperator(operator, getUserSession(request));
        return getMessageResp("更新成功");
    }

    @RequestMapping(value = "/operator/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOperator(
            HttpServletRequest request, @PathVariable Integer id) {
        this.operatorService.deleteOperator(id, getUserSession(request));
        return getMessageResp("删除成功");
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseEntity<OperatorCfg> getConfig(HttpServletRequest request) {
        OperatorCfg cfg = this.operatorService.getConfig(getUserSession(request));
        return getJsonResp(cfg, HttpStatus.OK);
    }

    @RequestMapping(value = "/config", method = RequestMethod.PUT)
    public ResponseEntity<RespEntity> updateConfig(
            HttpServletRequest request, @RequestBody OperatorCfg cfg) {
        this.operatorService.updateConfig(cfg, getUserSession(request));
        return getMessageResp("更新成功");
    }
}
