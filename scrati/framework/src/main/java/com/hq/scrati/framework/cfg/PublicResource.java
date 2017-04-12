package com.hq.scrati.framework.cfg;

import com.alibaba.fastjson.JSON;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.FrameworkInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取公共资源
 * @Author Zale
 * @Date 2016/11/7 下午3:07
 *
 */
@Component("publicResource")
public class PublicResource {
    @Autowired
    private FrameworkInvoker frameworkInvoker;
    /**
     * 获取省份
     * @return null if has exception
     * areaCode 编码
     * areaName 名称
     *
     */
    public List<Map<String,String>> getProvs(){
        Map<String, Object> params = new HashMap<>();
        return invoke("elva.area.getprov","1.0",params);
    }


    /**
     * 根据省份编码获得地市
     * @param provCode 省份编码
     * @return null if has exception
     * areaCode 编码
     * areaName 名称
     *
     */
    public List<Map<String,String>> getCities(String provCode){
        Map<String, Object> params = new HashMap<>();
        params.put("provCode",provCode);
        return invoke("elva.area.getcity","1.0",params);
    }


    /**
     * 获取银联省份
     * @return null if has exception
     * provCd  编码
     * provNm  名称
     *
     */
    public List<Map<String,String>> getBankProvs(){
        Map<String, Object> params = new HashMap<>();
        return invoke("elva.bank.getprov","1.0",params);
    }


    /**
     * 根据银联省份编码获得地市
     * @param provCode 省份编码
     * @return null if has exception
     * cityCd 编码
     * cityNm 名称
     *
     */
    public List<Map<String,String>> getBankCities(String provCode){
        Map<String, Object> params = new HashMap<>();
        params.put("provCode",provCode);
        return invoke("elva.bank.getcity","1.0",params);
    }

    /**
     * 根据银联城市编码获得区县
     * @param cityCode 地市编码
     * @return null if has exception
     * cityCd 编码
     * cityNm 名称
     *
     */
    public List<Map<String,String>> getBankArea(String cityCode){
        Map<String, Object> params = new HashMap<>();
        params.put("cityCode",cityCode);
        return invoke("elva.bank.getarea","1.0",params);
    }

    /**
     * 根据银联城市编码获得区县
     * @return null if has exception
     * lbnkCd 银行编号
     * bnkNm 银行名称
     * corpOrg 银行英文简称
     *
     */
    public List<Map<String,String>> getBankInfo(){
        Map<String, Object> params = new HashMap<>();
        return invoke("elva.bkcd.getbkcd","1.0",params);
    }

    /**
     * 根据银行编号获取银联行号数据
     * @param lbnkNo 联行行号
     * @return null if has exception
     * lbnkCd 银行编号
     * lbnkNm 联行名称
     * lbnkNo 联行行号
     * admCit 归属市
     * admPro 归属省
     * admRgn 归属区
     * corpOr 合作机构编号
     *
     */
    public Map<String,String> getBankIn(String lbnkNo){
        Map<String, Object> params = new HashMap<>();
        params.put("lbnkNo", lbnkNo);
        return invoke("elva.bkin.getbkin","1.0",params);
    }

    /**
     * 根据银行编码，省份，地市，区县获取银联行号数据
     * @param lbnkCd 银行编码
     * @param admProv 归属省
     * @param admCity 归属省
     * @param admRgn  归属区域
     * @return null if has exception
     * lbnkCd 银行编号
     * lbnkNm 联行名称
     * lbnkNo 联行行号
     * admCit 归属市
     * admPro 归属省
     * admRgn 归属区
     * corpOr 合作机构编号
     *
     */
    public List<Map<String,String>> getBankIn(String lbnkCd,String admProv ,String admCity ,String admRgn){
        Map<String, Object> params = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("lbnkCd", lbnkCd);
        param.put("admProv", admProv);
        param.put("admCity", admCity);
        param.put("admRgn", admRgn);
        params.put("paramMap", JSON.toJSONString(param));
        return invoke("elva.bkin.getbkinc","1.0",params);
    }


    /**
     * 根据参数名获取参数列表
     * @param paramName 参数名
     * @return null if has exception
     * paramCname 参数中文名
     * paramExp 参数输出
     * paramExpDesc 参数输出描述
     * paramType 参数类型
     * paramValue 参数值
     * paramId 参数ID
     *
     */
    public List<Map<String,String>> getParams(String paramName){
        Map<String, Object> params = new HashMap<>();
        params.put("paramName", paramName);
        return invoke("elva.dict.name","1.0",params);
    }

