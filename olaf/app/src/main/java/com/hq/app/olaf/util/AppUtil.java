package com.hq.app.olaf.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.hq.app.olaf.ui.bean.db.City;
import com.hq.app.olaf.ui.bean.db.Province;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by huwentao on 16/7/12.
 */
public class AppUtil {
    private final static Pattern CHECK_ID_18 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    private final static Pattern CHECK_ID_15 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
    private final static Pattern CHECK_ALL_NUM_PWD = Pattern.compile("^\\d+$");
    private final static Pattern CHECKIDP_ALL_ABC_PWD = Pattern.compile("^[a-zA-Z]+$");

    private final static Pattern CHECK_ONE_NUM = Pattern.compile("^\\d+(\\.[\\d])$");//1位小数
    private final static Pattern CHECK_TWO_NUM = Pattern.compile("^\\d+(\\.\\d{2})$");//两位小数

    public static boolean check(CharSequence charSequence, Pattern reg) {
        return reg.matcher(charSequence).matches();
    }

    /**
     * 检查身份证格式
     *
     * @param ID
     * @return
     */
    public static boolean checkID(CharSequence ID) {
        return check(ID, CHECK_ID_18) || check(ID, CHECK_ID_15);
    }

    /**
     * 检查是否为纯数字
     *
     * @param pwd
     * @return
     */
    public static boolean checkAllNumPwd(CharSequence pwd) {
        return check(pwd, CHECK_ALL_NUM_PWD);
    }

    /**
     * 检查是否为纯字母
     *
     * @param pwd
     * @return
     */
    public static boolean checkAllABCPwd(CharSequence pwd) {
        return check(pwd, CHECKIDP_ALL_ABC_PWD);
    }

    /**
     * 检查是否为1位小数
     *
     * @param num
     * @return
     */
    public static boolean checkOneNum(CharSequence num) {
        return check(num, CHECK_ONE_NUM);
    }

    /**
     * 检查是否为两位小数
     *
     * @param num
     * @return
     */
    public static boolean checkTwoNum(CharSequence num) {
        return check(num, CHECK_TWO_NUM);
    }

    /**
     * @param cityAreasJson
     * @param cityName
     * @return 0: province  1:city
     */
    public static String[] findCityAreaCode(JSONArray cityAreasJson, String provinceName, String cityName) {
        if (cityAreasJson == null) return null;
        String[] cityAreas = new String[2];
        List<Province> provinces = JSON.parseObject(cityAreasJson.toJSONString(),
                new TypeReference<List<Province>>() {
                });
        List<City> cityList = null;
        for (Province province : provinces) {
            if (province.getName().equals(provinceName)) {
                cityAreas[0] = province.getCode();
                cityList = province.getCitys();
            }
        }
        if (TextUtils.isEmpty(cityAreas[0])) {
            return null;
        }
        if (cityList == null || cityList.size() == 0) {
            return null;
        }
        for (City city : cityList) {
            if (city.getName().equals(cityName)) {
                cityAreas[1] = city.getCode();
            }
        }
        if (TextUtils.isEmpty(cityAreas[1])) {
            return null;
        }
        return cityAreas;
    }

    /**
     * @param cityAreasJson
     * @param provinceCode
     * @param cityCode
     * @return
     */
    public static String[] findCityAreaName(JSONArray cityAreasJson, String provinceCode, String cityCode) {
        if (cityAreasJson == null) return null;
        String[] cityAreas = new String[2];
        List<Province> provinces = JSON.parseObject(cityAreasJson.toJSONString(),
                new TypeReference<List<Province>>() {
                });
        List<City> cityList = null;
        for (Province province : provinces) {
            if (province.getCode().equals(provinceCode)) {
                cityAreas[0] = province.getName();
                cityList = province.getCitys();
            }
        }
        if (TextUtils.isEmpty(cityAreas[0])) {
            return null;
        }
        if (cityList == null || cityList.size() == 0) {
            return null;
        }
        for (City city : cityList) {
            if (city.getCode().equals(cityCode)) {
                cityAreas[1] = city.getName();
            }
        }
        if (TextUtils.isEmpty(cityAreas[1])) {
            return null;
        }
        return cityAreas;
    }

//    * NETWORK_TYPE_CDMA 网络类型为CDMA
//    * NETWORK_TYPE_EDGE 网络类型为EDGE
//    * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
//    * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
//    * NETWORK_TYPE_GPRS 网络类型为GPRS
//    * NETWORK_TYPE_HSDPA 网络类型为HSDPA
//    * NETWORK_TYPE_HSPA 网络类型为HSPA
//    * NETWORK_TYPE_HSUPA 网络类型为HSUPA
//    * NETWORK_TYPE_UMTS 网络类型为UMTS
//    联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO

    /**
     * @param context
     * @return
     */
    public static boolean is2GNetwork(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        int type = info.getSubtype();
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return true;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return true;
            default:
                return false;
        }
    }

    /**
     * 判断服务是否在运行
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if(serviceList == null || serviceList.isEmpty())
            return false;
        for(int i = 0; i < serviceList.size(); i++) {
            if(serviceList.get(i).service.getClassName().equals(className) && TextUtils.equals(
                    serviceList.get(i).service.getPackageName(), context.getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
