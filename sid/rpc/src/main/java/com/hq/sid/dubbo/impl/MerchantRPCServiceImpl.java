package com.hq.sid.dubbo.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.github.pagehelper.PageInfo;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.HqBaseException;
import com.hq.scrati.common.exception.ParamException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.alipay.gateway.service.MerchantSettledService;
import com.hq.sid.dao.generate.TSidMercAliauthMapper;
import com.hq.sid.dubbo.MerchantRPCService;
import com.hq.sid.entity.generate.TSidMercAliauth;
import com.hq.sid.service.MercService;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.entity.request.*;
import com.hq.sid.service.entity.response.AliAuthResp;
import com.hq.sid.service.entity.response.MercResp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zale on 2016/12/30.
 */
@Service(version = "1.0",interfaceClass = MerchantRPCService.class)
public class MerchantRPCServiceImpl implements MerchantRPCService {
    private Logger logger = Logger.getLogger();
    @Autowired
    private MercService mercService;
    @Autowired
    private OpertorService opertorService;
    @Autowired
    private MerchantSettledService merchantSettledService;
    @Autowired
    private TSidMercAliauthMapper tSidMercAliauthMapper;
    @Override
    public MercResp mercRegister(HqRequest hqRequest) {
        try {
            MercReq req = JSON.parse(hqRequest.getBizContent(), MercReq.class);
            req.setOperId(hqRequest.getUserId());
            OperSearchReq operReq  = new OperSearchReq();
            operReq.setId(Integer.valueOf(hqRequest.getUserId()));
            if(req.getCoreId()==null&& com.hq.scrati.common.util.StringUtils.isBlank(req.getMercId())){
                return mercService.createMerc(req);
            }else{
               mercService.uptMerc(req);
               MercResp resp = new MercResp();
               BeanUtils.copyProperties(req,resp);
               return resp;
            }
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("商户进件失败");
        } catch (HqBaseException e) {
            logger.error(e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e);
            throw new BusinessException("商户进件失败");
        }
    }


    @Override
    public AliAuthResp exchangeAliAuthToken(HqRequest hqRequest) {
        try {
        ExchangeTokenReq req =  JSON.parse(hqRequest.getBizContent(), ExchangeTokenReq.class);
        if(StringUtils.isBlank(req.getAuth_code())|| req.getCore_id()==null){
            throw new ParamException("参数不正确");
        }
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\"" +req.getAuth_code()+"\""+
                "  }");
        AlipayOpenAuthTokenAppResponse aliResp = merchantSettledService.alipayOpenAuthToken(request);
        if("10000".equals(aliResp.getCode())) {
            TSidMercAliauth tSidMercAliauth = tSidMercAliauthMapper.selectByPrimaryKey(req.getCore_id());
            if (tSidMercAliauth == null) {
                tSidMercAliauth = new TSidMercAliauth();
            }

            tSidMercAliauth.setAppAuthToken(aliResp.getAppAuthToken());
            tSidMercAliauth.setAppRefreshToken(aliResp.getAppRefreshToken());
            tSidMercAliauth.setUserId(aliResp.getUserId());
            tSidMercAliauth.setAuthAppId(aliResp.getAuthAppId());
            if(aliResp.getExpiresIn()!=null) {
                tSidMercAliauth.setExpDate(new DateTime().plusSeconds(Integer.parseInt(aliResp.getExpiresIn())).toDate());
            }
            if(aliResp.getReExpiresIn()!=null) {
                tSidMercAliauth.setReExpDate(new DateTime().plusSeconds(Integer.parseInt(aliResp.getReExpiresIn())).toDate());
            }
            if (tSidMercAliauth.getCoreId() == null) {
                tSidMercAliauth.setCoreId(req.getCore_id());
                tSidMercAliauthMapper.insert(tSidMercAliauth);
            } else {
                tSidMercAliauthMapper.updateByPrimaryKeySelective(tSidMercAliauth);
            }
            AliAuthResp resp = new AliAuthResp();
            resp.setCoreId(tSidMercAliauth.getCoreId());
            resp.setAuthToken(aliResp.getAppAuthToken());
            return resp;
        }else{
            throw new BusinessException(aliResp.getCode(),aliResp.getSubCode()+"-"+aliResp.getSubMsg());
        }
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("交换商户token失败"+hqRequest);
        }
    }

    @Override
    public AliAuthResp refreshAliAuthToken(HqRequest hqRequest) {
        try {
            RefreshTokenReq req =  JSON.parse(hqRequest.getBizContent(), RefreshTokenReq.class);
            TSidMercAliauth tSidMercAliauth = tSidMercAliauthMapper.selectByPrimaryKey(req.getCoreId());
            if(tSidMercAliauth == null|| StringUtils.isBlank(tSidMercAliauth.getAppRefreshToken())){
                throw new BusinessException("无此商户授权信息"+hqRequest);
            }
            AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
            request.setBizContent("{" +
                    "    \"grant_type\":\"authorization_code\"," +
                    "    \"refresh_token\":\"" +tSidMercAliauth.getAppRefreshToken()+"\""+
                    "  }");
            AlipayOpenAuthTokenAppResponse aliResp = merchantSettledService.alipayOpenAuthToken(request);
            if("10000".equals(aliResp.getCode())) {
                tSidMercAliauth.setAppAuthToken(aliResp.getAppAuthToken());
                tSidMercAliauth.setAppRefreshToken(aliResp.getAppRefreshToken());
                tSidMercAliauth.setUserId(aliResp.getUserId());
                tSidMercAliauth.setAuthAppId(aliResp.getAuthAppId());
                if(aliResp.getExpiresIn()!=null) {
                    tSidMercAliauth.setExpDate(new DateTime().plusSeconds(Integer.parseInt(aliResp.getExpiresIn())).toDate());
                }
                if(aliResp.getReExpiresIn()!=null) {
                    tSidMercAliauth.setReExpDate(new DateTime().plusSeconds(Integer.parseInt(aliResp.getReExpiresIn())).toDate());
                }
                tSidMercAliauthMapper.updateByPrimaryKeySelective(tSidMercAliauth);
                AliAuthResp resp = new AliAuthResp();
                resp.setCoreId(tSidMercAliauth.getCoreId());
                resp.setAuthToken(aliResp.getAppAuthToken());
                return resp;
            }else{
                throw new BusinessException(aliResp.getCode(),aliResp.getSubCode()+"-"+aliResp.getSubMsg());
            }
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("刷新商户token失败"+hqRequest);
        }
    }

    @Override
    public MercResp getMercInfo(HqRequest hqRequest) {
        try {
            MercSearchReq req = JSON.parse(hqRequest.getBizContent(), MercSearchReq.class);
            if(req.getCoreId()!=null){
                return mercService.getMercByCoreId(req.getCoreId());
            }
            if(req.getMercId()!=null) {
                return mercService.getMercByMercId(req.getMercId());
            }

        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("查询商户信息失败");
        }
        throw new BusinessException("没有找到对应的商户信息");
    }
    @Override
    public PageInfo<MercResp> getMercList(HqRequest hqRequest){
        try {
            MercListSearchReq listSearchReq = JSON.parse(hqRequest.getBizContent(), MercListSearchReq.class);
            MercSearchReq req = listSearchReq.getReq();
            if(req==null){
                req = new MercSearchReq();
            }
            Integer page = listSearchReq.getPage();
            if(page==null){
                page=0;
            }
            Integer pageSize = listSearchReq.getPageSize();
            if(pageSize == null){
                pageSize=20;
            }

            return mercService.getMercList(req,page,pageSize);
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinessException("查询商户列表失败");
        }
    }
}
