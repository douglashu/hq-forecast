package com.hq.sid.alipay.gateway.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.FileItem;
import com.alipay.api.domain.AntMerchantExpandPersonalApplyModel;
import com.alipay.api.domain.BaseInfo;
import com.alipay.api.domain.BusinessLicenceInfo;
import com.alipay.api.domain.ContactPersonInfo;
import com.alipay.api.domain.OperatorInfo;
import com.alipay.api.domain.PersonnalBankAccountInfo;
import com.alipay.api.domain.ShopInfo;
import com.alipay.api.response.AntMerchantExpandImageUploadResponse;
import com.alipay.api.response.AntMerchantExpandPersonalApplyResponse;
import com.hq.scrati.common.log.Logger;
import com.hq.sid.SpringJUnitConfiger;
import com.hq.sid.alipay.gateway.service.MerchantSettledService;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.impl
 * @创建人：yyx @创建时间：16/12/12 下午10:10
 */
@Service
public class MerchantSettledServiceImplTester extends SpringJUnitConfiger {

	public static final String PICS_BASE_PATH = "C:\\Users\\Yan\\Desktop\\pics\\";

	private Logger logger = Logger.getLogger();

	@Autowired
	private MerchantSettledService merchantSettledService;

	@Test
	public void antMerchantExpandPersonalApply() {
		// ISV图片上传
		AntMerchantExpandImageUploadResponse uploadResponse = null;
		FileItem item = null;
		
		// 营业执照图片上传
		item = new FileItem(new File(PICS_BASE_PATH + "205959759002045691.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		String businessLicensePic = uploadResponse.getImageId();
		
		// 个体工商户经营者证件正面照片
		item = new FileItem(new File(PICS_BASE_PATH + "396168035198605371.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		String operatorCertPicFront = uploadResponse.getImageId();

		// 个体工商户经营者证件照片背面图片
		item = new FileItem(new File(PICS_BASE_PATH + "748122615758991163.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		String operatorCertPicBack = uploadResponse.getImageId();

		// 店铺门头照图片
		item = new FileItem(new File(PICS_BASE_PATH + "800042279221667503.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		String shopSignBoardPic = uploadResponse.getImageId();

		// 店铺内景图片不能为空或者数量不能小于3张
		List<String> shopScenePic = new ArrayList<>();
		
		item = new FileItem(new File(PICS_BASE_PATH + "163309713003597892.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		shopScenePic.add(uploadResponse.getImageId());
		
		item = new FileItem(new File(PICS_BASE_PATH + "350403797666165857.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		shopScenePic.add(uploadResponse.getImageId());
		
		item = new FileItem(new File(PICS_BASE_PATH + "800042279221667503.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		shopScenePic.add(uploadResponse.getImageId());

		//个体工商户入驻接口 (ant.merchant.expand.personal.apply)
		AntMerchantExpandPersonalApplyModel model = new AntMerchantExpandPersonalApplyModel();
		
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setAlipayFuwuName("熊太太的花店");
		baseInfo.setMccCode("C_C10_5992");
		List<ContactPersonInfo> contactInfo = new ArrayList<>();
		ContactPersonInfo contactPersonInfo = new ContactPersonInfo();
		contactPersonInfo.setContactName("晏伦");
		contactPersonInfo.setContactMobile("15073152671");
		contactPersonInfo.setContactEmail("yanlun0323@163.com");
		baseInfo.setContactInfo(contactInfo);
		
		item = new FileItem(new File(PICS_BASE_PATH + "800042279221667503.jpg"));
		uploadResponse = merchantSettledService.antMerchantExpandImageUpload(item);
		baseInfo.setLogoPic(uploadResponse.getImageId());
		
		model.setBaseInfo(baseInfo);
		
		BusinessLicenceInfo businessLicenseInfo = new BusinessLicenceInfo();
		businessLicenseInfo.setCompanyName("长沙市开福区倪玉润鲜花店");
		businessLicenseInfo.setCompanyAddress("湖南省长沙市开福区金泰社区湘江世纪城赏江苑市政道路层一1914号门面");
		businessLicenseInfo.setBusinessLicenseNo("430105600472846");
		businessLicenseInfo.setBusinessLicenseIndate("2099-12-31");
		businessLicenseInfo.setBusinessLicensePic(businessLicensePic);
		businessLicenseInfo.setBusinessScope("礼品鲜花零售");
		businessLicenseInfo.setBusinessLicenseProvince("430000");
		businessLicenseInfo.setBusinessLicenseCity("430100");
		
		model.setBusinessLicenseInfo(businessLicenseInfo);
		
		model.setLoginId("18573177625");
		
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setOperatorName("倪玉润");
		operatorInfo.setOperatorCertType("RESIDENT");
		operatorInfo.setOperatorCertNo("430121198109221020");
		operatorInfo.setOperatorCertIndate("2017-09-20");
		operatorInfo.setOperatorCertPicFront(operatorCertPicFront);
		operatorInfo.setOperatorCertPicBack(operatorCertPicBack);
		
		model.setOperatorInfo(operatorInfo);
		
		model.setOutBizNo("HQAST201701121688");
		
		PersonnalBankAccountInfo personalBankAccountInfo = new PersonnalBankAccountInfo();
		personalBankAccountInfo.setPersonalBankAccountMobile("15073152671");
		personalBankAccountInfo.setPersonalBankCardNo("6214857211409166");
		personalBankAccountInfo.setPersonalBankHolderCertno("430726199203232518");
		personalBankAccountInfo.setPersonalBankHolderName("晏伦");
		
		model.setPersonalBankAccountInfo(personalBankAccountInfo);
		
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setShopName("熊太太的花店");
		shopInfo.setShopSignBoardPic(shopSignBoardPic);
		shopInfo.setShopScenePic(shopScenePic);
		
		model.setShopInfo(shopInfo);

		AntMerchantExpandPersonalApplyResponse applyResponse = merchantSettledService.antMerchantExpandPersonalApply(model);
		logger.debug(applyResponse);
	}
}
