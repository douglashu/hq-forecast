package com.hq.elva.rpc.facade.impl;

import com.alibaba.fastjson.JSON;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.dao.generate.TBasAreaMapper;
import com.hq.elva.entity.generate.TBasArea;
import com.hq.elva.rpc.facade.TBasAreaFacade;
import com.hq.elva.service.ITBasAreaService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasAreaFacadeImpl implements TBasAreaFacade {

    public static final Logger logger = Logger.getLogger(TBasAreaFacadeImpl.class);

    @Resource
    private ITBasAreaService iTBasAreaService;

    @Autowired
    private TBasAreaMapper tBasAreaMapper;

    /**
     * 获取省份信息
     *
     * @return
     */
    @Override
    public RespEntity<List<Map<String, String>>> getProv() {
        List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
        try {
            List<TBasArea> list = iTBasAreaService.getProv();
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("areaCode", list.get(i).getAreaCode());
                map.put("areaName", list.get(i).getAreaName());
                reList.add(map);
            }
        } catch (Exception e) {
            logger.error("", e);
            return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY, "获取系统参数错误", " ", reList);
        }
        return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY, "OK", " ", reList);
    }

    /**
     * 根据省份信息获取地市
     *
     * @return
     */
    @Override
    public RespEntity<List<Map<String, String>>> getCity(String provCode) {
        List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
        try {
            List<TBasArea> list = iTBasAreaService.getCity(provCode);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("areaCode", list.get(i).getAreaCode());
                map.put("areaName", list.get(i).getAreaName());
                reList.add(map);
            }
        } catch (Exception e) {
            logger.error("", e);
            return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY, "获取系统参数错误", " ", reList);
        }
        return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY, "OK", " ", reList);
    }

    @Override
    public Map<String, String> getName(HqRequest request) {
        List<String> code = null;
        try {
            code = JSON.parseArray(request.getBizContent(), String.class);

            Map<String, String> result = new HashMap<>();
            for (String cd : code) {
                TBasArea area = tBasAreaMapper.selectByPrimaryKey(cd);
                if (area != null) {
                    result.put(cd, area.getAreaName());
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e);
            throw new BusinessException("查询失败");
        }
    }

}
