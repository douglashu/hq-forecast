package com.hq.sid.dao.generate;

import com.hq.sid.entity.generate.TSidCode;
import com.hq.sid.entity.generate.TSidCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TSidCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    long countByExample(TSidCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int deleteByExample(TSidCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int insert(TSidCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int insertSelective(TSidCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    List<TSidCode> selectByExampleWithRowbounds(TSidCodeExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    List<TSidCode> selectByExample(TSidCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    TSidCode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int updateByExampleSelective(@Param("record") TSidCode record, @Param("example") TSidCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int updateByExample(@Param("record") TSidCode record, @Param("example") TSidCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int updateByPrimaryKeySelective(TSidCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_code
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    int updateByPrimaryKey(TSidCode record);
}