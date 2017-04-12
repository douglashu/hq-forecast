package com.hq.sid.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.*;
import com.hq.sid.dao.generate.TSidCodeMapper;
import com.hq.sid.dao.generate.TSidCodemouldMapper;
import com.hq.sid.entity.generate.TSidCode;
import com.hq.sid.entity.generate.TSidCodeExample;
import com.hq.sid.entity.generate.TSidCodemould;
import com.hq.sid.service.MercService;
import com.hq.sid.service.QRCodeGenerationService;
import com.hq.sid.service.QrCodeService;
import com.hq.sid.service.entity.enums.QrCodeType;
import com.hq.sid.service.entity.request.QrCodeReq;
import com.hq.sid.service.entity.response.MercResp;
import com.hq.sid.service.entity.response.QrCodeRsp;
import com.hq.sid.service.fs.FileStoreService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @包名称：com.hq.sid.service.impl
 * @创建人：yyx
 * @创建时间：17/1/26 下午3:11
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Value("${file.remote.location}")
    private String filepath;

    @Value("${qrcode.remote.location}")
    private String qrCodePath;

    @Autowired
    private TSidCodeMapper codeMapper;

    @Autowired
    private MercService mercService;

    @Autowired
    private TSidCodemouldMapper sidCodemouldMapper;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private QRCodeGenerationService qrCodeGenerationService;

    private Logger logger = Logger.getLogger();

    private static final Map<String, QrCodeType> lookup = new HashMap<String, QrCodeType>();

    static {
        for (QrCodeType codeType : EnumSet.allOf(QrCodeType.class)) {
            lookup.put(codeType.getType(), codeType);
        }
    }

    @Override
    @Transactional
    public boolean insertQrCodeTransactional(QrCodeReq req) {
        validateReq(req);
        TSidCode sidCode = new TSidCode();
        BeanUtils.copyProperties(req, sidCode);
        sidCode.setCreateTime(Calendar.getInstance().getTime());
        sidCode.setState("S");
        try {
            codeMapper.insert(sidCode);
            Integer id = sidCode.getId();
            sidCode.setUrl( qrCodePath.replaceAll("\\{\\{id\\}\\}", id.toString()) );

            if( null!=req.getTemplateId() ) {
                TSidCodemould mould = sidCodemouldMapper.selectByPrimaryKey(req.getTemplateId());
                if (null != mould && StringUtils.isNotBlank(mould.getImage())) {
                    try {
                        // URL qrcodeUrl = new URL(req.getUrl());
                        InputStream qrCodeStream = qrCodeGenerationService.generateImageReInputStream(sidCode.getUrl(),sidCode.getWidth(),sidCode.getHeight(),"JPG");

                        URL mouldUrl = new URL(mould.getImage());
                        InputStream inputStream = ImageComposeHandleUtil
                                .composeImgToByteArrayInputStream(mouldUrl, qrCodeStream, null != mould.getOffsetx() ? mould.getOffsetx() : 0,
                                        null != mould.getOffsety() ? mould.getOffsety() : 0);
                        String fileName = req.getName()+Calendar.getInstance().getTimeInMillis()+mould.getImage().substring(
                                mould.getImage().lastIndexOf("."));
                        fileStoreService.saveFile( inputStream , fileName);

                        String composeUrl = filepath + fileName;
                        sidCode.setComposeUrl( composeUrl );

                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                        logger.error("" + e1);
                        throw new BusinessException("二维码Url地址错误");
                    } catch (IOException ie) {
                        ie.printStackTrace();
                        logger.error("" + ie);
                        throw new BusinessException("合成图片异常");
                    }
                }
                codeMapper.updateByPrimaryKeySelective(sidCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.fillInStackTrace());
            throw new BusinessException("添加二维码信息数据库异常");
        }
        return Boolean.TRUE;
    }

    private void validateReq(QrCodeReq req) {
        if( null == req ){
            throw new ParamValueException("请求参数为空");
        }
        if( StringUtils.isBlank(req.getName() )){
            throw new ParamValueException("参数【name】不能为空");
        }
        if( StringUtils.isBlank(req.getType() )){
            throw new ParamValueException("参数【type】不能为空");
        }
        if( null == req.getCoreId() ){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        if( null == req.getWidth() ){
            throw new ParamValueException("参数【width】不能为空");
        }
        if( null == req.getWidth() ){
            throw new ParamValueException("参数【height】不能为空");
        }

        if (!lookup.containsKey(req.getType())) {
            throw new ParamValueException("二维码类型错误");
        }
    }

    @Override
    @Transactional
    public boolean updateQrCodeTransactional(QrCodeReq req) {
        validateReq(req);
        if (null == req.getId()) {
            throw new ParamValueException("二维码编码不能为空");
        }
        TSidCode sidCode = codeMapper.selectByPrimaryKey( req.getId() );
        BeanUtils.copyProperties(req, sidCode, new String[] { "id", "coreId" ,"url","createTime","width","height"});
        sidCode.setUpdateTime(Calendar.getInstance().getTime());
        try {
            if( null!= req.getTemplateId() ){
                if(  (null!=sidCode.getTemplateId() && req.getTemplateId() != sidCode.getTemplateId()) || (req.getWidth() != sidCode.getWidth()) || (req.getHeight() != sidCode.getHeight())){
                    // 删除原始合成图片
                    if( StringUtils.isNotBlank(sidCode.getComposeUrl() )){
                        String removeFileName = sidCode.getComposeUrl().substring(sidCode.getComposeUrl().lastIndexOf("/")+1);
                        fileStoreService.remove(removeFileName);
                    }

                    TSidCodemould mould = sidCodemouldMapper.selectByPrimaryKey(req.getTemplateId());
                    if (null != mould && StringUtils.isNotBlank(mould.getImage())) {
                        try {
                            // URL qrcodeUrl = new URL(req.getUrl());
                            InputStream qrCodeStream = qrCodeGenerationService.generateImageReInputStream(sidCode.getUrl(),req.getWidth(),req.getHeight(),"JPG");

                            URL mouldUrl = new URL(mould.getImage());
                            InputStream inputStream = ImageComposeHandleUtil
                                    .composeImgToByteArrayInputStream(mouldUrl, qrCodeStream, null != mould.getOffsetx() ? mould.getOffsetx() : 0,
                                            null != mould.getOffsety() ? mould.getOffsety() : 0);
                            String fileName = req.getName()+Calendar.getInstance().getTimeInMillis()+mould.getImage().substring(
                                    mould.getImage().lastIndexOf("."));
                            fileStoreService.saveFile( inputStream , fileName);

                            String composeUrl = filepath + fileName;
                            sidCode.setComposeUrl( composeUrl );

                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                            logger.error("" + e1);
                            throw new BusinessException("二维码Url地址错误");
                        } catch (IOException ie) {
                            ie.printStackTrace();
                            logger.error("" + ie);
                            throw new BusinessException("合成图片异常");
                        }
                    }
                }
            }else{
                if( null !=(sidCode.getTemplateId()) ){
                    // 删除原始合成图片
                    if( StringUtils.isNotBlank(sidCode.getComposeUrl() )){
                        String removeFileName = sidCode.getComposeUrl().substring(sidCode.getComposeUrl().lastIndexOf("/")+1);
                        fileStoreService.remove(removeFileName);
                    }
                }
            }
            sidCode.setWidth( req.getWidth() );
            sidCode.setHeight( req.getHeight() );
            codeMapper.updateByPrimaryKeySelective(sidCode);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("更新二维码信息数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean deleteQrCodeTransactional(Integer codeId) {
        if (null == codeId) {
            throw new ParamValueException("二维码编码不能为空");
        }
        validateSidCode(codeId);
        try {
            codeMapper.deleteByPrimaryKey(codeId);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    public TSidCode validateSidCode(Integer codeId) {
        if (codeId == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "二维码id不能为空");
        }
        return this.codeMapper.selectByPrimaryKey(codeId);
    }

    @Override
    public QrCodeRsp queryById(Integer codeId) {
        TSidCode sidCode = validateSidCode(codeId);
        if (sidCode == null) return null;
        QrCodeRsp rsp = new QrCodeRsp();
        BeanUtils.copyProperties(sidCode, rsp);
        MercResp mercResp = this.mercService.getMercByCoreId(sidCode.getCoreId());
        if (mercResp != null) {
            rsp.setMchId(mercResp.getMercId());
            rsp.setMchName(mercResp.getMercName());
            rsp.setMchShortName(mercResp.getMercShotName());
            rsp.setStoreName(mercResp.getShopName());
            rsp.setLogo(mercResp.getLogoPic());
        }
        return rsp;
    }

    @Override
    public PageInfo<QrCodeRsp> queryByCondition(Integer coreId
            , String name, String type, Integer page, Integer pageSize) {
        TSidCodeExample sidCodeExample = new TSidCodeExample();
        TSidCodeExample.Criteria criteria = sidCodeExample.createCriteria();
        if (coreId != null) criteria.andCoreIdEqualTo(coreId);
        if (!ValidateUtils.isStrEmpty(name)) criteria.andNameLike("%" + name + "%");
        if (!ValidateUtils.isStrEmpty(type)) criteria.andTypeEqualTo(type);
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<QrCodeRsp> rsps = new ArrayList<>();
        List<TSidCode> records = this.codeMapper.selectByExampleWithRowbounds(sidCodeExample, rowBounds);
        CollectionUtils.copyCollections(records, rsps, QrCodeRsp.class);
        PageInfo<QrCodeRsp> pageInfo = new com.github.pagehelper.PageInfo<>(rsps);
        return pageInfo;
    }

    @Override
    @Transactional
    public Boolean updateQrCodeState(Integer codeId,String state) {
        if (null == codeId) {
            throw new ParamValueException("二维码编码不能为空");
        }
        if( StringUtils.isBlank( state )){
            throw new ParamValueException("二维码状态不能为空");
        }
        TSidCode sidCode = validateSidCode(codeId);
        if (sidCode != null){
            sidCode.setUpdateTime(Calendar.getInstance().getTime());
            sidCode.setState( state );
            try {
                codeMapper.updateByPrimaryKeySelective(sidCode);
            } catch (Exception e) {
                logger.error(e.fillInStackTrace());
                throw new BusinessException("添加二维码信息数据库异常");
            }
            return Boolean.TRUE;
        }else{
            throw new ParamValueException("二维码信息不存在");
        }
    }

    public static void main(String[] args) {
        String s = "http://m.hqast.com/sylvia/rs?qr={{id}}";
        Integer id =1000201;
        System.out.println(s.replaceAll("\\{\\{id\\}\\}", id.toString()));
    }
}
