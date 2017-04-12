package com.hq.flood.dao.custom;

import com.hq.flood.entity.generate.TFloodOperRole;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @包名称：com.hq.flood.dao.custom
 * @创建人：yyx
 * @创建时间：17/2/11 下午9:16
 */
public interface OperRoleDao {

    public void deleteOperRoleWithRoleTemplate(@Param("operId") Integer operId,@Param("urmId")String urmId);

    public List<BatchOperRoleRsp> batchRoles(@Param("systemId")String systemId, @Param("operIds")List<Integer> operIds);

    public List<TFloodOperRole> selectSubOpers(@Param("roleId")String roleId, @Param("pcoreId")Integer pCoreId,@Param("coreId")Integer coreId);
}
