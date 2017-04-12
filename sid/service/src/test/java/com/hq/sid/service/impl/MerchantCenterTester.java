package com.hq.sid.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.SpringJUnitConfiger;
import com.hq.sid.dao.generate.TSidMercMapper;
import com.hq.sid.entity.generate.TSidOper;
import com.hq.sid.service.MercService;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.entity.request.MercReq;
import com.hq.sid.service.entity.request.MercSearchReq;
import com.hq.sid.service.entity.request.OperSearchReq;
import com.hq.sid.service.entity.response.MercResp;

/**
 * @author Yan
 * @date 2017年1月12日
 * @version V1.0
 */
public class MerchantCenterTester extends SpringJUnitConfiger {

	@Autowired
	private MercService mercService;
	
	@Autowired
	private OpertorService opertorService;
	
	@Autowired
	private TSidMercMapper tSidMercMapper;

	/*@Test
	public void mercRegister(){
		try {
			HqRequest hqRequest = new HqRequest();
			hqRequest.setUserId("1");
			
			String bizContent = "{\"mercName\":\"熊太太的花店\",\"mercShotName\":\"熊太太的花店\",\"mercAddr\":\"湖南省长沙市开福区金泰社区湘江世纪城赏江苑市政道路层一1914号门面\",\"mercType\":false,\"busiIsThreeInOne\":true,\"busiLicenseNo\":\"430105600472846\",\"busiLicenseDate\":\"2015-11-12\",\"busiLicenseIndate\":\"2099-12-31\",\"busiScope\":\"礼品鲜花零售\",\"busiLicenseProv\":\"430000\",\"busiLicenseCity\":\"430100\",\"orgCode\":\"430105600472846\",\"legalPersonName\":\"倪玉润\",\"legalPersonCertType\":\"RESIDENT\",\"legalPersonCertNo\":\"430121198109221020\",\"legalPersonCertDate\":\"2007-09-20\",\"legalPersonCertIndate\":\"2017-09-20\",\"bankType\":false,\"bankCardNo\":\"\",\"bankAccountName\":\"\",\"bankSub\":\"\",\"bankName\":\"\",\"bankProv\":\"\",\"bankCity\":\"\",\"bankAccountMobile\":\"\",\"bankHolderCertno\":\"\",\"contactName\":\"\",\"contactMobile\":\"\",\"contactEmail\":\"\",\"serviceTel\":\"\",\"shopName\":\"\",\"webAddress\":\"\",\"sellDetail\":\"礼品鲜花零售\",\"aliLoginId\":\"\",\"aliMccCode\":\"\",\"aliFwName\":\"\",\"wxMccCode\":\"\",\"wxPublicName\":\"\"}";
			hqRequest.setBizContent(bizContent);
			
            MercReq req = JSON.parse(hqRequest.getBizContent(), MercReq.class);
            req.setOperId(hqRequest.getUserId());
            OperSearchReq operReq  = new OperSearchReq();
            operReq.setId(Integer.valueOf(hqRequest.getUserId()));
            TSidOper oper = opertorService.getOper(operReq);
            req.setpCoreId(oper.getBeloneCoreId());
            if(req.getCoreId()==null&&req.getMercId()==null){
                mercService.createMerc(req);
            }else{
               mercService.uptMerc(req);
               MercResp resp = new MercResp();
               BeanUtils.copyProperties(req,resp);
            }
        } catch (ParseException e) {
            throw new BusinessException("商户进件失败");
        } catch (Exception e) {
            throw new BusinessException("商户进件失败");
        }
	}*/
	
	@Test
	public void getSidOper() {
		OperSearchReq operReq = new OperSearchReq();
		operReq.setId(Integer.valueOf("1"));
		opertorService.getOper(operReq);
	}
	
	@Test
	public void deleteMerc(){
		MercSearchReq serarch = new MercSearchReq();
		PageInfo<?> result = mercService.getMercList(serarch, 10, 1);
		Logger.getLogger().info(result);
		
		tSidMercMapper.deleteByPrimaryKey(1);
	}
}
