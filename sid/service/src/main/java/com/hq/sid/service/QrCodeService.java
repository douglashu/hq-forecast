package com.hq.sid.service;

import com.github.pagehelper.PageInfo;
import com.hq.sid.service.entity.request.QrCodeReq;
import com.hq.sid.service.entity.response.QrCodeRsp;

/**
 * @包名称：com.hq.sid.service
 * @创建人：yyx
 * @创建时间：17/1/25 下午3:33
 */
public interface QrCodeService {

    /**
     * 新增二维码
     *
     * @param req
     * @return
     */
    boolean insertQrCodeTransactional(QrCodeReq req);

    /**
     * 更新二维码
     *
     * @param req
     * @return
     */
    boolean updateQrCodeTransactional(QrCodeReq req);

    /**
     * 删除二维码
     *
     * @param codeId
     * @return
     */
    boolean deleteQrCodeTransactional(Integer codeId);

    /**
     * 查询二维码信息
     *
     * @param codeId
     * @return
     */
    QrCodeRsp queryById(Integer codeId);

    /**
     * 分页查询二维码信息
     *
     * @param coreId
     * @param name
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<QrCodeRsp> queryByCondition(Integer coreId, String name, String type, Integer page, Integer pageSize);

    /**
     * 更新二维码状态
     *
     * @param  codeId
     * @param state
     * @return
     */
    Boolean updateQrCodeState(Integer codeId,String state);
}
