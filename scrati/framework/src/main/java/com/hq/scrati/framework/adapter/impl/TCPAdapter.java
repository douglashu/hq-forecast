package com.hq.scrati.framework.adapter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hq.esc.dao.generate.TesbTcpMapper;
import com.hq.esc.entity.generate.TesbService;
import com.hq.esc.entity.generate.TesbTcp;
import com.hq.esc.entity.generate.TesbTcpExample;
import com.hq.esc.inf.entity.BizContent;
import com.hq.esc.inf.entity.Constants;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.framework.adapter.IAdapter;
import com.hq.scrati.framework.invoker.TCPShortInvoker;
import com.hq.scrati.cache.local.LocalCacheDelayExpire;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hq.scrati.framework.invoker.TCPLongInvoker;

@Component("tcpAdapter")
public class TCPAdapter implements IAdapter {

	public static LocalCacheDelayExpire<TesbTcp> tcpCache = new LocalCacheDelayExpire<TesbTcp>(5 * 60 * 1000L, 100000L);
	
	@Resource
	private TesbTcpMapper tesbTcpMapper;
	
	@Resource
	private TCPShortInvoker tcpShortInvoker;
	
	@Resource
	private TCPLongInvoker tcplongInvoker;
	
	@Override
	public Object excute(TesbService tesbService, Map<String, Object> parameters) throws Exception {
		String key =tesbService.getServiceCode()+"_"+tesbService.getServiceVersion();
		TesbTcp tesbTcp= tcpCache.get(key);
		if(tesbTcp==null){
			// 调用后台http服务
			TesbTcpExample example = new TesbTcpExample();
			example.createCriteria().andServiceCodeEqualTo(tesbService.getServiceCode()).andServiceVersionEqualTo(tesbService.getServiceVersion());
			try{
				List<TesbTcp> tesbTcpList= tesbTcpMapper.selectByExample(example);
				if(tesbTcpList!=null){
					tesbTcp = tesbTcpList.get(0);
					tcpCache.put(key, tesbTcp);
				}else{
					String msg = String.format("service not exist, service=%s, version=%s", tesbService.getServiceCode(),tesbService.getServiceVersion());
					throw new InfException(Constants.SERVICE_UNAVAILABLE,msg," ");
				}
			}catch(Exception e){
				tesbTcp = tcpCache.get(key, true);
				tcpCache.put(key, tesbTcp);
			}
		}
		List<String> parameterTypes = new ArrayList<String>();
		List<Object> httpParameters = new ArrayList<Object>();
		if(Constants.NEED_HEAD_TRUE.equals(tesbService.getNeedHead())){
			Map<String, Object> tmpMap = new HashMap<>();
			for(Map.Entry<String,Object> entry:parameters.entrySet()){
				if(entry.getKey().startsWith(Constants.HTTP_HEAD_PREFIX)){
					tmpMap.put(entry.getKey().replace(Constants.HTTP_HEAD_PREFIX,""),entry.getValue());
				}else{
					tmpMap.put(entry.getKey(), entry.getValue());
				}
			}
			parameters = tmpMap;
		}
		TypeReference<List<BizContent>> type = new TypeReference<List<BizContent>>() {
		};
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
				httpParameters.add(parameters.get(paramMeta.getName()));
			}
		}
		if("2".equals(tesbTcp.getLinkMode())){
			return tcplongInvoker.invokeMethod(tesbTcp, httpParameters);
		}else{
			return tcpShortInvoker.invokeMethod(tesbTcp, httpParameters);
		}
	}

}
