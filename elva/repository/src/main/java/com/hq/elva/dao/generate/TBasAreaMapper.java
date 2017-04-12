package com.hq.elva.dao.generate;

import com.hq.elva.entity.generate.TBasArea;
import com.hq.elva.entity.generate.TBasAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TBasAreaMapper {
    long countByExample(TBasAreaExample example);

    int deleteByExample(TBasAreaExample example);

    int deleteByPrimaryKey(String areaCode);

    int insert(TBasArea record);

    int insertSelective(TBasArea record);

    List<TBasArea> selectByExampleWithRowbounds(TBasAreaExample example, RowBounds rowBounds);

    List<TBasArea> selectByExample(TBasAreaExample example);

    TBasArea selectByPrimaryKey(String areaCode);

    int updateByExampleSelective(@Param("record") TBasArea record, @Param("example") TBasAreaExample example);

    int updateByExample(@Param("record") TBasArea record, @Param("example") TBasAreaExample example);

    int updateByPrimaryKeySelective(TBasArea record);

    int updateByPrimaryKey(TBasArea record);
}