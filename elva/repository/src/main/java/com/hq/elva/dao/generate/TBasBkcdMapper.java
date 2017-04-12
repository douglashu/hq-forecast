package com.hq.elva.dao.generate;

import com.hq.elva.entity.generate.TBasBkcd;
import com.hq.elva.entity.generate.TBasBkcdExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TBasBkcdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int countByExample(TBasBkcdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int deleteByExample(TBasBkcdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int deleteByPrimaryKey(String lbnkCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int insert(TBasBkcd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int insertSelective(TBasBkcd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    List<TBasBkcd> selectByExample(TBasBkcdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    TBasBkcd selectByPrimaryKey(String lbnkCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int updateByExampleSelective(@Param("record") TBasBkcd record, @Param("example") TBasBkcdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int updateByExample(@Param("record") TBasBkcd record, @Param("example") TBasBkcdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int updateByPrimaryKeySelective(TBasBkcd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkcd
     *
     * @mbggenerated Thu Nov 03 17:28:37 CST 2016
     */
    int updateByPrimaryKey(TBasBkcd record);
}