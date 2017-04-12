package com.hq.sid.dao.generate.custom;

import com.hq.sid.entity.generate.TSidDevice;
import com.hq.sid.entity.generate.TSidOper;
import com.hq.sid.service.entity.response.OperResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @包名称：com.hq.sid.dao.generate.custom
 * @创建人：yyx
 * @创建时间：17/2/6 下午1:50
 */
public interface OperMercMapper {

    public List<TSidOper> selectOperByMchId(@Param("mchId")String mchId);

    public List<TSidDevice> selectDeviceByMchId(@Param("mchId")String mchId);

    public List<OperResp> selectOpersConfig(@Param("operIds")List operIds);

}
