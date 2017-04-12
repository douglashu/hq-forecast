package com.hq.soto.dao.generate.custom;

import com.hq.soto.service.entity.response.FeeMercRsp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @包名称：com.hq.soto.dao.generate.custom
 * @创建人：yyx
 * @创建时间：17/2/16 下午10:34
 */
public interface FeeMercDao {

    public FeeMercRsp queryFeeByMchId(@Param("mchId")Integer mchId);
}
