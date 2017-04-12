package com.hq.diego.repository.dao.generate;

import com.hq.diego.repository.model.generate.TDiegoCancelOrder;
import com.hq.diego.repository.model.generate.TDiegoCancelOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TDiegoCancelOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    long countByExample(TDiegoCancelOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int deleteByExample(TDiegoCancelOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int insert(TDiegoCancelOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int insertSelective(TDiegoCancelOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    List<TDiegoCancelOrder> selectByExampleWithRowbounds(TDiegoCancelOrderExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    List<TDiegoCancelOrder> selectByExample(TDiegoCancelOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    TDiegoCancelOrder selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") TDiegoCancelOrder record, @Param("example") TDiegoCancelOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int updateByExample(@Param("record") TDiegoCancelOrder record, @Param("example") TDiegoCancelOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int updateByPrimaryKeySelective(TDiegoCancelOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_diego_cancel_order
     *
     * @mbg.generated Wed Mar 22 18:29:43 CST 2017
     */
    int updateByPrimaryKey(TDiegoCancelOrder record);
}