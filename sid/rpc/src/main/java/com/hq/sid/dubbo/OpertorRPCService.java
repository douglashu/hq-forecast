package com.hq.sid.dubbo;

import com.alibaba.dubbo.common.json.ParseException;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.*;

import java.util.List;

/**
 * Created by Zale on 2016/12/18.
 */
public interface OpertorRPCService {

    /**
     * 商户登录
     *
     * @param request
     * @return
     */
    MercLoginResp mercLogin(HqRequest request);

    /**
     * 操作员修改密码
     *
     * @param request
     * @return
     */
    String changeSelfPwd(HqRequest request);

    /**
     * 操作员忘记密码用户短信验证码更新密码
     *
     * @param request
     * @return
     */
    String changePwdByCode(HqRequest request);

    /**
     * 操作员忘记密码发送短信验证码
     *
     * @param hqRequest
     * @return
     */
    String sendForgetPwdCode(HqRequest hqRequest);
    /**
     * 系统管理员登录
     *
     * @param request
     * @return
     */
    SystemLoginResp systemLogin(HqRequest request);
    /**
     * 创建老板帐号
     *
     * @param request
     * @return
     */
    CreateOperResp createSuperAdmin(HqRequest request) throws ParseException;

    /**
     * 创建APP操作员
     *
     * @param request
     * @return
     */
    CreateOperResp createAppAdmin(HqRequest request);

    /**
     * 商户更新操作员配置信息
     *
     * @param hqRequest
     * @return
     */
    String updateOper(HqRequest hqRequest);
    /**
     * 获取操作员信息
     *
     * @param request
     * @return
     */
    OperResp getOperInfo(HqRequest request);

    /**
     * 查询商户操作员列表(下级)
     *
     * @param request
     * @return
     */
    List<OperResp> queryOperByMchId(HqRequest request);

    OperResp getSuperOperInfo(HqRequest request);

    boolean checkOperExist(HqRequest request);


    /**
     * 查询商户操作员列表(All)
     *
     * @param request
     * @return
     */
    List<OperResp> queryAllOperByMchId(HqRequest request);

}