    /**
     * 根据参数名和参数值获取参数
     * @param paramName 参数名
     * @param paramValue 参数值
     * @return 参数输出 null if has exception
     *
     *
     */
    public String getParam(String paramName,String paramValue){
        Map<String, Object> params = new HashMap<>();
        params.put("paramName", paramName);
        params.put("paramValue", paramValue);
        return invoke("elva.dict.nameval","1.0",params);
    }
    
    /**
     * 根据mcc大类获取mcc信息
     * @param typId 大类ID
     * @return 参数输出 null if has exception
     * busCls 经营类别
     * busDesc 适用业务范围
     * indCls 行业类别
     * mccCd  MCC代码
     * mccCdExt MCC大类
     * mercClsNm 商户类型名称
     *
     */
    public List<Map<String, String>> getMccs(String typId){
        Map<String, Object> params = new HashMap<>();
        params.put("typId", typId);
        return invoke("elva.mcc.typId","1.0",params);
    }
    
    /**
     * 
     * @param pMccCd 父级MCC Code
     * @return
     */
    public List<?> getAliMcc(String pMccCd){
    	Map<String, Object> params = new HashMap<>();
        params.put("pMccCd", pMccCd);
    	return invoke("elva.mcc.alimcc", "1.0", params);
    }
    
    /**
     * 根据mcc码获取mcc信息
     * @param mccCd mcc码
     * @return 参数输出 null if has exception
     * busCls 经营类别
     * busDesc 适用业务范围
     * indCls 行业类别
     * mccCd  MCC代码
     * mccCdExt MCC大类
     * mercClsNm 商户类型名称
     *
     */
    public Map<String, String> getMccInfo(String mccCd){
        Map<String, Object> params = new HashMap<>();
        params.put("mccCd", mccCd);
        return invoke("elva.mcc.mcccd","1.0",params);
    }
    
    /**
     * 根据银行卡号获取卡bin数据
     * @param cardNo 卡号
     * @return 参数输出 null if has exception
     * bnkTyp 银行类型
     * crdFlg 卡类型
     * crdLen 卡号长度
     * crdNm  卡名称
     * crdOfs 卡号所在磁道的偏移量
     * crdTrk 卡号所在磁道
     * expDtFlg 卡有效期偏移量
     * fitCtt FIT内容
     * fitLen FIT内容的有效数据长度
     * fitNo  FIT序号
     * fitOfs FIT在磁道上的偏移量
     * fitTrk FIT所在磁道
     * intMod 是否允许手输卡号
     * fitId  卡bin ID
     *
     */
    public Map<String, String> getUfit(String cardNo){
        Map<String, Object> params = new HashMap<>();
        params.put("cardNo", cardNo);
        return invoke("elva.ufit.card","1.0",params);
    }
    
    /**
     * 查询Mcc大类信息
     * @return 参数输出 null if has exception
     * typId 类别ID
     * typNm 类别名称
     * baseAmt 基准费率封顶
     * baseRef 基准费率
     * specAmt 特殊计费费率封顶
     * specRef 特殊计费费率
     *
     */
    public List<Map<String, String>> getYpets(){
        Map<String, Object> params = new HashMap<>();
        return invoke("elva.mcc.getypets","1.0",params);
    }
    
    /**
     * 根据mcc大类获取大类明细
     * @param typId 大类ID
     * @return 参数输出 null if has exception
     * typId 类别ID
     * typNm 类别名称
     * baseAmt 基准费率封顶
     * baseRef 基准费率
     * specAmt 特殊计费费率封顶
     * specRef 特殊计费费率
     *
     */
    public Map<String, String> getYpets(String typId){
        Map<String, Object> params = new HashMap<>();
        params.put("typeId", typId);
        return invoke("elva.mcc.getypet","1.0",params);
    }

    private <T> T invoke(String serviceCode,String version,Map<String, Object> params) {
        RespEntity<T> resp = (RespEntity<T>) frameworkInvoker.invoke(serviceCode,version,params);
        if("0000".equals(resp.getKey())){
            return resp.getExt();
        }
        return null;
    }


}
