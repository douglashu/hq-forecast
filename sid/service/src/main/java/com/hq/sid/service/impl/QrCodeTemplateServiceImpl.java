package com.hq.sid.service.impl;

import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.sid.dao.generate.TSidCodemouldMapper;
import com.hq.sid.entity.generate.TSidCodemould;
import com.hq.sid.entity.generate.TSidCodemouldExample;
import com.hq.sid.service.QrCodeTemplateService;
import com.hq.sid.service.entity.request.QrCodeTemplateReq;
import com.hq.sid.service.entity.response.QrCodeTemplateRsp;
import com.hq.sid.service.fs.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @包名称：com.hq.sid.service.impl
 * @创建人：yyx
 * @创建时间：17/2/13 下午10:12
 */
@Service
public class QrCodeTemplateServiceImpl implements QrCodeTemplateService{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private TSidCodemouldMapper sidCodemouldMapper;

    @Autowired
    private FileStoreService fileStoreService;

    @Value("${file.local.location}")
    private String filepath;


    @Override
    @Transactional
    public boolean insert(QrCodeTemplateReq req) {
        validateReq(req);
        TSidCodemould sidCodemould = new TSidCodemould();
        BeanUtils.copyProperties(req, sidCodemould);
        try {
//            fileStoreService.saveFile( new File(req.getImage()) , "template"+req.getImage().substring(
//                    req.getImage().lastIndexOf(".")));

            sidCodemould.setImage(filepath+"template"+req.getImage().substring(
                    req.getImage().lastIndexOf(".")));
            sidCodemouldMapper.insert(sidCodemould);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.fillInStackTrace());
            throw new BusinessException("添加二维码模版信息数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public List<QrCodeTemplateRsp> queryAll() {
        try {
            List<TSidCodemould> list = sidCodemouldMapper.selectByExample(new TSidCodemouldExample());
            List<QrCodeTemplateRsp> rsp = new ArrayList<>();
            CollectionUtils.copyCollections(list,rsp,QrCodeTemplateRsp.class);
            return rsp;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.fillInStackTrace());
            throw new BusinessException("获取二维码模版信息数据库异常");
        }
    }

    private void validateReq(QrCodeTemplateReq req) {
        if( null == req ){
            throw new ParamValueException("请求参数为空");
        }
        if( StringUtils.isBlank(req.getName() )){
            throw new ParamValueException("参数【name】为空");
        }
        if( StringUtils.isBlank(req.getImage() )){
            throw new ParamValueException("参数【image】为空");
        }
        if( null == req.getOffsetx()){
            throw new ParamValueException("参数【offsetx】为空");
        }
        if( null == req.getOffsety()){
            throw new ParamValueException("参数【offsety】为空");
        }
    }
}
