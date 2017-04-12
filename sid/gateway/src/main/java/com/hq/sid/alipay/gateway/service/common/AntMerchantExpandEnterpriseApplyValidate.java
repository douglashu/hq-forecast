package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.domain.*;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:57
 */
@Component
public class AntMerchantExpandEnterpriseApplyValidate implements IAntMerchantExpandEnterpriseApplyValidate {

    @Override
    public void validate(AntMerchantExpandEnterpriseApplyModel model) {
        if( null == model ){
            throw new ParamValueException("【AntMerchantExpandEnterpriseApplyModel】企业级商户入驻接口参数不能为空");
        }
        if (StringUtils.isBlank(model.getLoginId())) {
            throw new ParamValueException("【login_id】支付宝登录别名,邮箱地址或手机号码不能为空");
        }
        if (StringUtils.length(model.getLoginId()) > 64) {
            throw new ParamValueException("【login_id】支付宝登录别名,邮箱地址或手机号码长度不能超过64");
        }
        if (StringUtils.isBlank(model.getOutBizNo())) {
            throw new ParamValueException("【out_biz_no】外部入驻申请单据号不能为空");
        }
        if (StringUtils.length(model.getOutBizNo()) > 64) {
            throw new ParamValueException("【out_biz_no】外部入驻申请单据号长度不能超过64");
        }

        // 企业基本信息验证
        if (null == model.getBaseInfo()) {
            throw new ParamValueException("【base_info】企业基本信息不能为空");
        }
        BaseInfo info = model.getBaseInfo();
        if (StringUtils.isBlank(info.getLogoPic())) {
            throw new ParamValueException("【logo_pic】企业logo图片不能为空");
        }
        if (StringUtils.length(info.getLogoPic()) > 64) {
            throw new ParamValueException("【logo_pic】企业logo图片长度不能超过64");
        }
        if (StringUtils.isBlank(info.getMccCode())) {
            throw new ParamValueException("【mcc_code】企业所属MCCCode不能为空");
        }
        if (StringUtils.length(info.getMccCode()) > 16) {
            throw new ParamValueException("【mcc_code】企业l所属MCCCode长度不能超过16");
        }

        if (null == info.getContactInfo() || info.getContactInfo().size() <= 0) {
            throw new ParamValueException("【contact_info】企业联系人信息不能为空");
        }
        info.getContactInfo().stream().filter(c -> c != null).forEach(c -> {
            if (StringUtils.isBlank(c.getContactName())) {
                throw new ParamValueException("【contact_name】企业联系人名称不能为空");
            }
            if (StringUtils.length(c.getContactName()) > 64) {
                throw new ParamValueException("【contact_name】企业联系人名称长度不能超过64");
            }
            if (StringUtils.isBlank(c.getContactMobile())) {
                throw new ParamValueException("【contact_mobile】联系人手机号不能为空");
            }
            if (StringUtils.length(c.getContactMobile()) > 16) {
                throw new ParamValueException("【contact_mobile】联系人手机号长度不能超过16");
            }
            if (StringUtils.isBlank(c.getContactEmail())) {
                throw new ParamValueException("【contact_email】联系人邮箱地址不能为空");
            }
            if (StringUtils.length(c.getContactEmail()) > 64) {
                throw new ParamValueException("【contact_email】联系人邮箱地址长度不能超过64");
            }
        });

        // 营业执照信息验证
        if (null == model.getBusinessLicenseInfo()) {
            throw new ParamValueException("【business_license_info】营业执照信息不能为空");
        }
        BusinessLicenceInfo licenceInfo = model.getBusinessLicenseInfo();
        if (StringUtils.isBlank(licenceInfo.getCompanyName())) {
            throw new ParamValueException("【company_name】营业执照上的企业名称不能为空");
        }
        if (StringUtils.length(licenceInfo.getCompanyName()) > 64) {
            throw new ParamValueException("【company_name】营业执照上的企业名称长度不能超过64");
        }

        if (StringUtils.isBlank(licenceInfo.getCompanyAddress())) {
            throw new ParamValueException("【company_address】营业执照上的企业联系地址不能为空");
        }
        if (StringUtils.length(licenceInfo.getCompanyAddress()) > 128) {
            throw new ParamValueException("【company_address】营业执照上的企业联系地址长度不能超过128");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessLicenseNo())) {
            throw new ParamValueException("【business_license_no】营业执照号码不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessLicenseNo()) > 32) {
            throw new ParamValueException("【business_license_no】营业执照号码长度不能超过32");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessLicenseIndate())) {
            throw new ParamValueException("【business_license_indate】营业执照有效期不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessLicenseIndate()) > 32) {
            throw new ParamValueException("【business_license_indate】营业执照有效期长度不能超过32");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessLicensePic())) {
            throw new ParamValueException("【business_license_pic】营业执照图片不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessLicensePic()) > 64) {
            throw new ParamValueException("【business_license_pic】营业执照图片长度不能超过64");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessScope())) {
            throw new ParamValueException("【business_scope】营业执照上的企业经营范围不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessScope()) > 512) {
            throw new ParamValueException("【business_scope】营业执照上的企业经营范围长度不能超过512");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessLicenseProvince())) {
            throw new ParamValueException("【business_license_province】营业执照所在地省份不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessLicenseProvince()) > 6) {
            throw new ParamValueException("【business_license_province】营业执照所在地省份长度不能超过6");
        }

        if (StringUtils.isBlank(licenceInfo.getBusinessLicenseCity())) {
            throw new ParamValueException("【business_license_city】营业执照所在城市不能为空");
        }
        if (StringUtils.length(licenceInfo.getBusinessLicenseCity()) > 6) {
            throw new ParamValueException("【business_license_city】营业执照所在城市长度不能超过6");
        }

        // 企业级商户法人信息验证
        if (null == model.getLegalRepresentativeInfo()) {
            throw new ParamValueException("【legal_representative_info】企业级商户法人信息不能为空");
        }

        LegalRepresentativeInfo legalRepresentativeInfo = model.getLegalRepresentativeInfo();

        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeName())) {
            throw new ParamValueException("【legal_representative_name】法人姓名不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeName()) > 128) {
            throw new ParamValueException("【legal_representative_name】法人姓名长度不能超过128");
        }
        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeCertType())) {
            throw new ParamValueException("【legal_representative_cert_type】法人证件类型不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeCertType()) > 16) {
            throw new ParamValueException("【legal_representative_cert_type】法人证件类型长度不能超过16");
        }

        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeCertNo())) {
            throw new ParamValueException("【legal_representative_cert_no】法人证件号码不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeCertNo()) > 32) {
            throw new ParamValueException("【legal_representative_cert_no】法人证件号码长度不能超过32");
        }

        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeCertIndate())) {
            throw new ParamValueException("【legal_representative_cert_indate】法人证件到期日不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeCertIndate()) > 16) {
            throw new ParamValueException("【legal_representative_cert_indate】法人证件到期日长度不能超过16");
        }

        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeCertPicFront())) {
            throw new ParamValueException("【legal_representative_cert_pic_front】法人证件正面照片不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeCertPicFront()) > 64) {
            throw new ParamValueException("【legal_representative_cert_pic_front】法人证件正面照片长度不能超过64");
        }

        if (StringUtils.isBlank(legalRepresentativeInfo.getLegalRepresentativeCertPicBack())) {
            throw new ParamValueException("【legal_representative_cert_pic_back】法人证件照片背面图片不能为空");
        }
        if (StringUtils.length(legalRepresentativeInfo.getLegalRepresentativeCertPicBack()) > 64) {
            throw new ParamValueException("【legal_representative_cert_pic_back】法人证件照片背面图片长度不能超过64");
        }

        // 企业对公账户信息验证
        if (null == model.getBusinessBankAccountInfo()) {
            throw new ParamValueException("【business_bank_account_info】企业对公账户信息不能为空");
        }

        BusinessBankAccountInfo businessBankAccountInfo = model.getBusinessBankAccountInfo();
        if (StringUtils.isBlank(businessBankAccountInfo.getBusinessBankCardNo())) {
            throw new ParamValueException("【business_bank_card_no】企业对公银行账户号不能为空");
        }
        if (StringUtils.length(businessBankAccountInfo.getBusinessBankCardNo()) > 32) {
            throw new ParamValueException("【business_bank_card_no】企业对公银行账户号长度不能超过32");
        }
        if (StringUtils.isBlank(businessBankAccountInfo.getBusinessBankAccountName())) {
            throw new ParamValueException("【business_bank_account_name】企业对公账户名称不能为空");
        }
        if (StringUtils.length(businessBankAccountInfo.getBusinessBankAccountName()) > 64) {
            throw new ParamValueException("【business_bank_account_name】企业对公账户名称不能超过64");
        }
        if (StringUtils.isBlank(businessBankAccountInfo.getBusinessBankSub())) {
            throw new ParamValueException("【business_bank_sub】企业对公账户开户行支行全称不能为空");
        }
        if (StringUtils.length(businessBankAccountInfo.getBusinessBankSub()) > 64) {
            throw new ParamValueException("【business_bank_sub】企业对公账户开户行支行全称长度不能超过64");
        }

        if (StringUtils.isBlank(businessBankAccountInfo.getBusinessBankName())) {
            throw new ParamValueException("【business_bank_name】企业对公账户开户行名称不能为空");
        }
        if (StringUtils.length(businessBankAccountInfo.getBusinessBankName()) > 32) {
            throw new ParamValueException("【business_bank_name】企业对公账户开户行名称长度不能超过32");
        }

        // 门店信息验证
        if (null != model.getShopInfo()) {
            ShopInfo shopInfo = model.getShopInfo();
            if (StringUtils.isBlank(shopInfo.getShopSignBoardPic())) {
                throw new ParamValueException("【shop_sign_board_pic】店铺门头照图片不能为空");
            }
            if (StringUtils.length(shopInfo.getShopSignBoardPic()) > 64) {
                throw new ParamValueException("【shop_sign_board_pic】店铺门头照图片长度不能超过64");
            }

            if (null == shopInfo.getShopScenePic() || shopInfo.getShopScenePic().size() != 3) {
                throw new ParamValueException("【shop_scene_pic】店铺内景图片不能为空或者数量不能小于3张");
            }

            shopInfo.getShopScenePic().stream().filter(c -> c != null).forEach((String sinfo) -> {
                if (StringUtils.length(sinfo) > 64) {
                    throw new ParamValueException("【shop_scene_pic】店铺内景图片长度不能大于64");
                }
            });
        }
    }
}
