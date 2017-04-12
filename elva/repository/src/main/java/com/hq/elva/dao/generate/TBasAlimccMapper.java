package com.hq.elva.dao.generate;

import com.hq.elva.entity.generate.TBasAlimcc;
import com.hq.elva.entity.generate.TBasAlimccExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TBasAlimccMapper {
    long countByExample(TBasAlimccExample example);

    int deleteByExample(TBasAlimccExample example);

    int deleteByPrimaryKey(String mccCd);

    int insert(TBasAlimcc record);

    int insertSelective(TBasAlimcc record);

    List<TBasAlimcc> selectByExampleWithRowbounds(TBasAlimccExample example, RowBounds rowBounds);

    List<TBasAlimcc> selectByExample(TBasAlimccExample example);

    TBasAlimcc selectByPrimaryKey(String mccCd);

    int updateByExampleSelective(@Param("record") TBasAlimcc record, @Param("example") TBasAlimccExample example);

    int updateByExample(@Param("record") TBasAlimcc record, @Param("example") TBasAlimccExample example);

    int updateByPrimaryKeySelective(TBasAlimcc record);

    int updateByPrimaryKey(TBasAlimcc record);
}