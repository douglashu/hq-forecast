package com.hq.ellie.dao.generate;

import com.hq.ellie.entity.generate.Customer;
import com.hq.ellie.entity.generate.CustomerExample;
import com.hq.ellie.entity.generate.CustomerKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    long countByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int deleteByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int deleteByPrimaryKey(CustomerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    List<Customer> selectByExampleWithRowbounds(CustomerExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    List<Customer> selectByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    Customer selectByPrimaryKey(CustomerKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int updateByExample(@Param("record") Customer record, @Param("example") CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    int updateByPrimaryKey(Customer record);
}