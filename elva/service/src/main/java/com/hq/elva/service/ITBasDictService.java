package com.hq.elva.service;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.entity.generate.TBasDict;

import java.util.List;
import java.util.Map;

public interface ITBasDictService {
	
	/**
	 * 根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	public List<TBasDict> getTBasDicts(String paramName);
	
	/**
	 * 根据参数名以及参数值获取参数列表
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public String getTBasDict(String paramName, String paramValue);
	
	/**
	 *  根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	public RespEntity<Map<String, Object>> getTBasDictsPage(String paramName, int pageSize, int curPage);
	
	/**
	 * 新增tBasDict数据
	 * @param tBasConfig
	 */
	public void insert(TBasDict tBasDict);
	
	/**
	 * 修改tBasDict数据
	 * @param tBasConfig
	 */
	public void update(TBasDict tBasDict);
	
	/**
	 * 修改tBasDict数据
	 * @param tBasConfig
	 */
	public void delete(String paramId);

}
