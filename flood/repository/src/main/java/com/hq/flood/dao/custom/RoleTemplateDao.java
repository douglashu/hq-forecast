package com.hq.flood.dao.custom;

import com.hq.flood.entity.generate.TFloodRoleTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @包名称：com.hq.flood.dao.custom
 * @创建人：yyx
 * @创建时间：17/2/10 下午4:58
 */
public interface RoleTemplateDao {

    public List<TFloodRoleTemplate> findTemplateByRoleId(@Param("roleId")String roleId);

}
