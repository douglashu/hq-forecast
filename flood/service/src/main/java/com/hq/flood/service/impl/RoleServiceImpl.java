package com.hq.flood.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.flood.dao.custom.OperRoleDao;
import com.hq.flood.dao.generate.TFloodRoleMapper;
import com.hq.flood.dao.generate.TFloodRoleTemplateMapper;
import com.hq.flood.entity.generate.TFloodRole;
import com.hq.flood.entity.generate.TFloodRoleExample;
import com.hq.flood.entity.generate.TFloodRoleTemplate;
import com.hq.flood.entity.generate.TFloodRoleTemplateExample;
import com.hq.flood.service.RoleService;
import com.hq.flood.service.entity.request.RoleReq;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import com.hq.flood.service.entity.response.RoleRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.UUIDGenerator;
import com.hq.scrati.common.util.ValidateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.impl
 * @创建人：yyx
 * @创建时间：16/12/15 下午8:29
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private TFloodRoleMapper floodRoleMapper;

    @Autowired
    private TFloodRoleTemplateMapper floodRoleTemplateMapper;

    @Autowired
    private OperRoleDao operRoleDao;

    /**
     * 验证在表内统一子系统下有无同名角色
     *
     * @param req
     * @return
     */
    private void validateRoleNameUnique(RoleReq req) {
        TFloodRoleExample example = new TFloodRoleExample();
        TFloodRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleNameEqualTo(req.getRoleName()).andUrmIdEqualTo(req.getUrmId());
        if (!ValidateUtils.isStrEmpty(req.getRoleId())) {
            criteria.andRoleIdNotEqualTo(req.getRoleId());
        }
        long count;
        try {
            count = floodRoleMapper.countByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (count > 0) {
            throw new BusinessException("角色信息已经存在");
        }
    }

    private void validateRoleReq(RoleReq req) {
        if (StringUtils.isBlank(req.getRoleName())) {
            throw new BusinessException("缺少【roleName】参数，不能新增角色");
        }
        if (StringUtils.isBlank(req.getUrmId())) {
            throw new BusinessException("缺少【urmId】参数，不能新增角色");
        }
        if (StringUtils.isBlank(req.getSystemId())) {
            throw new BusinessException("缺少【systemId】参数，不能新增角色");
        }
    }

    private TFloodRole validateRole(String roleId) {
        TFloodRole role = null;
        try {
            role = floodRoleMapper.selectByPrimaryKey(roleId);
        } catch (Exception e) {
            Logger.getLogger().error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (null == role) {
            throw new ParamValueException("没有对应的角色");
        }
        return role;
    }

    @Override
    @Transactional
    public String saveRoleTransactional(String userId, RoleReq req) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("缺少【userId】参数，不能新增角色");
        }
        validateRoleNameUnique(req);
        validateRoleReq(req);

        TFloodRole role = new TFloodRole();
        String roleId = UUIDGenerator.generate();
        BeanUtils.copyProperties(req, role);
        role.setRoleId(roleId);
        role.setRoleStatus("0");
        try {
            floodRoleMapper.insert(role);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return roleId;
    }

    @Override
    @Transactional
    public boolean updateRoleTransactional(String userId, RoleReq req) {
        if (ValidateUtils.isStrEmpty(req.getRoleId())) {
            throw new ParamValueException("缺少【roleId】参数");
        }
        validateRoleReq(req);
        validateRoleNameUnique(req);
        TFloodRole role = validateRole(req.getRoleId());

        BeanUtils.copyProperties(req, role, new String[] { "roleId", "urmId", "systemId", "roleStatus" });
        role.setLastupdatetime(Calendar.getInstance().getTime());
        role.setModifiedUser(userId);
        try {
            floodRoleMapper.updateByPrimaryKeySelective(role);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean deleteRoleTransactional(String userId, String roleId) {
        TFloodRole role = validateRole(roleId);
        role.setRoleStatus("1");
        role.setLastupdatetime(Calendar.getInstance().getTime());
        role.setModifiedUser(userId);
        try {
            floodRoleMapper.updateByPrimaryKeySelective(role);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public RoleRsp queryRoleById(String roleId) {
        TFloodRole role = validateRole(roleId);
        RoleRsp rsp = new RoleRsp();
        BeanUtils.copyProperties(role, rsp);
        return rsp;
    }

    @Override
    public PageInfo<RoleRsp> queryRoles(String roleName, String roleStatus, String urmId, String pRoleId, Integer page,
            Integer pageSize) {
        // 生成分页查询用的条件
        TFloodRoleExample example = new TFloodRoleExample();
        example.setOrderByClause("ROLE_ID");
        TFloodRoleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(roleName)) {
            criteria.andRoleNameLike(roleName);
        }
        if (StringUtils.isNotBlank(roleStatus)) {
            criteria.andRoleStatusEqualTo(roleStatus);
        }
        if (StringUtils.isNotBlank(urmId)) {
            criteria.andUrmIdEqualTo(urmId);
        }
        if ("0".equals(pRoleId)) {
            criteria.andPRoleIdIsNull();
        } else if ("1".equals(pRoleId)) {
            criteria.andPRoleIdIsNotNull();
        }
        //决定是否分页查询
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<RoleRsp> rsps = new ArrayList<>();
        List<TFloodRole> records = floodRoleMapper.selectByExampleWithRowbounds(example, rowBounds);
        CollectionUtils.copyCollections(records, rsps, RoleRsp.class);
        PageInfo<RoleRsp> pageInfo = new com.github.pagehelper.PageInfo<>(rsps);
        return pageInfo;
    }

    @Override
    public Boolean initRoles(String userId, String systemId, Integer coreId, Boolean isAdmin) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("缺少【userId】参数，不能初始化角色");
        }
        if (StringUtils.isBlank(systemId)) {
            throw new BusinessException("缺少【systemId】参数，不能初始化角色");
        }
        if ( null == coreId) {
            throw new BusinessException("缺少【coreId】参数，不能初始化角色");
        }
        try {
            Boolean flag = Boolean.FALSE;
            if( null!=isAdmin && isAdmin){
                flag = Boolean.TRUE;
            }
            TFloodRoleTemplateExample example = new TFloodRoleTemplateExample();
            TFloodRoleTemplateExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo((byte)0);
            if( flag ){
                criteria.andIdEqualTo(1);
            }else{
                criteria.andIdNotEqualTo(1);
            }
            List<TFloodRoleTemplate> templates = floodRoleTemplateMapper.selectByExample(example);
            templates.stream().filter(o -> o!=null).forEach(obj->{
                TFloodRole role = new TFloodRole();
                String roleId = UUIDGenerator.generate();
                role.setSystemId(systemId);
                role.setUrmId( String.valueOf(coreId) );
                role.setRoleName(obj.getTemplateName());
                role.setRoleDesc(obj.getTemplateName());
                role.setCreateTime(Calendar.getInstance().getTime());
                role.setRoleStatus( "0" );
                role.setTemplateId( obj.getId() );
                role.setRoleId( roleId );
                floodRoleMapper.insert( role );

            });
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public List<BatchOperRoleRsp> batchRoles(String systemId, List<Integer> operId) {
        if(StringUtils.isBlank(systemId)){
            throw new ParamValueException("缺少【systemId】参数,不能进行批量接口查询");
        }
        if(CollectionUtils.isEmpty(operId)){
            throw new ParamValueException("缺少【operId】参数,不能进行批量接口查询");
        }
        try{
            List<BatchOperRoleRsp> result = operRoleDao.batchRoles(systemId,operId);
            return  result;
        }catch (Exception e){
            logger.error(""+e);
            throw new BusinessException("数据库异常");
        }
    }

}
