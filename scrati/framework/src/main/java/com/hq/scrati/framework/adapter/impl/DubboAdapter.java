package com.hq.scrati.framework.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hq.esc.dao.generate.TesbDubboMapper;
import com.hq.esc.entity.generate.TesbDubbo;
import com.hq.esc.entity.generate.TesbDubboExample;
import com.hq.esc.entity.generate.TesbService;
import com.hq.esc.inf.entity.BizContent;
import com.hq.esc.inf.entity.Constants;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.cache.local.LocalCacheDelayExpire;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.framework.adapter.IAdapter;
import com.hq.scrati.framework.constants.FrameworkConstants;
import com.hq.scrati.framework.invoker.DubboInvoker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("dubboAdapter")
public class DubboAdapter implements IAdapter {

    public static LocalCacheDelayExpire<TesbDubbo> dubboCache = new LocalCacheDelayExpire<TesbDubbo>(5 * 60 * 1000L, 100000L);

    @Resource
    private TesbDubboMapper tesbDubboMapper;

    @Resource
    private DubboInvoker dubboInvoker;

    /**
     * 调用协议按照指定协议调用后端应用
     *
     * @param tesbService
     * @param parameters
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public Object excute(TesbService tesbService, Map<String, Object> parameters) throws InfException, IllegalAccessException {
        Object serviceResult = null;
        String key = tesbService.getServiceCode() + "_" + tesbService.getServiceVersion();
        TesbDubbo dubbo = dubboCache.get(key);
        if (dubbo == null) {
            // 调用后台Dubbo服务
            TesbDubboExample example = new TesbDubboExample();
            example.createCriteria().andServiceCodeEqualTo(tesbService.getServiceCode())
                    .andServiceVersionEqualTo(tesbService.getServiceVersion());
            try {
                List<TesbDubbo> dubboList = tesbDubboMapper.selectByExample(example);
                if (dubboList != null) {
                    dubbo = dubboList.get(0);
                    dubboCache.put(key, dubbo);
                } else {
                    String msg = String.format("service not exist, service=%s, version=%s", tesbService.getServiceCode(),
                            tesbService.getServiceVersion());
                    throw new InfException(Constants.SERVICE_UNAVAILABLE, msg, " ");
                }
            } catch (Exception e) {
                dubbo = dubboCache.get(key, true);
                dubboCache.put(key, dubbo);
            }

        }
        List<String> parameterTypes = new ArrayList<String>();
        List<Object> dubboParameters = new ArrayList<Object>();
        //添加头信息
        if (Constants.NEED_HEAD_TRUE.equals(tesbService.getNeedHead())) {
            parameterTypes.add("java.util.Map");
            Map<String, Object> headMap = new HashMap<>();

            parameters.entrySet().stream().filter(e -> e.getKey().startsWith(Constants.HTTP_HEAD_PREFIX))
                    .forEach(e -> headMap.put(e.getKey().replace(Constants.HTTP_HEAD_PREFIX, ""), e.getValue()));
            dubboParameters.add(headMap);
        }
        TypeReference<List<BizContent>> type = new TypeReference<List<BizContent>>() {
        };
        if (tesbService.getBizContent() != null && !"".equals(tesbService.getBizContent())) {
            List<BizContent> paramMetas = JSON.parseObject(tesbService.getBizContent(), type);
            if (!(tesbService.getBizContent() == null || "".equals(tesbService.getBizContent()))) {
                paramMetas.stream().sorted(((o1, o2) -> o1.getOrder() - o2.getOrder())).forEach(paramMeta -> {
                    parameterTypes.add(paramMeta.getType());
                    dubboParameters.add(parameters.get(paramMeta.getName()));
                });
            }
        }
		serviceResult = dubboInvoker.invokeMethod(dubbo.getClazz(), dubbo.getMethod(),
				parameterTypes.toArray(new String[0]), dubboParameters.toArray(), tesbService.getServiceVersion());
		@SuppressWarnings("unchecked")
		Map<Object, Object> serviceMapResult = (HashMap<Object, Object>) serviceResult;
		if (FrameworkConstants.RESPONSE_ENTITY_CANONICAL_NAME.equals(serviceMapResult.get("class"))) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			RespEntity<Map<String, String>> respEntity = new RespEntity((String) serviceMapResult.get("key"),
					(String) serviceMapResult.get("msg"), " ", serviceMapResult.get("ext"));
			respEntity.setHost((String) serviceMapResult.get("host"));
			return respEntity;
		} else {
			return serviceResult;
		}
    }

}
