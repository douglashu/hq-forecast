package com.hq.sid.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.flood.service.entity.request.RoleInitReq;
import com.hq.scrati.common.enums.SystemEnum;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.FrameworkInvoker;
import com.hq.scrati.framework.IDGenerator;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dao.generate.TSidCoreMapper;
import com.hq.sid.dao.generate.TSidMercAliauthMapper;
import com.hq.sid.dao.generate.TSidMercMapper;
import com.hq.sid.dao.generate.TSidMercPicMapper;
import com.hq.sid.entity.generate.*;
import com.hq.sid.service.MercService;
import com.hq.sid.service.constant.SidConstants;
import com.hq.sid.service.entity.request.MercReq;
import com.hq.sid.service.entity.request.MercSearchReq;
import com.hq.sid.service.entity.response.MercResp;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zale on 2016/12/13.
 */
@Service
public class MercServiceImpl implements MercService {
    @Autowired
    private TSidMercMapper tSidMercMapper;
    @Autowired
    private IDGenerator idGenerator;
    @Autowired
    private TSidCoreMapper tSidCoreMapper;
    @Autowired
    private TSidMercPicMapper tSidMercPicMapper;
    @Autowired
    private TSidMercAliauthMapper tSidMercAliauthMapper;
    @Autowired
    private FrameworkInvoker frameworkInvoker;
    @Override
    @Transactional
    public MercResp createMerc(MercReq req) throws Exception {
        //step1验证
        //待补充 商户名称，营业地址不能为空
        if(StringUtils.isBlank(req.getMercName())){
            throw new BusinessException("商户名称不能为空");
        }
        if(StringUtils.isBlank(req.getBusiLicenseCity())){
            throw new BusinessException("营业地址不能为空");
        }
        if(req.getMercType() == null){
            throw new BusinessException("末知企业类型");
        }
        //step1 end;
        //step2 插入core表
        Date now = new Date();
        TSidMerc merc = new TSidMerc();
        BeanUtils.copyProperties(req,merc);
        TSidCore tSidCore = new TSidCore();
        tSidCore.setName(req.getMercName());
        tSidCore.setType(SidConstants.CoreType.MERCHANT.getValue());
        tSidCore.setCrtOperId(Integer.valueOf(req.getOperId()));
        tSidCore.setCrtTime(now);
        tSidCoreMapper.insertSelective(tSidCore);
        //step2 end;
        //step3 插入商户表
        merc.setCoreId(tSidCore.getId());
        merc.setMercId(idGenerator.generateMerchantNo(req.getBusiLicenseCity().substring(0,4)));
        tSidMercMapper.insertSelective(merc);
        //step3 end;
        //step4 处理图片
        req.setCoreId(merc.getCoreId());
        savePics(req);
        //step4 end;
        //step5 初始化角色
        HqRequest request = new HqRequest();
        request.setUserId(req.getOperId());
        RoleInitReq roleInitReq = new RoleInitReq();
        roleInitReq.setCoreId(merc.getCoreId());
        roleInitReq.setIsAdmin(false);
        roleInitReq.setSystemId(SystemEnum.APP.getCode());

        request.setBizContent(JSON.toJSONString(roleInitReq));
        Map<String, Object> params = new HashMap<>();
        params.put("request",request);
        RespEntity respEntity = (RespEntity) frameworkInvoker.invoke("flood.role.init","1.0",params);
        if(!"0000".equals(respEntity.getKey())){
            throw new BusinessException("初始化角色异常" + respEntity.getMsg());
        }
        //step5 end
        MercResp resp = new MercResp();
        resp.setMercId(merc.getMercId());
        resp.setCoreId(tSidCore.getId());
        return resp;
    }

    @Override
    @Transactional
    public void uptMerc(MercReq req) {
        TSidMerc merc = new TSidMerc();
        BeanUtils.copyProperties(req,merc);
        //step1 把不能更新字段设空
        merc.setMercId(null);
        //待补充
        //step1 end;
        //step 2验证
        if(merc.getCoreId()==null){
            throw new BusinessException("core Id不能为空");
        }
        //step2 end;
        //step3 更新
        tSidMercMapper.updateByPrimaryKeySelective(merc);
        //step3 end;
        //step4 处理图片
        savePics(req);
        //step4 end;

    }

