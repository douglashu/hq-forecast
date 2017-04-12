package com.hq.flood.dao.generate;

import com.hq.flood.entity.generate.TFloodRoleTemplate;
import com.hq.flood.entity.generate.TFloodRoleTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TFloodRoleTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    long countByExample(TFloodRoleTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int deleteByExample(TFloodRoleTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int insert(TFloodRoleTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int insertSelective(TFloodRoleTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    List<TFloodRoleTemplate> selectByExampleWithRowbounds(TFloodRoleTemplateExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    List<TFloodRoleTemplate> selectByExample(TFloodRoleTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    TFloodRoleTemplate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByExampleSelective(@Param("record") TFloodRoleTemplate record, @Param("example") TFloodRoleTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByExample(@Param("record") TFloodRoleTemplate record, @Param("example") TFloodRoleTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByPrimaryKeySelective(TFloodRoleTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByPrimaryKey(TFloodRoleTemplate record);
}