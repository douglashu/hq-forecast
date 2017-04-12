package com.hq.elva.service.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.dao.generate.TBasDictMapper;
import com.hq.elva.entity.generate.TBasDict;
import com.hq.elva.entity.generate.TBasDictExample;
import com.hq.elva.entity.generate.TBasDictExample.Criteria;
import com.hq.elva.service.ITBasDictService;
import com.hq.elva.util.MybatisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TBasDictServiceImpl implements ITBasDictService {


	@Resource
	private TBasDictMapper tBasDictMapper;
	/**
	 * 根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	@Override
	public List<TBasDict> getTBasDicts(String paramName) {
		TBasDictExample example = new TBasDictExample();
		example.createCriteria().andParamNameEqualTo(paramName);
		List<TBasDict> list = tBasDictMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据参数名以及参数值获取参数列表
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	@Override
	public String getTBasDict(String paramName, String paramValue) {
		TBasDictExample example = new TBasDictExample();
		example.createCriteria().andParamNameEqualTo(paramName).andParamValueEqualTo(paramValue);
		List<TBasDict> list = tBasDictMapper.selectByExample(example);
		return list.get(0).getParamExp();
	}



	public TBasDictMapper gettBasDictMapper() {
		return tBasDictMapper;
	}

	public void settBasDictMapper(TBasDictMapper tBasDictMapper) {
		this.tBasDictMapper = tBasDictMapper;
	}

	/**
	 *  根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	@Override
	public RespEntity<Map<String, Object>> getTBasDictsPage(String paramName, int pageSize, int curPage) {
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("pageSize", pageSize);
		pageMap.put("curPage", curPage);
		TBasDictExample example = new TBasDictExample();
		Criteria criteria = example.createCriteria();
		if(paramName!=null&&!"".equals(paramName)){
			criteria.andParamNameEqualTo(paramName);
		}
		example.setOrderByClause("param_id");
		int cnt=tBasDictMapper.countByExample(example);
		List<TBasDict> list = tBasDictMapper.selectByExampleWithRowbounds(example, MybatisUtil.getRowBounds(pageMap));
		RespEntity<Map<String, Object>> entity = new RespEntity<Map<String, Object>>("0000"," "," ",MybatisUtil.populate(list, cnt));
		return entity;
	}

	/**
	 * 新增tBasDict数据
	 * @param tBasConfig
	 */
	@Override
	public void insert(TBasDict tBasDict) {
		tBasDictMapper.insert(tBasDict);

	}
	/**
	 * 修改tBasDict数据
	 * @param tBasConfig
	 */
	@Override
	public void update(TBasDict tBasDict) {
		TBasDictExample example = new TBasDictExample();
		Criteria criteria = example.createCriteria();
		criteria.andParamIdEqualTo(tBasDict.getParamId());
		tBasDictMapper.updateByExample(tBasDict, example);
	}
	/**
	 * 修改tBasDict数据
	 * @param tBasConfig
	 */
	@Override
	public void delete(String paramId) {
		tBasDictMapper.deleteByPrimaryKey(Long.parseLong(paramId));
	}
}
