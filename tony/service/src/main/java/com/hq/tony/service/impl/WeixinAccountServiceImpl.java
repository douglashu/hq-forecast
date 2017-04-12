package com.hq.tony.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.*;
import com.hq.tony.dao.generate.WeixinAccountMapper;
import com.hq.tony.entity.generate.WeixinAccount;
import com.hq.tony.entity.generate.WeixinAccountExample;
import com.hq.tony.service.WeixinAccountService;
import com.hq.tony.service.entity.request.WeixinAccountReq;
import com.hq.tony.service.entity.response.WeixinAccountRsp;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名称：com.hq.tony.service.impl
 * @创建人：yyx
 * @创建时间：17/3/4 下午3:08
 */
@Service
public class WeixinAccountServiceImpl implements WeixinAccountService{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private WeixinAccountMapper weixinAccountMapper;


    private void valiateReq(WeixinAccountReq req) {
        if(StringUtils.isBlank(req.getAccountname())){
            throw new ParamValueException("参数【accountname】不能为空");
        }
        if(StringUtils.isBlank(req.getAccountaccesstoken())){
            throw new ParamValueException("参数【accountaccesstoken】不能为空");
        }
        if(StringUtils.isBlank(req.getAccountnumber())){
            throw new ParamValueException("参数【accountnumber】不能为空");
        }
        if(StringUtils.isBlank(req.getAccounttype())){
            throw new ParamValueException("参数【accounttype】不能为空");
        }
        if(StringUtils.isBlank(req.getAccountemail())){
            throw new ParamValueException("参数【accountemail】不能为空");
        }
        if(StringUtils.isBlank(req.getAccountappid())){
            throw new ParamValueException("参数【accountappid】不能为空");
        }
        if(StringUtils.isBlank(req.getAccountappsecret())){
            throw new ParamValueException("参数【accountappsecret】不能为空");
        }
    }
    @Override
    @Transactional
    public Boolean insert(WeixinAccountReq req) {
        valiateReq(req);
        WeixinAccount account = new WeixinAccount();
        BeanUtils.copyProperties(req,account);
        try{
            account.setId(UUIDGenerator.generate());
            weixinAccountMapper.insert(account);
        }catch (Exception e){
            logger.error(">>>插入微信公众帐号信息失败");
            throw new BusinessException("数据库异常");
        }

        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean update(WeixinAccountReq req) {
        valiateReq(req);
        WeixinAccount account = valiateAccount(req.getId());
        BeanUtils.copyProperties( req,account,new String[]{"id","addtoekntime"} );
        try{
            weixinAccountMapper.updateByPrimaryKeySelective(account);
        }catch (Exception e){
            logger.error(">>>更新微信公众帐号信息失败");
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    private WeixinAccount valiateAccount(String id) {
        if( null == id ){
            throw new ParamValueException("参数【id】不能为空");
        }
        WeixinAccount account = weixinAccountMapper.selectByPrimaryKey(id);
        if( null == account ){
            throw new BusinessException("不存在对应的微信公众号信息");
        }
        return account;
    }

    @Override
    @Transactional
    public Boolean delete(String id) {
        WeixinAccount account = valiateAccount(id);
        try{
            weixinAccountMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            logger.error(">>>删除微信公众帐号信息失败");
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public WeixinAccountRsp findById(String id) {
        WeixinAccount account = valiateAccount(id);
        WeixinAccountRsp rsp = new WeixinAccountRsp();
        BeanUtils.copyProperties( account,rsp );
        return rsp;
    }

    @Override
    public PageInfo<WeixinAccountRsp> pages(String accountName, String accountToken, String accountAppid, Integer page, Integer pageSize) {
        WeixinAccountExample example = new WeixinAccountExample();
        WeixinAccountExample.Criteria criteria = example.createCriteria();
        if (!ValidateUtils.isStrEmpty(accountName)) {
            criteria.andAccountnameLike("%" + accountName + "%");
        }
        if (!ValidateUtils.isStrEmpty(accountToken)) {
            criteria.andAccounttokenLike("%" + accountToken + "%");
        }
        if (!ValidateUtils.isStrEmpty(accountAppid)) {
            criteria.andAccountappidLike("%" + accountAppid + "%");
        }
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<WeixinAccountRsp> rsps = new ArrayList<>();
        List<WeixinAccount> records = weixinAccountMapper.selectByExampleWithRowbounds(example, rowBounds);
        CollectionUtils.copyCollections(records, rsps, WeixinAccountRsp.class);
        PageInfo<WeixinAccountRsp> pageInfo = new com.github.pagehelper.PageInfo<>(rsps);
        return pageInfo;
    }
}
