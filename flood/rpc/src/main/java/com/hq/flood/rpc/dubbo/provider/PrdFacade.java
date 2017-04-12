package com.hq.flood.rpc.dubbo.provider;

import com.github.pagehelper.PageInfo;
import com.hq.flood.service.entity.response.ProdRsp;
import com.hq.scrati.model.HqRequest;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：16/12/16 下午10:32
 */
public interface PrdFacade {

    /**
     * 新增产品
     *
     * @param hqRequest 公共参数 + 产品对象【ProdReq】
     * @return
     */
    public boolean insert(HqRequest hqRequest);

    /**
     * 删除产品
     *
     * @param hqRequest 公共参数 + 产品ID【ProdIdReq】
     * @return
     */
    public boolean delete(HqRequest hqRequest);

    /**
     * 修改产品
     *
     * @param hqRequest 公共参数 + 产品对象【ProdReq】
     * @return
     */
    public boolean update(HqRequest hqRequest);

    /**
     * 查询产品信息
     *
     * @param hqRequest 产品ID【ProdIdReq】
     * @return
     */
    public ProdRsp queryById(HqRequest hqRequest);

    /**
     * 根据条件查询产品分页信息
     *
     * @param hqRequest 公共参数 + 产品分页对象【ProdPageReq】->{产品名称,开始记录数,每页记录数}
     * @return
     */
    public PageInfo<ProdRsp> findProdByCondition(HqRequest hqRequest);
}
