package com.hq.elva.dao.generate;

import com.hq.elva.entity.generate.TBasBankCity;
import com.hq.elva.entity.generate.TBasBankCityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TBasBankCityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int countByExample(TBasBankCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int deleteByExample(TBasBankCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int deleteByPrimaryKey(String cityCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int insert(TBasBankCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int insertSelective(TBasBankCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    List<TBasBankCity> selectByExample(TBasBankCityExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    List<TBasBankCity> selectByCityExample(TBasBankCityExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    List<TBasBankCity> selectByCountyExample(TBasBankCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    TBasBankCity selectByPrimaryKey(String cityCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int updateByExampleSelective(@Param("record") TBasBankCity record, @Param("example") TBasBankCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int updateByExample(@Param("record") TBasBankCity record, @Param("example") TBasBankCityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int updateByPrimaryKeySelective(TBasBankCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bankcity
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    int updateByPrimaryKey(TBasBankCity record);
}