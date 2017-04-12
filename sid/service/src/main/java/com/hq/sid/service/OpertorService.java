package com.hq.sid.service;

import com.hq.scrati.model.HqRequest;
import com.hq.sid.entity.generate.TSidOper;
import com.hq.sid.service.entity.request.CreateOperReq;
import com.hq.sid.service.entity.request.OperSearchReq;
import com.hq.sid.service.entity.request.OperUpdateReq;
import com.hq.sid.service.entity.request.UptOperReq;
import com.hq.sid.service.entity.response.CreateOperResp;
import com.hq.sid.service.entity.response.OperResp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zale on 2016/12/12.
 */
public interface OpertorService {
    /**
     * 创建超级管理员
     *
     * @Author Zale
     * @Date 2016/12/12 下午8:54
     */
    CreateOperResp createSupperAdmin(CreateOperReq req);

    /**
     * 创建专属管理员
     *
     * @Author Zale
     * @Date 2016/12/12 下午8:54
     */
    CreateOperResp createAdmin(CreateOperReq req);

    CreateOperResp createAppOper(CreateOperReq req, Integer operId);

    /**
     * 操作员自己修改操作员密码
     *
     * @Author Zale
     * @Date 2016/12/12 下午8:59
     */
    void changePwd(Integer id, String password);

    /**
     * 商户更新操作员或操作员自更新
     *
     * @Author Zale
     * @Date 2016/12/12 下午8:59
     */
    void updateOper(HqRequest hqRequest, OperUpdateReq req);
    /**
     * 验证操作员用户名密码
     *
     * @Author Zale
     * @Date 2016/12/12 下午8:59
     */
    TSidOper verifyPwd(String un, String password);

    /**
     * 修改操作员信息
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:01
     */
    void uptOper(UptOperReq req);

    /**
     * 获得操作员信息
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:21
     */
    TSidOper getOper(OperSearchReq req);

    /**
     * 获得操作员列表
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:21
     */
    List<TSidOper> getOperList(OperSearchReq req, int pageSize, int page);

    /**
     * 查询商户操作员信息
     *
     * @param mchId
     * @return
     */
    List<OperResp> queryByMchId(String mchId);

    /**
     * 查询下级操作员信息
     *
     * @param operId
     * @param roleId
     * @return
     */
    List<OperResp> queryByOperId(Integer operId,String roleId,Integer pcoreId,Integer coreId);
}
