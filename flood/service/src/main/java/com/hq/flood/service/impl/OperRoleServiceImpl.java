package com.hq.flood.service.impl;

import com.hq.flood.dao.custom.OperRoleDao;
import com.hq.flood.dao.generate.TFloodOperRoleMapper;
import com.hq.flood.dao.generate.TFloodRoleMapper;
import com.hq.flood.dao.generate.TFloodRoleTemplateMapper;
import com.hq.flood.entity.generate.*;
import com.hq.flood.service.OperRoleService;
import com.hq.flood.service.entity.response.OperRoleRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.impl
 * @创建人：yyx
 * @创建时间：17/2/9 下午7:00
 */
@Service
public class OperRoleServiceImpl implements OperRoleService{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private TFloodOperRoleMapper floodOperRoleMapper;

    @Autowired
    private TFloodRoleMapper roleMapper;

    @Autowired
    private TFloodRoleTemplateMapper floodRoleTemplateMapper;

    @Autowired
    private OperRoleDao operRoleDao;

    @Override
    @Transactional
    public Boolean bindRole(Integer userId,Integer operId, List<String> roles) {
        if( null == userId ){
            throw new ParamValueException("参数【userId】不能为空");
        }
        validateOper(operId);
        try {
            // 删除原有的操作员拥有的角色
            TFloodOperRoleExample floodOperRoleExample = new TFloodOperRoleExample();
            TFloodOperRoleExample.Criteria criteria = floodOperRoleExample.createCriteria();
            criteria.andOperIdEqualTo( operId );
            floodOperRoleMapper.deleteByExample(floodOperRoleExample );
            // 插入数据
            for (String role : roles) {
                TFloodOperRole operRole = new TFloodOperRole();
                operRole.setOperId(operId);
                operRole.setCreateTime(Calendar.getInstance().getTime());
                operRole.setCreateOperId( userId );
                operRole.setRoleId( role );
                floodOperRoleMapper.insert( operRole );
            }
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean bindRoleTemplate(Integer userId, Integer operId, String coreId, Integer templateId) {
        if( null == userId ){
            throw new ParamValueException("参数【userId】不能为空");
        }
        if(StringUtils.isBlank(coreId)){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        validateOper(operId);
        TFloodRoleTemplate template = validateTemplate(templateId);
        try{
            operRoleDao.deleteOperRoleWithRoleTemplate(operId,coreId);

            TFloodRoleExample roleExample = new TFloodRoleExample();
            TFloodRoleExample.Criteria criteria = roleExample.createCriteria();
            criteria.andTemplateIdEqualTo( templateId );
            criteria.andUrmIdEqualTo( coreId );
            List<TFloodRole> roles = roleMapper.selectByExample( roleExample );
            if(CollectionUtils.isNotEmpty(roles)){
                TFloodRole tempRole = roles.get( 0 );
                TFloodOperRole operRole = new TFloodOperRole();
                operRole.setOperId(operId);
                operRole.setCreateTime(Calendar.getInstance().getTime());
                operRole.setCreateOperId( userId );
                operRole.setRoleId( tempRole.getRoleId() );
                floodOperRoleMapper.insert( operRole );
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        throw new BusinessException("没有对应的角色信息");
    }

    @Override
    public List<OperRoleRsp> queryOpersWithMchId(String roleId,Integer pCoreId, Integer coreId) {
        if( StringUtils.isBlank(roleId)){
            throw new ParamValueException("参数【roleId】不能为空");
        }
        if( null==coreId){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        try {
            List<TFloodOperRole> result = operRoleDao.selectSubOpers(roleId,pCoreId,coreId);
            List<OperRoleRsp> rsp = new ArrayList<>();
            CollectionUtils.copyCollections(result,rsp,OperRoleRsp.class);
            return rsp;

        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
    }

    private TFloodRoleTemplate validateTemplate(Integer templateId) {
        if( null == templateId ){
            throw new ParamValueException("参数【templateId】不能为空");
        }
        try {
            TFloodRoleTemplate template = floodRoleTemplateMapper.selectByPrimaryKey(templateId);
            if (null == template) {
                throw new BusinessException("没有对应的角色模版");
            }
            return template;
        }catch (Exception e){
            logger.error(""+e);
            throw new BusinessException("数据库异常");
        }
    }

    private String[] validateRole(String roleIds) {
        if(StringUtils.isBlank(roleIds)){
            throw new ParamValueException("角色不能为空");
        }
        String[] roles = roleIds.split("\\|");
        for (String role : roles) {
            TFloodRole floodRole = roleMapper.selectByPrimaryKey( role );
            if( null == floodRole){
                throw new BusinessException("角色不存在");
            }
        }
        return roles;
    }
    private void validateOper(Integer operId) {
        if( null == operId ){
            throw new ParamValueException("参数【operId】不能为空");
        }
    }
}
