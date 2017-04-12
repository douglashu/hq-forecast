package com.hq.elva.web.controller;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.entity.generate.TBasDict;
import com.hq.elva.service.ITBasDictService;
import com.hq.scrati.common.log.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/dict")
public class TBasDictController extends BaseController {
	
	public static final Logger logger = Logger.getLogger(TBasDictController.class);
	@Resource
	private ITBasDictService iTBasDictService;
	
	@RequestMapping(value = "/add")
	public Object addTBasConfig(HttpServletRequest request,@RequestBody TBasDict tBasDict){
		try{
			iTBasDictService.insert(tBasDict);
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<String>("9999","新增配置数据失败，失败原因为："+e.getMessage()," "), HttpStatus.OK);
		}
		return getJSONResp(new RespEntity<String>("0000","OK"," "), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/update")
	public Object updateTBasConfig(HttpServletRequest request,@RequestBody TBasDict tBasDict){
		try{
			iTBasDictService.update(tBasDict);
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<String>("9999","修改配置数据失败，失败原因为："+e.getMessage()," "), HttpStatus.OK);
		}
		return getJSONResp(new RespEntity<String>("0000","OK"," "), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete")
	public Object deleteTBasConfig(HttpServletRequest request){
		try{
			Map<String, String> req = HttpUtils.getParameterMap(request);
			iTBasDictService.delete(req.get("paramId"));
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<String>("9999","删除配置数据失败，失败原因为："+e.getMessage()," "), HttpStatus.OK);
		}
		return getJSONResp(new RespEntity<String>("0000","OK"," "), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/query")
	public Object queryByService(HttpServletRequest request){
		RespEntity<Map<String, Object>> resp = null;
		try{
			Map<String, String> req = HttpUtils.getParameterMap(request);
			resp = iTBasDictService.getTBasDictsPage((String)req.get("paramName"), 
					Integer.parseInt((String)req.get("pageSize")), Integer.parseInt((String)req.get("curPage")));
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<Map<String, Object>>("9999","修改配置数据失败，失败原因为："+e.getMessage(),null), HttpStatus.OK);
		}
		return getJSONResp(resp, HttpStatus.OK);
	}
	
}
