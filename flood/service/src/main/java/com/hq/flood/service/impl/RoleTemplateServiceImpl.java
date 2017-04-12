package com.hq.flood.service.impl;

import com.hq.flood.dao.custom.RoleTemplateDao;
import com.hq.flood.entity.generate.TFloodRoleTemplate;
import com.hq.flood.service.RoleTemplateService;
import com.hq.flood.service.entity.response.RoleTemplateRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.impl
 * @创建人：yyx
 * @创建时间：17/2/10 下午2:54
 */
@Service
public class RoleTemplateServiceImpl implements RoleTemplateService {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private RoleTemplateDao roleTemplateDao;

    @Override
    public List<RoleTemplateRsp> queryByRoleId(String roleId) {
        if(StringUtils.isBlank(roleId)){
            throw new ParamValueException("角色ID不能为空");
        }
        try{
            List<TFloodRoleTemplate> templates = roleTemplateDao.findTemplateByRoleId(roleId);
            List<RoleTemplateRsp> rsps = new ArrayList<>();
            CollectionUtils.copyCollections(templates,rsps,RoleTemplateRsp.class);
            return  rsps;
        }catch (Exception e){
            logger.error(""+e);
            throw new BusinessException("数据库异常");
        }
    }
}
