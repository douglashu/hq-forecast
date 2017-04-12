package com.hq.tony.service;

import com.github.pagehelper.PageInfo;
import com.hq.tony.service.entity.request.WeixinAccountReq;
import com.hq.tony.service.entity.response.WeixinAccountRsp;

/**
 * @包名称：com.hq.tony.service
 * @创建人：yyx
 * @创建时间：17/3/4 下午2:36
 */
public interface WeixinAccountService {

    /**
     * 新增微信公众帐号信息
     * @param req
     * @return
     */
    public Boolean insert(WeixinAccountReq req);

    /**
     * 更新微信公众帐号信息
     * @param req
     * @return
     */
    public Boolean update(WeixinAccountReq req);

    /**
     * 删除微信公众帐号信息
     * @param id
     * @return
     */
    public Boolean delete(String id);

    /**
     * 查询微信公众帐号信息
     * @param id
     * @return
     */
    public WeixinAccountRsp findById(String id);

    /**
     *
     * @param accountName   公众帐号名称
     * @param accountToken  公众帐号TOKEN
     * @param accountAppid  公众帐号APPID
     * @return
     */
    public PageInfo<WeixinAccountRsp> pages(String accountName,String accountToken,String accountAppid, Integer page, Integer pageSize);

}
