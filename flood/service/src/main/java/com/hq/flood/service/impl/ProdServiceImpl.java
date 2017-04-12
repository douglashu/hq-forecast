package com.hq.flood.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.flood.dao.generate.TFloodPrdMapper;
import com.hq.flood.entity.generate.TFloodPrd;
import com.hq.flood.entity.generate.TFloodPrdExample;
import com.hq.flood.service.ProdService;
import com.hq.flood.service.entity.request.ProdReq;
import com.hq.flood.service.entity.response.ProdRsp;
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
 * @创建时间：16/12/15 下午8:28
 */
@Service
public class ProdServiceImpl implements ProdService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private TFloodPrdMapper prdMapper;

    private void validatePrdNameUnique(ProdReq req) {
        TFloodPrdExample example = new TFloodPrdExample();
        TFloodPrdExample.Criteria criteria = example.createCriteria();
        criteria.andPrdNameEqualTo(req.getPrdName());
        if (!ValidateUtils.isStrEmpty(req.getPrdCode())) {
            criteria.andPrdCodeNotEqualTo(req.getPrdCode());
        }
        long count;
        try {
            count = prdMapper.countByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (count > 0) {
            throw new BusinessException("产品信息已经存在");
        }
    }

    private void validatePrdReq(ProdReq req) {
        if (StringUtils.isBlank(req.getPrdName())) {
            throw new BusinessException("缺少【prdName】参数，不能新增产品");
        }
    }

    private TFloodPrd validatePrd(String prdId) {
        TFloodPrd prd = null;
        try {
            prd = prdMapper.selectByPrimaryKey(prdId);
        } catch (Exception e) {
            Logger.getLogger().error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (null == prd) {
            throw new ParamValueException("没有对应的产品");
        }
        return prd;
    }

    @Override
    @Transactional
    public boolean saveProductTransactional(String userId, ProdReq req) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("缺少【userId】参数，不能新增产品");
        }
        validatePrdNameUnique(req);
        validatePrdReq(req);

        TFloodPrd prd = new TFloodPrd();
        BeanUtils.copyProperties(req, prd);
        prd.setStatus("0");
        prd.setGmtCreate(Calendar.getInstance().getTime());
        prd.setCreateUserId(userId);
        try {
            prdMapper.insert(prd);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean updateProductTransactional(String userId, ProdReq req) {
        if (ValidateUtils.isStrEmpty(req.getPrdCode())) {
            throw new ParamValueException("缺少【prdCode】参数");
        }
        validatePrdReq(req);
        validatePrdNameUnique(req);
        TFloodPrd prd = validatePrd(req.getPrdCode());

        BeanUtils.copyProperties(req, prd, new String[] { "prdCode", "status" });
        prd.setGmtModified(Calendar.getInstance().getTime());
        prd.setModifiedUserId(userId);
        try {
            prdMapper.updateByPrimaryKeySelective(prd);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean deleteProductTransactional(String userId, String prdCode) {
        TFloodPrd prd = validatePrd(prdCode);
        prd.setStatus("1");
        prd.setGmtModified(Calendar.getInstance().getTime());
        prd.setModifiedUserId(userId);
        try {
            prdMapper.updateByPrimaryKeySelective(prd);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public ProdRsp queryProductById(String prdCode) {
        TFloodPrd role = validatePrd(prdCode);
        ProdRsp rsp = new ProdRsp();
        BeanUtils.copyProperties(role, rsp);
        return rsp;
    }

    @Override
    public PageInfo<ProdRsp> queryProductList(String prdName, Integer page, Integer pageSize) {
        // 生成分页查询用的条件
        TFloodPrdExample example = new TFloodPrdExample();
        example.setOrderByClause("PRD_CODE");
        TFloodPrdExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(prdName)) {
            criteria.andPrdNameLike(prdName);
        }
        //决定是否分页查询
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<ProdRsp> rsps = new ArrayList<>();
        List<TFloodPrd> records = prdMapper.selectByExampleWithRowbounds(example, rowBounds);
        CollectionUtils.copyCollections(records, rsps, ProdRsp.class);
        PageInfo<ProdRsp> pageInfo = new com.github.pagehelper.PageInfo<>(rsps);
        return pageInfo;
    }
}
