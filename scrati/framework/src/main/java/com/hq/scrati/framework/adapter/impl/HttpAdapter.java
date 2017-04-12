package com.hq.scrati.framework.adapter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hq.esc.dao.generate.TesbHttpMapper;
import com.hq.esc.entity.generate.TesbHttp;
import com.hq.esc.entity.generate.TesbHttpExample;
import com.hq.esc.entity.generate.TesbService;
import com.hq.esc.inf.entity.BizContent;
import com.hq.esc.inf.entity.Constants;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.framework.invoker.RestFulInvoker;
import com.hq.scrati.cache.local.LocalCacheDelayExpire;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hq.scrati.framework.adapter.IAdapter;
import com.hq.scrati.framework.invoker.GetInvoker;
import com.hq.scrati.framework.invoker.PostInvoker;

@Component("httpAdapter")
public class HttpAdapter implements IAdapter {
	
	public static LocalCacheDelayExpire<TesbHttp> httpCache = new LocalCacheDelayExpire<TesbHttp>(5 * 60 * 1000L, 100000L);
	
	@Resource
	private TesbHttpMapper tesbHttpMapper;
	
	@Resource
	private RestFulInvoker restFulInvoker;
	
	@Resource
	private PostInvoker postInvoker;
	
	@Resource
	private GetInvoker getInvoker;
	
	@Override
	public Object excute(TesbService tesbService, Map<String, Object> parameters) throws Exception {
		String key =tesbService.getServiceCode()+"_"+tesbService.getServiceVersion();
		TesbHttp tesbHttp= httpCache.get(key);
		if(tesbHttp==null){
			// 调用后台http服务
			TesbHttpExample example = new TesbHttpExample();
			example.createCriteria().andServiceCodeEqualTo(tesbService.getServiceCode()).andServiceVersionEqualTo(tesbService.getServiceVersion());
			try{
				List<TesbHttp> tesbHttpList= tesbHttpMapper.selectByExample(example);
				if(tesbHttpList!=null){
					tesbHttp = tesbHttpList.get(0);
					httpCache.put(key, tesbHttp);
				}else{
					String msg = String.format("service not exist, service=%s, version=%s", tesbService.getServiceCode(),tesbService.getServiceVersion());
					throw new InfException(Constants.SERVICE_UNAVAILABLE,msg," ");
				}
			}catch(Exception e){
				tesbHttp = httpCache.get(key, true);
				httpCache.put(key, tesbHttp);
			}
		}
		List<String> parameterTypes = new ArrayList<String>();
		List<Object> httpParameters = new ArrayList<Object>();
		TypeReference<List<BizContent>> type = new TypeReference<List<BizContent>>() {};
		List<BizContent> paramMetas = JSON.parseObject(tesbService.getBizContent(), type);
		if(!(tesbService.getBizContent()==null||"".equals(tesbService.getBizContent()))){
			Collections.sort(paramMetas, new Comparator<BizContent>() {
				@Override
				public int compare(BizContent o1, BizContent o2) {
					return o1.getOrder()-o2.getOrder();
				}
			});
			
			for(BizContent paramMeta:paramMetas){
				parameterTypes.add(paramMeta.getType());
				if("1".equals(tesbHttp.getUrlStyle())){
					httpParameters.add(paramMeta.getName()+"="+parameters.get(paramMeta.getName()));
				}else{
					httpParameters.add(parameters.get(paramMeta.getName()));
				}
			}
		}
		if("1".equals(tesbHttp.getUrlStyle())){
			if("post".equals(tesbHttp.getMethod().toLowerCase())){
				if(Constants.NEED_HEAD_TRUE.equals(tesbService.getNeedHead())) {
					return postInvoker.invokeMethod(tesbHttp, httpParameters, parameters);
				}else{
					return postInvoker.invokeMethod(tesbHttp, httpParameters, null);
				}
			}else{
				if(Constants.NEED_HEAD_TRUE.equals(tesbService.getNeedHead())) {
					return getInvoker.invokeMethod(tesbHttp, httpParameters, parameters);
				}else{
					return getInvoker.invokeMethod(tesbHttp, httpParameters, null);
				}
			}
			
		}else{
			if(Constants.NEED_HEAD_TRUE.equals(tesbService.getNeedHead())) {
				return restFulInvoker.invokeMethod(tesbHttp, httpParameters,parameters);
			}else{
				return restFulInvoker.invokeMethod(tesbHttp, httpParameters,null);
			}
		}
	}
}
