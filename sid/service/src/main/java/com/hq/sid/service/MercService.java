package com.hq.sid.service;

import com.github.pagehelper.PageInfo;
import com.hq.sid.service.entity.request.MercReq;
import com.hq.sid.service.entity.request.MercSearchReq;
import com.hq.sid.service.entity.response.MercResp;

/**
 * Created by Zale on 2016/12/12.
 */
public interface MercService {
    /**
     * 创建商户信息
     * @Author Zale
     * @Date 2016/12/12 下午11:17
     *
     */
    MercResp createMerc(MercReq req) throws Exception;
    /**
     * 更新商户信息
     * @Author Zale
     * @Date 2016/12/12 下午11:20
     *
     */
     void uptMerc(MercReq req);

    /**
     * 获得商户信息
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:25
     */
    MercResp getMercByCoreId(Integer coreId);
    /**
     * 获得商户信息
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:25
     */
    MercResp getMercByMercId(String mercId);
    /**
     * 获得商户信息列表
     *
     * @Author Zale
     * @Date 2016/12/12 下午11:25
     */
    PageInfo<MercResp> getMercList(MercSearchReq req,int pageSize,int page);
}
