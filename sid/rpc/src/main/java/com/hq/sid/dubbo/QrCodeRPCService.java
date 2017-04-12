package com.hq.sid.dubbo;

import com.github.pagehelper.PageInfo;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.QrCodeRsp;

/**
 * @包名称：com.hq.sid.dubbo
 * @创建人：yyx
 * @创建时间：17/1/26 下午4:11
 */
public interface QrCodeRPCService {

    /**
     * 添加二维码信息
     *
     * @param hqRequest （QrCodeReq）
     * @return
     */
    boolean insert(HqRequest hqRequest);

    /**
     * 删除二维码信息
     *
     * @param hqRequest（QrCodeIdReq）
     * @return
     */
    boolean delete(HqRequest hqRequest);

    /**
     * 更新二维码信息
     *
     * @param hqRequest （QrCodeReq）
     * @return
     */
    boolean update(HqRequest hqRequest);

    /**
     * 查询二维码信息
     *
     * @param hqRequest （QrCodeIdReq）
     * @return
     */
    QrCodeRsp queryById(HqRequest hqRequest);

    /**
     * 分页查询二维码消息
     *
     * @param hqRequest
     * @return
     */
    PageInfo<QrCodeRsp> queryByCondition(HqRequest hqRequest);

    /**
     * 更新二维码的状态
     *
     * @param hqRequest
     * @return
     */
    boolean updateQrCodeState(HqRequest hqRequest);

}
