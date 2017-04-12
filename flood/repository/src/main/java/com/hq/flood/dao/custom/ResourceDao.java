package com.hq.flood.dao.custom;

import com.hq.flood.entity.generate.TFloodResource;
import com.hq.flood.service.entity.response.ResourceRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @包名称：com.hq.flood.dao.custom
 * @创建人：yyx
 * @创建时间：16/12/16 下午3:04
 */
public interface ResourceDao {

    /**
     * 查询产品拥有的资源
     *
     * @param prdId
     * @return
     */
    List<TFloodResource> findResourcesByPrdId(@Param(value = "prdId") String prdId);

    /**
     * 查询角色拥有的资源
     *
     * @param roleId
     * @return
     */
    List<TFloodResource> findResourcesByRoleId(@Param(value = "roleId") String roleId);


    /**
     * 查询角色拥有的资源
     *
     * @param systemId
     * @param operId
     * @return
     */
    List<ResourceRsp> findResourcesByOperId(@Param(value = "systemId")String systemId,@Param(value = "operId") Integer operId);

}