    private void savePics(MercReq req) {
        Integer coreId = req.getCoreId();
        if(req.getSpecialLicensePic()!=null&&req.getSpecialLicensePic().size()>0){
            req.getSpecialLicensePic().forEach(pic ->{tSidMercPicMapper.insert(buildSidMercPic(coreId,pic,SidConstants.PICType.SPECIAL_LICENSE));});
        }
        if(StringUtils.isNotBlank(req.getLogoPic())){
            dropExit(coreId,SidConstants.PICType.LOGO);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getLogoPic(), SidConstants.PICType.LOGO));
        }
        if(StringUtils.isNotBlank(req.getOrgPic())){
            dropExit(coreId, SidConstants.PICType.ORG_CODE_CERTIFICATE);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getOrgPic(), SidConstants.PICType.ORG_CODE_CERTIFICATE));
        }
        if(StringUtils.isNotBlank(req.getBusiAuthPic())){
            dropExit(coreId,SidConstants.PICType.BUSI_LICENSE_AUTH);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getBusiAuthPic(), SidConstants.PICType.BUSI_LICENSE_AUTH));
        }
        if(StringUtils.isNotBlank(req.getBusiLicensePic())){
            dropExit(coreId, SidConstants.PICType.BUSI_LICENSE);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getBusiLicensePic(), SidConstants.PICType.BUSI_LICENSE));
        }
        if(StringUtils.isNotBlank(req.getShopBoardPic())){
            dropExit(coreId, SidConstants.PICType.SHOP_BOARD);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getShopBoardPic(), SidConstants.PICType.SHOP_BOARD));
        }
        if(StringUtils.isNotBlank(req.getWebAuthPic())){
            dropExit(coreId, SidConstants.PICType.WEB_AUTH);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getWebAuthPic(), SidConstants.PICType.WEB_AUTH));
        }
        if(StringUtils.isNotBlank(req.getLegalPersonCertPicB())){
            dropExit(coreId, SidConstants.PICType.LEGAL_PERSON_CERT_BACK);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getLegalPersonCertPicB(), SidConstants.PICType.LEGAL_PERSON_CERT_BACK));
        }
        if(StringUtils.isNotBlank(req.getLegalPersonCertPicF())){
            dropExit(coreId, SidConstants.PICType.LEGAL_PERSON_CERT_FRONT);
            tSidMercPicMapper.insert(buildSidMercPic(coreId, req.getLegalPersonCertPicF(), SidConstants.PICType.LEGAL_PERSON_CERT_FRONT));
        }
        if(req.getShopScenePic()!=null&&req.getShopScenePic().size()>0){
            req.getShopScenePic().forEach(pic ->{tSidMercPicMapper.insert(buildSidMercPic(coreId,pic,SidConstants.PICType.SHOP_SCENE));});
        }
    }

    private void dropExit(Integer coreId, SidConstants.PICType logo) {
        TSidMercPicExample example = new TSidMercPicExample();
        example.createCriteria().andCoreIdEqualTo(coreId).andTypeEqualTo(logo.getValue());
        tSidMercPicMapper.deleteByExample(example);

    }

    private TSidMercPic buildSidMercPic(Integer coreId, String pic, SidConstants.PICType specialLicense) {
        if(coreId == null){
            throw new BusinessException("coreId 不能为空");
        }
        if(StringUtils.isBlank(pic)){
            throw new BusinessException("图片名称不能为空");
        }
        if(specialLicense == null){
            throw new BusinessException("图片类型不能为空");
        }
        TSidMercPic tSidMercPic = new TSidMercPic();
        tSidMercPic.setCoreId(coreId);
        tSidMercPic.setPic(pic);
        tSidMercPic.setType(specialLicense.getValue());
        return tSidMercPic;
    }

    @Override
    public MercResp getMercByCoreId(Integer coreId) {
        TSidMerc merc = this.tSidMercMapper.selectByPrimaryKey(coreId);
        if (merc != null) {
            MercResp resp = buildMercResp(merc);
            return resp;
        }
        return null;
    }

    private MercResp buildMercResp(TSidMerc merc) {
        MercResp resp = new MercResp();
        BeanUtils.copyProperties(merc,resp);
        return buildMercResp(resp);

    }
    @Override
    public MercResp getMercByMercId(String mercId){
        MercSearchReq req = new MercSearchReq();
        req.setMercId(mercId);
        PageInfo<MercResp> pageinfo = getMercList(req, 1, 1);
        List<MercResp> list = pageinfo.getList();
        if(list!=null&&list.size()>0){
            MercResp merc = list.get(0);
            return buildMercResp(merc);
        }
        return null;
    }

    private MercResp buildMercResp(MercResp resp) {
        TSidMercPicExample example = new TSidMercPicExample();
        example.createCriteria().andCoreIdEqualTo(resp.getCoreId());
        //step1 end
        //step2 获得图片信息
        List<TSidMercPic> pics = tSidMercPicMapper.selectByExample(example);
        pics.forEach(pic ->{
            String picName = pic.getPic();
            switch (SidConstants.PICType.parse(pic.getType())){
                case BUSI_LICENSE:
                    resp.setBusiLicensePic(picName);
                    break;
                case BUSI_LICENSE_AUTH:
                    resp.setBusiAuthPic(picName);
                    break;
                case LEGAL_PERSON_CERT_BACK:
                    resp.setLegalPersonCertPicB(picName);
                    break;
                case LEGAL_PERSON_CERT_FRONT:
                    resp.setLegalPersonCertPicF(picName);
                    break;
                case LOGO:
                    resp.setLogoPic(picName);
                    break;
                case ORG_CODE_CERTIFICATE:
                    resp.setOrgPic(picName);
                    break;
                case SHOP_BOARD:
                    resp.setShopBoardPic(picName);
                    break;
                case SHOP_SCENE:
                    if(resp.getShopScenePic()==null){
                        resp.setShopScenePic(new ArrayList<>());
                    }
                    resp.getShopScenePic().add(picName);
                    break;
                case SPECIAL_LICENSE:
                    if(resp.getSpecialLicensePic()==null){
                        resp.setSpecialLicensePic(new ArrayList<>());
                    }
                    resp.getSpecialLicensePic().add(picName);
                    break;
                case WEB_AUTH:
                    resp.setWebAuthPic(picName);
                    break;
            }
        });
        //step2 end;
        //step3 支付信息
        TSidMercAliauth aliauth = tSidMercAliauthMapper.selectByPrimaryKey(resp.getCoreId());
        if(aliauth!=null){
            resp.setAliAuthToken(aliauth.getAppAuthToken());
            resp.setAliAuthAppId(aliauth.getAuthAppId());
            resp.setAliUserId(aliauth.getUserId());
        }
        //step3 end;
        return resp;
    }

    @Override
    public PageInfo<MercResp> getMercList(MercSearchReq req, int pageSize, int page) {
        TSidMercExample mercExample = new TSidMercExample();
        TSidMercExample.Criteria criteria = mercExample.createCriteria();
        if(req.getPcoreId()!=null){
            criteria.andPcoreIdEqualTo(req.getPcoreId());
        }
        if(req.getMercId()!=null){
            criteria.andMercIdEqualTo(req.getMercId());
        }
        if(req.getMercShotName() != null){
        	criteria.andMercShotNameLike("%" + req.getMercShotName() + "%");
        }
        if(req.getMercName() != null){
        	criteria.andMercNameLike("%" + req.getMercName() + "%");
        }
        if(req.getBusiLicenseNo() != null){
        	criteria.andBusiLicenseNoLike("%" + req.getBusiLicenseNo() + "%");
        }
        if(req.getContactName()!=null){
            criteria.andContactNameEqualTo(req.getContactName());
        }
        if(req.getContactMobile()!=null){
            criteria.andContactMobileEqualTo(req.getContactMobile());
        }
        //待补充其他查询条件

        List<TSidMerc> list = tSidMercMapper.selectByExampleWithRowbounds(mercExample,new RowBounds(page,pageSize));
        List<MercResp> rsps = new ArrayList<>();
        CollectionUtils.copyCollections(list,rsps,MercResp.class);
        PageInfo<MercResp> pageInfo = new PageInfo<>(rsps);
        return pageInfo;
    }
}
