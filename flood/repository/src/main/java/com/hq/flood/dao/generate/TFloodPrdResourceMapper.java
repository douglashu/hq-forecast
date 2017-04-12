package com.hq.flood.dao.generate;

import com.hq.flood.entity.generate.TFloodPrdResource;
import com.hq.flood.entity.generate.TFloodPrdResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TFloodPrdResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    long countByExample(TFloodPrdResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int deleteByExample(TFloodPrdResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int insert(TFloodPrdResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int insertSelective(TFloodPrdResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    List<TFloodPrdResource> selectByExampleWithRowbounds(TFloodPrdResourceExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    List<TFloodPrdResource> selectByExample(TFloodPrdResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByExampleSelective(@Param("record") TFloodPrdResource record, @Param("example") TFloodPrdResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_prd_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    int updateByExample(@Param("record") TFloodPrdResource record, @Param("example") TFloodPrdResourceExample example);
}