package com.hq.tony.dao.generate;

import com.hq.tony.entity.generate.WeixinNewsitem;
import com.hq.tony.entity.generate.WeixinNewsitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WeixinNewsitemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    long countByExample(WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int deleteByExample(WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int insert(WeixinNewsitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int insertSelective(WeixinNewsitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    List<WeixinNewsitem> selectByExampleWithBLOBsWithRowbounds(WeixinNewsitemExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    List<WeixinNewsitem> selectByExampleWithBLOBs(WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    List<WeixinNewsitem> selectByExampleWithRowbounds(WeixinNewsitemExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    List<WeixinNewsitem> selectByExample(WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    WeixinNewsitem selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByExampleSelective(@Param("record") WeixinNewsitem record, @Param("example") WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") WeixinNewsitem record, @Param("example") WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByExample(@Param("record") WeixinNewsitem record, @Param("example") WeixinNewsitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByPrimaryKeySelective(WeixinNewsitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(WeixinNewsitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_newsitem
     *
     * @mbg.generated Sat Mar 04 11:19:50 CST 2017
     */
    int updateByPrimaryKey(WeixinNewsitem record);
}