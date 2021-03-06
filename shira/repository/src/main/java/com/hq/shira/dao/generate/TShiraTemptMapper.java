package com.hq.shira.dao.generate;

import com.hq.shira.entity.generate.TShiraTempt;
import com.hq.shira.entity.generate.TShiraTemptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TShiraTemptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    long countByExample(TShiraTemptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int deleteByExample(TShiraTemptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int insert(TShiraTempt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int insertSelective(TShiraTempt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    List<TShiraTempt> selectByExampleWithRowbounds(TShiraTemptExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    List<TShiraTempt> selectByExample(TShiraTemptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    TShiraTempt selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int updateByExampleSelective(@Param("record") TShiraTempt record, @Param("example") TShiraTemptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int updateByExample(@Param("record") TShiraTempt record, @Param("example") TShiraTemptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int updateByPrimaryKeySelective(TShiraTempt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_shira_tempt
     *
     * @mbg.generated Wed Apr 05 23:04:28 CST 2017
     */
    int updateByPrimaryKey(TShiraTempt record);
}