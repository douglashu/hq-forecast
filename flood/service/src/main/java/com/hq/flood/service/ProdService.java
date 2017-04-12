package com.hq.flood.service;

import com.github.pagehelper.PageInfo;
import com.hq.flood.service.entity.request.ProdReq;
import com.hq.flood.service.entity.response.ProdRsp;

/**
 * 产品管理
 *
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:09
 */
public interface ProdService {
    /**
     * 产品新增
     *
     * @param userId //经办员ID
     * @param req     //产品信息
     * @return
     */
    boolean saveProductTransactional(String userId, ProdReq req);

    /**
     * 产品修改
     *
     * @param userId //经办员ID
     * @param req     //产品信息
     * @return
     */
    boolean updateProductTransactional(String userId, ProdReq req);

    /**
     * 产品删除
     *
     * @param userId //经办员ID
     * @param prdCode //产品代码
     * @return
     */
    boolean deleteProductTransactional(String userId, String prdCode);

    /**
     * 产品查询
     *
     * @param prdCode //产品代码
     * @return
     */
    ProdRsp queryProductById(String prdCode);

    /**
     * 产品列表查询
     *
     * @param prdName  //产品名称
     * @param page     //页码
     * @param pageSize //每页行数
     * @return
     */
    PageInfo<ProdRsp> queryProductList(String prdName, Integer page, Integer pageSize);
}
