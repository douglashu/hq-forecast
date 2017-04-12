package com.hq.elva.web.controller;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.entity.generate.TBasConfig;
import com.hq.elva.service.ITBasConfigService;
import com.hq.scrati.common.log.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class TBasConfigController extends BaseController {
	
	public static final Logger logger = Logger.getLogger(TBasConfigController.class);
	@Resource
	private ITBasConfigService iTBasConfigService;
	
	@RequestMapping(value = "/add")
	public Object addTBasConfig(HttpServletRequest request,@RequestBody TBasConfig tBasConfig){
		try{
			iTBasConfigService.insert(tBasConfig);
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<String>("9999","新增配置数据失败，失败原因为："+e.getMessage()," "), HttpStatus.OK);
		}
		return getJSONResp(new RespEntity<String>("0000","OK"," "), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/update")
	public Object updateTBasConfig(HttpServletRequest request,@RequestBody TBasConfig tBasConfig){
		try{
			iTBasConfigService.update(tBasConfig);
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
			iTBasConfigService.delete(req.get("configId"));
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<String>("9999","删除配置数据失败，失败原因为："+e.getMessage()," "), HttpStatus.OK);
		}
		return getJSONResp(new RespEntity<String>("0000","OK"," "), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/queryByService")
	public Object queryByService(HttpServletRequest request){
		RespEntity<Map<String, Object>> resp = null;
		try{
			Map<String, String> req = HttpUtils.getParameterMap(request);
			resp = iTBasConfigService.getTBasConfigByServicePage((String)req.get("serviceName"),(String) req.get("serviceIp"), 
					Integer.parseInt((String)req.get("pageSize")), Integer.parseInt((String)req.get("curPage")));
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<Map<String, Object>>("9999","修改配置数据失败，失败原因为："+e.getMessage(),null), HttpStatus.OK);
		}
		return getJSONResp(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/query")
	public Object query(HttpServletRequest request){
		RespEntity<Map<String, Object>> resp = null;
		try{
			Map<String, String> req = HttpUtils.getParameterMap(request);
			resp = iTBasConfigService.getTBasConfigsPage((String)req.get("configId"),(String) req.get("configName"), (String)req.get("serviceName"),(String) req.get("serviceIp"), 
					Integer.parseInt((String)req.get("pageSize")), Integer.parseInt((String)req.get("curPage")));
		}catch(Exception e){
			logger.error("", e);
			return getJSONResp(new RespEntity<Map<String, Object>>("9999","修改配置数据失败，失败原因为："+e.getMessage(),null), HttpStatus.OK);
		}
		return getJSONResp(resp, HttpStatus.OK);
	}
}
