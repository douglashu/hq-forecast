package com.hq.tony.dao.generate;

import com.hq.tony.entity.generate.WeixinMenuentity;
import com.hq.tony.entity.generate.WeixinMenuentityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WeixinMenuentityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    long countByExample(WeixinMenuentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int deleteByExample(WeixinMenuentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int insert(WeixinMenuentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int insertSelective(WeixinMenuentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    List<WeixinMenuentity> selectByExampleWithRowbounds(WeixinMenuentityExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    List<WeixinMenuentity> selectByExample(WeixinMenuentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    WeixinMenuentity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int updateByExampleSelective(@Param("record") WeixinMenuentity record, @Param("example") WeixinMenuentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int updateByExample(@Param("record") WeixinMenuentity record, @Param("example") WeixinMenuentityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int updateByPrimaryKeySelective(WeixinMenuentity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_menuentity
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    int updateByPrimaryKey(WeixinMenuentity record);
}