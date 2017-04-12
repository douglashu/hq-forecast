package com.hq.app.olaf.net;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.net.sign.Algorithm;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.bean.merchant.Picture;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.ui.bean.page.Pager;
import com.hq.app.olaf.util.DateUtil;
import com.hq.app.olaf.util.StringUtil;
import com.hq.app.olaf.util.ZipUtil;
import com.hq.component.network.exception.NetError;
import com.hq.component.network.net.HttpMethod;
import com.hq.component.network.net.IHttpRequest;
import com.hq.component.network.net.KeyValue;
import com.hq.component.network.net.MimeType;
import com.hq.component.network.net.impl.UploadFile;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.BitmapUtils;
import com.hq.component.utils.MD5;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by mokey on 2016/4/20.
 */
public class HttpService {

    private static final String PROCTOL_HTML_IP = "114.80.221.216:11680";//协议页面地址
    private static final String HELP_HTML_IP = "114.80.221.216:7070";//协议页面地址


//    private final static String SERVER_ADDRESS = "http://172.19.100.189:8080";//开发服务器
//    private final static String SERVER_ADDRESS = "http://172.19.100.182:8080/app-server";//开发文件服务器

   // private final static String SERVER_ADDRESS = "http://172.19.100.190:8080";//测试OPEN服务器-内网
   // private final static String FILE_SERVER_ADDRESS = "http://172.19.100.224:8080/uploadfile";//测试文件服务器
    //private static final String IMAGE_LOAD_IP = "172.19.100.162:8888";//测试图片服务器
    //
   private final static String FILE_SERVER_ADDRESS = "http://114.80.221.216:7070/app-server";//生产文件服务器

   private static final String IMAGE_LOAD_IP = "114.80.221.216:11680";//生产服务器IP

    //    private final static String SERVER_ADDRESS = "http://subscription.cardinfo.com.cn/openapi";//生产服务器域名
    public final static String CER_CERTIFICATES = "";

    /**
     * 通知消息查询
     *
     * @return
     */
    public HttpTask management_PushMessage_getMessages(final int rowIndex) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("rowIndex", rowIndex);
                get(getHttpRequest(), "management_PushMessage_getMessages", params);
            }
        };
    }

    /**
     * 查询收款成功通知业务状态
     *
     * @return
     */
    public HttpTask management_msg_getResult() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "config", null);
            }
        };
    }

    /**
     * 更新收款成功通知业务及时到账状态
     *
     * @param isOpen
     * @return
     */
    public HttpTask management_msg_update(final boolean isOpen) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("acceptTradePush", isOpen);
                put(getHttpRequest(), "config", params);
            }
        };
    }

    /**
     * 绑定推送
     *
     * @param deviceToken 绑定接口
     * @return
     */
    public HttpTask management_push_bind(final String deviceToken) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                //osPlatform[1- Android, 2- IOS],
                params.put("osplatform", "Android");
                params.put("deviceToken", deviceToken);
                put(getHttpRequest(), "device", params);
            }
        };
    }

    /**
     * 解绑推送
     *
     * @param deviceToken 绑定接口
     * @return
     */
    public HttpTask management_push_unbind(final String deviceToken) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("deviceToken", deviceToken);
                get(getHttpRequest(), "management_push_bind", params);
            }
        };
    }




    /**
     * 版本更新检查
     *
     * @return
     */
    public HttpTask CheckVer(final String version) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("appId", HttpUtils.appId);
                params.put("version", version);
                get(getHttpRequest(), "app_check_version", params);
            }
        };
    }

    /**
     * 查询签约信息
     *
     * @return
     */
    public HttpTask getProdSigns() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                get(getHttpRequest(), "prod_signs", params);
            }
        };
    }

    /**
     * 今日数据统计信息
     *
     * @return
     */
    public HttpTask getStatisticSummary() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("statisticDate", DateUtil.getDateString(new Date(),"yyyyMMdd"));
                get(getHttpRequest(), "statistic_summary", params);
            }
        };
    }

    /**
     * 查询对账单数据信息
     *
     * @return
     */
    public HttpTask getBillSummary(final String statisticDate) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("statisticDate", TextUtils.isEmpty(statisticDate)?DateUtil.getDateString(new Date(),"yyyyMMdd"):statisticDate);
                get(getHttpRequest(), "bill_summary", params);
            }
        };
    }


    /**
     * 查询商户信息
     *
     * @return
     */
    public HttpTask getMch() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                get(getHttpRequest(), "mch", params);
            }
        };
    }


    /**
     * 查询我的设备
     *
     * @return
     */
    public HttpTask getStoreCodes() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                get(getHttpRequest(), "store_codes", params);
            }
        };
    }

    /**
     * 获取省份列表
     * 高阳历史遗留原因，一般情况传[0],获取支行省市传[1]
     *
     * @return
     */
    public HttpTask getProvs(final String type) {
        return new SimpleHttpTask() {

            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("type", type);
                get(getHttpRequest(), "app_Infrastructure_getProvs", params);
            }
        };
    }

    /**
     * 获取城市列表
     * 高阳历史遗留原因，一般情况传[0],获取支行省市传[1]
     *
     * @param provsCode 省份代码
     * @return
     */
    public HttpTask getCitys(final String provsCode, final String type) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("code", provsCode);
                params.put("type", type);
                get(getHttpRequest(), "app_Infrastructure_getCities", params);
            }
        };
    }

    /**
     * 获取行业类别列表
     *
     * @return
     */
    public HttpTask getMCCs() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "app_Infrastructure_getMCCs", null);
            }
        };
    }

    /**
     * 获取银行列表
     *
     * @param name 银行名称
     * @return
     */
    public HttpTask getBanks(final String name) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(name)) {
                    params.put("name", name);
                }
//                get(getHttpRequest(), "app_Infrastructure_getBanks", params);
                get(getHttpRequest(), "management_baseParam_getBanks", params);
            }
        };
    }

    /**
     * 获取银行支行列表
     *
     * @param bankCode 银行代码
     * @param provCode 省份代码
     * @param cityCode 城市代码
     * @return
     */
    public HttpTask getSubbranches(final String bankCode, final String bankName, final String provCode, final String cityCode) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("provCode", provCode);
                params.put("cityCode", cityCode);
                if (!TextUtils.isEmpty(bankCode)) {
                    params.put("bankCode", bankCode);
                }
                if (!TextUtils.isEmpty(bankName)) {
                    params.put("name", bankName);
                }
                post(getHttpRequest(), "management_baseParam_getBankBranches", params);
//                post(getHttpRequest(), "app_Infrastructure_getBankSubbranches", params);
            }
        };
    }

    /**
     * @param mobile 11位手机号码
     * @param type   需要获取的短信验证码类型 注册传 [1]，重置密码传 [2]
     * @return
     */
    public HttpTask getPasscode(final String mobile, final String type) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", mobile);
               // params.put("type", type);
                get(getHttpRequest(), "passcode", params);
            }
        };
    }

    /**
     * 检查要注册的手机号码是否已被注册
     *
     * @param mobile 手机号
     * @return
     */
    public HttpTask getAccountCheckExists(final String mobile) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", mobile);
                get(getHttpRequest(), "auth_Account_checkExists", params);
            }
        };
    }

    /**
     * 注册账号
     *
     * @param account  11位手机号(与获取短信验证码的手机号需一致)
     * @param passcode 申请账号注册的短信验证码
     * @param password 登录密码(必须调用密码控件进行加密)
     * @return
     */
    public HttpTask postAccount(final String account, final String passcode, final String password) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("account", account);
                params.put("passcode", passcode);
                params.put("password", password);
                post(getHttpRequest(), "auth_Register_register", params);
            }
        };
    }

    /**
     * 通过手机号重置密码(忘记密码)
     *
     * @param mobile  11位手机号(与获取短信验证码的手机号需一致)
     * @param passcode 申请重置密码的短信验证码
     * @param password 新的登录密码(必须调用密码控件进行加密)
     * @return
     */
    public HttpTask putAccount(final String mobile, final String passcode, final String password) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("passcode", passcode);
                params.put("newPassword", password);
                put(getHttpRequest(), "pwd", params);
            }
        };
    }

    /**
     * 原密码修改密码
     *
     * @param originPwd 原密码
     * @return
     */
    public HttpTask pubModfyOriginPass(final String originPwd, final String password) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("oldPassword", originPwd);
                params.put("newPassword", password);
                put(getHttpRequest(), "password", params);
            }
        };
    }

    /**
     * 获取登录挑战值
     *
     * @param mobile 用于登录的11位手机号码
     * @return
     */
    public HttpTask getChallenge(final String mobile) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", mobile);
                get(getHttpRequest(), "auth_Login_getLoginChallengeValue", params);
            }
        };
    }

    /**
     * 登录
     *
     * @param account  11位手机号(与获取登录挑战值的手机号需一致)
     * @param password 使用挑战值加密后的密码
     *                 加密规则:
     *                 1. 使用MD5对密码明文进行加密
     *                 2. 将挑战值拼接到上一步结果尾部
     *                 3. 再使用MD5对上一步结果进行加密
     *                 计算公式:  MD5( MD5(密码明文) + 挑战值 )
     * @return
     */
    public HttpTask postSessions(final String account, final String password) {
        return new SimpleHttpTask() {
            private String encypt = null;

//            @Override
//            public boolean before() {
//                boolean flag = super.before();
//                if (flag) {
//                    TaskResult taskResult = getPreResult();
//                    if (taskResult != null && taskResult.isHaveData()) {
//                        String result = taskResult.getResult().toString();
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(result);
//                            String value = jsonObject.getString("value");
//                            String expiryTime = jsonObject.getString("expiryTime");
//                            encypt = MD5.encryptMD5(MD5.encryptMD5(password) + value);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        getResult().setError("网络加载失败，请稍后再试");
//                        getResult().setStatus(TaskStatus.DataEmpty);
//                    }
//                }
//                return flag;
//            }

            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params = HttpUtils.getCommonParams(params);
                params.put("account", account);
                params.put("password", password);
                post(getHttpRequest(), "sessions", params);
            }

//            @Override
//            public void after() {
//                TaskResult taskResult = getResult();
//                Response response = taskResult.getResponse();
//                TaskStatus status = taskResult.getStatus();
//                Logger.d("请求状态：status(%s)", taskResult.getStatus());
//                if (status.isSuccess()) {
//                    try {
//                        String bodyStr = response.body().string();
//                        if (TextUtils.isEmpty(bodyStr) && HttpUtils.isEmpty(bodyStr)) {
//                            failure();
//                        } else {
//                            Logger.json(bodyStr);
//                            taskResult.setStatus(TaskStatus.success);
//                            taskResult.setResult(bodyStr);
//                        }
//                    } catch (IOException e) {
//                        failure();
//                    } catch (JSONException e) {
//                        Logger.d(e.getMessage(), e);
//                    }
//                }
//            }
         };
    }

    /**
     * 退出登录
     *
     * @return
     */
    public HttpTask deleteSessions() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                delete(getHttpRequest(), "session", null);
            }
        };
    }

    /**
     * 获取应用列表(首页)
     *
     * @param hashcode 上次获取应用列表返回的hashcode
     *                 (服务端会根据此hashcode检查应用本地是否是最新列表减少流量请求，如不传永远获取最新列表)
     * @return
     */
    public HttpTask getApps(final String hashcode) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(hashcode)) {
                    params.put("hashcode", hashcode);
                }
                get(getHttpRequest(), "management_portal_getApps", params);
            }
        };
    }


    /**
     * 扫码客户的付款码发起支付
     * @param authCode
     * @param totalAmount
     * @return
     */
    public HttpTask tradeOrders( final  String authCode, final  double totalAmount, final String ipAddress){
        return new SimpleHttpTask() {

            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("authCode", authCode); // 客户钱包付款码
                params.put("ipAddress", ipAddress);// 动态获取手机ip地址
                //terminalId 传  deviceName, 注意最大长度不能超过32位
                params.put("terminalId", HttpUtils.deviceName.length()>32?HttpUtils.deviceName.substring(0,32):HttpUtils.deviceName);
                params.put("totalAmount", totalAmount);
                post(getHttpRequest(), "trade_orders", params);
            }

        };
    }

    /**
     * 查询订单状态
     * @return
     */
    public HttpTask orderQuery(final  String orderId){

        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                //terminalId 传  deviceName, 注意最大长度不能超过32位
               // params.put("terminalId", HttpUtils.deviceName.length()>32?HttpUtils.deviceName.substring(0,32):HttpUtils.deviceName);
                get(getHttpRequest(), "polling/trade_orders/"+orderId, params);
            }

            @Override
            public void after() {
                TaskResult taskResult = getResult();
                Response response = taskResult.getResponse();
                TaskStatus status = taskResult.getStatus();
                Logger.d("请求状态：status(%s)", taskResult.getStatus());
                if (status.isSuccess()) {
                    try {
                        String bodyStr = response.body().string();
                        if (TextUtils.isEmpty(bodyStr) && HttpUtils.isEmpty(bodyStr)) {
                            failure();
                        } else {
                            Logger.json(bodyStr);
                            ScannerOrderResp scannerOrderResp = JSON.parseObject(bodyStr, ScannerOrderResp.class);
                            taskResult.setStatus(TaskStatus.success);
                            taskResult.setResult(bodyStr);

                        }
                    } catch (IOException e) {
                        failure();
                    } catch (JSONException e) {
                        Logger.d(e.getMessage(), e);
                    }
                }
            }
        };
    }

    /**
     * 商户进件信息
     *
     * @return
     */
    @Deprecated
    public HttpTask getSettles() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "app_Merchant_getSettles", null);
            }
        };
    }

    /**
     * 查询商户性质列表
     *
     * @return
     */
    public HttpTask getMerchantAttr() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "app_Infrastructure_getMerchantAttrs", null);
            }
        };
    }


    /**
     * @param uploadFiles
     * @return
     */
    public HttpTask uploadMerchantPic(final Context context, final UploadFile... uploadFiles) {
        return new SimpleHttpTask() {
            @Override
            public boolean before() {
                if (uploadFiles != null && uploadFiles.length > 0) {
                    for (UploadFile uploadFile : uploadFiles) {
                        if (uploadFile == null) continue;
                        if (!uploadFile.getUploadFile().exists()) {
                            getResult().setError("待上传文件找不到或不存在");
                            getResult().setStatus(TaskStatus.Failure);
                            return false;
                        }
                    }
                } else {
                    getResult().setStatus(TaskStatus.success);
                    Logger.e(new NetError("没有传入的待上传文件"), uploadFiles != null ? uploadFiles.toString() : "uploadFiles==NULL");
                    return false;
                }
                return true;
            }

            @Override
            public void doTask() throws NetError {
                //上传文件压缩
                List<UploadFile> uploadFileList = new ArrayList<>();
                for (UploadFile uploadFile : uploadFiles) {
                    UploadFile forUploadFile = newUploadFile(uploadFile);
                    File originFile = forUploadFile.getUploadFile();
                    Date date = new Date();
                    File newFile = new File(context.getExternalCacheDir(), "temp_" + date.getTime() + originFile.getName());
                    try {
                        newFile.deleteOnExit();
                        if (newFile.createNewFile()) {
                            File newUploadFile = BitmapUtils.compress(originFile.getAbsolutePath(), newFile.getAbsolutePath(), 800, 99);
                            forUploadFile.setUploadFile(newUploadFile);
                            forUploadFile.setFileName(newUploadFile.getName());
                        }
                        uploadFileList.add(forUploadFile);
                    } catch (IOException e) {
                        Logger.e(e, e.getMessage());
                        new NetError("图片处理失败,请重新选择图片");
                    }
                }
                //上传
                Uri uri = Uri.parse(FILE_SERVER_ADDRESS);
                String requestUrl = String.format("http://%s:%s/file/uploadsByName?type=1", uri.getHost(), uri.getPort());
                Map<String, String> params = new HashMap<>();

                UploadFile[] forUploadFiles = new UploadFile[uploadFileList.size()];
                forUploadFiles = uploadFileList.toArray(forUploadFiles);
                getHttpRequest().upload(HttpMethod.POST,
                        requestUrl,
                        params,
                        forUploadFiles);
            }

            private UploadFile newUploadFile(UploadFile uploadFile) {
                UploadFile newUploadFile = new UploadFile();
                if (uploadFile != null) {
                    newUploadFile.setFileName(uploadFile.getFileName());
                    newUploadFile.setUploadFile(uploadFile.getUploadFile());
                    newUploadFile.setMineType(uploadFile.getMineType());
                    newUploadFile.setParamKey(uploadFile.getParamKey());
                }
                return newUploadFile;
            }
        };
    }

    /**
     * 压缩商户图片文件上传
     *
     * @param context
     * @param pics
     * @return
     */
    @Deprecated
    public HttpTask uploadMerchantPic2(Context context, final List<Picture> pics) {
        return new UploadTask(context) {
            private String tempDirectoryPath = "merchant/pictures";
            private File tempDirectory = null;
            List<File> zipFiles = new ArrayList<>();

            @Override
            public boolean before() {
                File path = Environment.getExternalStorageDirectory();
                try {
                    tempDirectory = new File(path, tempDirectoryPath);
                    //创建临时图片保存目录
                    if (!tempDirectory.exists())
                        tempDirectory.mkdirs();
                    //创建压缩文件位置
                    File zipFile = new File(path, "zipUploadFile.zip");
                    if (!zipFile.exists()) {
                        if (zipFile.createNewFile()) {
                            setUploadFile(zipFile);
                            return true;
                        }
                    } else {
                        setUploadFile(zipFile);
                        return true;
                    }
                } catch (IOException e) {
                    Logger.e(e, e.getMessage());
                }
                return false;
            }

            @Override
            public void doTask() throws NetError {
                try {
                    for (Picture picture : pics) {
                        String filePath = Picture.getRealFilePath(getContext(), picture.getFileUri());
                        if (!TextUtils.isEmpty(filePath)) {
                            File pictureFile = new File(filePath);
                            File tempPicture = new File(tempDirectory, pictureFile.getName());
                            File uploadFile = BitmapUtils.compress(filePath, tempPicture.getAbsolutePath(), 700, 95);
                            zipFiles.add(uploadFile);
                        }
                    }
                    ZipUtil.zipFiles(zipFiles, getUploadFile());
                    File[] files = tempDirectory.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                    KeyValue keyValue = new KeyValue(MimeType.STREAM_TYPE, getUploadFile());
                    Map<String, KeyValue> keyValueMap = new HashMap<>();
                    keyValueMap.put("uploadFile", keyValue);
//                    Uri uri = Uri.parse(SERVER_ADDRESS);
                    Uri uri = Uri.parse(FILE_SERVER_ADDRESS);
//                    getHttpRequest().upload(HttpMethod.POST,"http://172.16.100.60:8080/files/upload",keyValueMap);
                    getHttpRequest().upload(HttpMethod.POST, String.format("http://%s:%s/file/upload?type=99",
                            uri.getHost(), uri.getPort()), keyValueMap);
                } catch (IOException e) {
                    Logger.e(e, e.getMessage());
                    throw new NetError("文件打包失败,请确认文件选择正确");
                }
            }
        };
    }

    /**
     * 查询商户信息
     *
     * @return
     */
    public HttpTask getMerchantByRunningNo(final String runningNo) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(runningNo)) {
                    params.put("runningNo", runningNo);
                }
                get(getHttpRequest(), "management_Merchant_getSettleMerchant", params);
            }
        };
    }

    /**
     * 查询商户信息
     *
     * @return
     */
    public HttpTask getMerchantByMerchantId(final String merchantId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(merchantId)) {
                    params.put("merchantId", merchantId);
                }
                get(getHttpRequest(), "management_Merchant_getMerchant", params);
            }
        };
    }

    /**
     * 查询商户信息
     *
     * @return
     */
    @Deprecated
    public HttpTask getMerchant2(final String runningNo, final String merchantId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(runningNo)) {
                    params.put("runningNo", runningNo);
                }
                if (!TextUtils.isEmpty(merchantId)) {
                    params.put("merchantId", merchantId);
                }
                get(getHttpRequest(), "app_Merchant_getMerchantByRunningNo", params);
            }
        };
    }

    /**
     * 查看商户资产信息
     *
     * @return
     */
    @Deprecated
    public HttpTask getAssets2(final String merchantId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(merchantId)) {
                    params.put("merchantNo", merchantId);
                }
                get(getHttpRequest(), "app_Assets_getAssets", params);
            }
        };
    }

    /**
     * 查看商户资产信息
     *
     * @return
     */
    public HttpTask getAssets(final String mblNo) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(mblNo)) {
                    params.put("mblNo", mblNo);
                }
                get(getHttpRequest(), "management_elecaccouts_getAssets", params);
            }
        };
    }

    /**
     * 商户收支明细查询
     *
     * @param timeRange N,过滤条件,时间起始范围,LONG类型表示逗号分隔,如[11111111111,22222222222]
     * @param timeline  N,时间线分页法用于加载更多,数据基于创建时间倒叙排列,系统默认返回timeline之前20条数据
     * @return
     */
    public HttpTask getPayments(final String type,
                                final String timeRange,
                                final String timeline) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (!TextUtils.isEmpty(timeRange)) {
                    params.put("timeRange", timeRange);
                }
                if (!TextUtils.isEmpty(timeline)) {
                    params.put("timeline", timeline);
                }
                if (!TextUtils.isEmpty(type)) {
                    params.put("type", type);

                }
                get(getHttpRequest(), "management_elecaccouts_getPaymentRecords", params);
            }
        };
    }

    /**
     * 首页查询今日扫码流水（店长与操作员)
     *
     * @return
     */
    public HttpTask getScanPayRecords(final Integer rowIndex) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                if (rowIndex != null) {
                    params.put("rowIndex", rowIndex);
                }
                get(getHttpRequest(), "management_scanpay_records", params);
            }
        };
    }

    /**
     * 费率套餐列表
     *
     * @param merchantNo
     * @return
     */
    public HttpTask getFeePackList(final String merchantNo) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("merchantNo", merchantNo);
                get(getHttpRequest(), "marketing_pack_getMercPack", params);
            }
        };
    }

    /**
     * 查询终端列表
     *
     * @return
     */
    public HttpTask getPosList() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "app_Pos_getPOSes", null);
            }
        };
    }

    /**
     * 获取订单列表
     * @param id:   orderId
     * @return
     */
    public HttpTask getOrder(
            final String id ) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();

                get(getHttpRequest(), "trade_order/"+id, params);
            }

        };
    }
    /**
     * 获取订单列表
     * @param operatorId   操作员id  可以不传 ，  传了就显示操作员的订单
     * @param startDate 开始时间,如 20170120
     * @param endDate 结束时间,如 20170220
     * @param page  N,当前页数， 从1开始，  不传默认 == 1
     * @param payChannel:   交易渠道  ALIPAY,  WEIXIN_PAY  ， 不传查所有的
     * @return
     */
    public HttpTask getOrders(
                              final String operatorId ,
                              final String startDate ,
                              final String endDate ,
                              final Integer page,
                              final String payChannel) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                String serviceCode = "";
                if (!TextUtils.isEmpty(operatorId)) {//查询当前操作员的订单信息
                    params.put("operatorId", operatorId);
                }
                serviceCode = "trade_orders";

                if (!TextUtils.isEmpty(startDate)) {
                    params.put("startDate",startDate);
                }
                if (!TextUtils.isEmpty(endDate)) {
                    params.put("endDate", endDate);
                }
                if (page != null) {
                    params.put("page", page);
                }else{
                    params.put("page", 1);
                }

                // 没页显示条数， 不传默认  20条，  最大不能超过50条
                params.put("size", 20);
                if (!TextUtils.isEmpty(payChannel)) {
                    params.put("payChannel", payChannel);
                }
                //notInTradeStates=FAIL,PRE_CREATE    但是这个要传， 意思是不显示失败和预计状态订单
                params.put("notInTradeStates","FAIL,PRE_CREATE");
                get(getHttpRequest(), serviceCode, params);
            }

            @Override
            public void after() {
                TaskResult taskResult = getResult();
                Response response = taskResult.getResponse();
                TaskStatus status = taskResult.getStatus();
                Logger.d("请求状态：status(%s)", taskResult.getStatus());
                if (status.isSuccess()) {
                    try {
                        String bodyStr = response.body().string();
                        if (TextUtils.isEmpty(bodyStr) && HttpUtils.isEmpty(bodyStr)) {
                            failure();
                        } else {
                            Pager<Order> pager = com.alibaba.fastjson.JSONObject.parseObject(bodyStr, Pager.class);
                            if(pager!=null && pager.getList()!=null && pager.getList().size()>0){
                                Logger.json(bodyStr);
                                taskResult.setStatus(TaskStatus.success);
                                taskResult.setResult(bodyStr);
                            }else{
                                setDataEmpty();
                            }

                        }
                    } catch (IOException e) {
                        failure();
                    } catch (JSONException e) {
                        Logger.d(e.getMessage(), e);
                    }
                }
            }
        };
    }

    /**
     * 获取订单列表
     *
     * @param state       N,订单状态
     * @param timeRange   N,过滤条件,时间起始范围,LONG类型表示逗号分隔,如[11111111111,22222222222]
     * @param lastOrderId N,最后一条订单ID
     * @return
     */
    @Deprecated
    public HttpTask getOrders2(final String merchantId,
                               final String state,
                               final String timeRange,
                               final String lastOrderId,
                               final String category) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                String serviceCode = "";
                if (TextUtils.isEmpty(merchantId)) {//查询当前登录商户的订单信息
                    params.put("timeRange", timeRange);
                    serviceCode = "management_order_getOrders";
                } else {//查询指定商户的订单信息
                    params.put("merchantNo", merchantId);
                    params.put("timeRange", timeRange);
                    serviceCode = "app_Order_getMerchantOrders";
                }

                if (!TextUtils.isEmpty(state)) {
                    params.put("tradeState", state);
                }
                if (!TextUtils.isEmpty(lastOrderId)) {
                    params.put("orderId", lastOrderId);
                }
                if (!TextUtils.isEmpty(category)) {
                    params.put("category", category);
                }
                get(getHttpRequest(), serviceCode, params);
            }
        };
    }

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    public HttpTask getOrderDetail(final String operatorId, final String orderId, final int orderType) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                String serviceCode;
                if (TextUtils.isEmpty(operatorId)) {
                    params.put("orderId", orderId);
                    params.put("type", String.valueOf(orderType));
                    serviceCode = "management_order_getOrderById";
                } else {
                    params.put("operatorId", operatorId);
                    params.put("orderId", orderId);
                    params.put("type", String.valueOf(orderType));
                    serviceCode = "management_order_getOrderByCondition";
                }
                get(getHttpRequest(), serviceCode, params);
            }
        };
    }

    /**
     * 获取机具列表
     *
     * @return
     */
    public HttpTask getPosModels() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "management_posApply_getPosModels", null);
            }
        };
    }

    /**
     * 获取机具品牌
     *
     * @return
     */
    public HttpTask getPosBrands() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "management_pos_getMfrs", null);
            }
        };
    }

    /**
     * 获取机具类型
     *
     * @param mfrNo 厂商编码
     * @return
     */
    public HttpTask getPosTypes(final String mfrNo) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("mfrNo", mfrNo);
                post(getHttpRequest(), "management_pos_getEmods", params);
            }
        };
    }

    /**
     * 机具激活
     *
     * @param mfrNo 厂商编码
     * @param modNo 类型编码
     * @param sn    机具sn
     * @return
     */
    public HttpTask postPosActivation(final String mfrNo, final String modNo, final String sn) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("vendorId", mfrNo);
                params.put("modelId", modNo);
                params.put("sn", sn);
                post(getHttpRequest(), "management_pos_register", params);
            }
        };
    }

    /**
     * 机具状态信息
     *
     * @param terminalNo 终端号
     * @return
     */
    public HttpTask getPostActivationState(final String terminalNo) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("terminalNo", terminalNo);
                post(getHttpRequest(), "management_pos_getCfg", params);
            }
        };
    }

    /**
     * 申请机具
     *
     * @param posModelCode POS机具类型编号
     * @param postalAddr   邮寄地址
     * @param recipient    收件人名称
     * @param mobile       收件人手机号
     * @return
     */
    public HttpTask postPosApplyRecords(final String posModelCode,
                                        final String vendorCode,
                                        final String posNum,
                                        final String postalAddr,
                                        final String recipient,
                                        final String mobile,
                                        final String remark) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("posModelCode", posModelCode);
                params.put("vendorCode", vendorCode);
                params.put("posNum", posNum);
                params.put("postalAddr", postalAddr);
                params.put("recipient", recipient);
                params.put("mobile", mobile);
                if (!TextUtils.isEmpty(remark)) {
                    params.put("remark", remark);
                }
                post(getHttpRequest(), "app_Pos_applyPOS", params);
            }
        };
    }

    /**
     * tradeCode:Y:交易码;
     * totalFee:Y:商户订单金额;
     * mobileNo:Y:手机号;
     * name:Y:姓名;
     * certNo:Y:证件号码;
     * bankAccount:Y:银行卡号;
     * subMechId:Y:卡友商户号;
     * orderDesc:N:商户订单描述;
     * orderDesc: Renew 续费  Upgrade 升级;
     */
    public HttpTask postPosApply(final String id,
                                 final String totalFee,
                                 final String mobileNo,
                                 final String name,
                                 final String certNo,
                                 final String bankAccount,
                                 final String subMechId,
                                 final String orderDesc,
                                 final String type,
                                 final String decision) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("tradeCode", "PlaceOrder");
                params.put("aplId", id);
                params.put("totalFee", totalFee);
                params.put("mobileNo", mobileNo);
                params.put("name", name);
                params.put("certNo", certNo);
                params.put("type", type);
                params.put("bankAccount", bankAccount);
                params.put("subMechId", subMechId);
                params.put("decision", decision);
                if (!TextUtils.isEmpty(orderDesc)) {
                    params.put("orderDesc", orderDesc);
                }
                post(getHttpRequest(), "management_mobilepay_pay", params);
            }
        };
    }

    /**
     * 机具申请订单查询
     *
     * @param merchOrderId
     * @return
     */
    public HttpTask posApplyQuery(final String merchOrderId, final String subMechId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("tradeCode", "QueryOrder");
                params.put("merchOrderId", merchOrderId);
                params.put("subMechId", subMechId);

                post(getHttpRequest(), "management_mobilepay_pay", params);
            }
        };
    }

    /**
     * 获取机具申请记录
     *
     * @return
     */
    public HttpTask getPosApplyRecords() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "app_Pos_getPosApplyRecords", null);
            }
        };
    }

    /**
     * 获取机具申请记录
     *
     * @return
     */
    public HttpTask getSingelPosApplyRecord(final String aplId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("aplId", aplId);
                get(getHttpRequest(), "management_posApply_getPosApplyRecord", params);
            }
        };
    }

    /**
     * 角色列表
     *
     * @return
     */
    public HttpTask getRoles() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                get(getHttpRequest(), "roles", params);
            }
        };
    }

    /**
     * 操作员列表
     *
     * @return
     */
    public HttpTask getOperators() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                get(getHttpRequest(), "operators", params);
            }
        };
    }



    /**
     * 删除操作员
     * 用户操作员内部ID(只修改时传入)
     * 用户操作员编号(只修改时传入)
     *
     * @param operatorId
     * @return
     */
    public HttpTask deleteOperator(final int operatorId) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                delete(getHttpRequest(), "/operator/"+operatorId, params);
            }
        };
    }

    /**
     * usrOprNo	LONG	N	用户操作员内部ID(只修改时传入)
     * usrOprId	STRING	N	用户操作员编号(只修改时传入)
     * usrOprNm	STRING	Y	操作员姓名
     * usrOprMbl	LONG	Y	手机号码
     *
     * @param operator 操作员新增修改
     * @return
     */
    public HttpTask newOperator(@NonNull final Operator operator) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("name", operator.getOperName());
                params.put("mobile", operator.getMobilePhone());
                params.put("refund", operator.getRefundAuth());
                params.put("roleType", operator.getRole().get(0).getRoleType());
//                if (!TextUtils.isEmpty(funcs))
//                    params.put("funcList", funcs);
//                if (!TextUtils.isEmpty(headImageUrl))
//                    params.put("headImage", headImageUrl);
                post(getHttpRequest(), "operators", params);
            }
        };
    }

    /**
     * 修改操作数据
     *
     * @param operator     只修改操作员名字
     * @return
     */
    public HttpTask modifyOperaor(@NonNull final Operator operator) {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                Map<String, Object> params = new HashMap<>();
                params.put("id", operator.getId());
                params.put("name", operator.getOperName());
                params.put("refund", operator.getRefundAuth());
                params.put("roleType", operator.getRole().get(0).getRoleType());
//                if (!TextUtils.isEmpty(funcs))
//                    params.put("funcList", funcs);
//                if (!TextUtils.isEmpty(headImageUrl))
//                    params.put("headImage", headImageUrl);
                put(getHttpRequest(), "operators", params);
            }
        };
    }

    /**
     * 查询操作员权限
     *
     * @return
     */
    public HttpTask getOperatorRights() {
        return new SimpleHttpTask() {
            @Override
            public void doTask() throws NetError {
                get(getHttpRequest(), "roles", null);
            }
        };
    }

    /**
     * form表单上传单张图片
     *
     * @param fileMimeType 文件MIME类型
     * @param uploadFile   待上传文件
     * @return
     */
    public HttpTask uploadFile(final String fileMimeType, File uploadFile) {
        return new UploadTask(uploadFile) {
            @Override
            public void doTask() throws NetError {
                KeyValue keyValue = new KeyValue(fileMimeType, getUploadFile());
                Map<String, KeyValue> keyValueMap = new HashMap<>();
                keyValueMap.put("uploadFile", keyValue);
                Uri uri = Uri.parse(HttpUtils.SERVER_ADDRESS);
                getHttpRequest().upload(HttpMethod.POST, String.format("http://%s:%s/file/upload?type=99",
                        uri.getHost(), uri.getPort()), keyValueMap);
            }
        };
    }

    /**
     * 下载文件
     *
     * @param savePath    下载文件保存路径
     * @param downlaodUrl 文件网络地址
     * @return
     */
    public HttpTask download(String savePath, final String downlaodUrl, String fileName) {
        return new DownloadTask(savePath, fileName) {
            @Override
            public void doTask() throws NetError {
                getHttpRequest().download(HttpMethod.GET, downlaodUrl);
            }
        };
    }

    /**
     * 图片加载网络地址
     *
     * @param imageFileName
     * @return
     */
    public String getImageLoadUrl(String imageFileName) {
        return MessageFormat.format("http://{0}/name/gen320{1}", IMAGE_LOAD_IP, imageFileName);//测试内网
    }

    /**
     * 图片加载网络地址
     *
     * @param imageFileName
     * @return
     */
    public String getBigImageLoadUrl(String imageFileName) {
        if (TextUtils.isEmpty(imageFileName)) {
            imageFileName = "";
        }
        return MessageFormat.format("http://{0}/name/{1}", IMAGE_LOAD_IP, imageFileName);//测试内网
    }

    /**
     * 返回帮助页面地址
     *
     * @return
     */
    public String getHelperUrl() {
        return MessageFormat.format("http://{0}/app-server/pages/help/index.html", HELP_HTML_IP);
    }

    /**
     * 卡友商户通业务用户注册协议
     *
     * @return
     */
    public String getRegisterAgreementUrl() {
        return MessageFormat.format("http://{0}/name/register_agreement_index.html", PROCTOL_HTML_IP);
    }

    public final static Map<String, String[]> funcMap = new HashMap<>();

    static {//签名字段配置

    }



    /**
     * @param request
     * @param params
     * @throws NetError
     */
    private void get(IHttpRequest request, String serviceCode, Map<String, Object> params) throws NetError {

       // Signature signature = generateSignature(params, serviceCode);
       // String requestUrl = createRequestUrl(signature);
        String requestUrl = createRequestUrl(serviceCode);
        /*requestUrl = MessageFormat.format("{0}?{1}={2}",
                requestUrl,
                "params",
                signature.params);*/
        String paramStr="";
        if(params!=null && params.size()>0){
            for(Map.Entry<String ,Object> entry :params.entrySet()){
                if(StringUtil.isEmpty(paramStr)){
                    paramStr+=(entry.getKey()+"="+entry.getValue());
                }else{
                    paramStr+=("&"+entry.getKey()+"="+entry.getValue());
                }
            }
            requestUrl = MessageFormat.format("{0}?{1}", requestUrl, paramStr);
        }
        request.get(requestUrl);
        Logger.i(requestUrl);
    }

    /**
     * @param request
     * @param params
     * @throws NetError
     */
    private void get(IHttpRequest request, String serviceCode, String paramsKey, Map<String, Object> params) throws NetError {

        //Signature signature = generateSignature(paramsKey, params, serviceCode);
        String requestUrl = createRequestUrl(serviceCode);
        //String requestUrl = createRequestUrl(signature);
        /*requestUrl = MessageFormat.format("{0}?{1}={2}",
                requestUrl,
                TextUtils.isEmpty(paramsKey) ? "params" : paramsKey,
                signature.params);*/
        String paramStr="";
        if(params!=null && params.size()>0){
            for(Map.Entry<String ,Object> entry :params.entrySet()){
                if(StringUtil.isEmpty(paramStr)){
                    paramStr+=(entry.getKey()+"="+entry.getValue());
                }else{
                    paramStr+=("&"+entry.getKey()+"="+entry.getValue());
                }
            }
            requestUrl = MessageFormat.format("{0}?{1}", requestUrl,paramStr);
        }
        request.get(requestUrl);
        Logger.i(requestUrl);
    }

    /**
     * @param request
     * @param serviceCode
     * @param params
     * @throws NetError
     */
    private void post(IHttpRequest request, String serviceCode, Map<String, Object> params) throws NetError {

        //Signature signature = generateSignature(params, serviceCode);
       // String requestUrl = createRequestUrl(signature);
        String requestUrl = createRequestUrl(serviceCode);
        Map<String, String> postParams = new HashMap<>();
        //postParams.put("params", signature.params);
        postParams=(Map)params;
        request.post(requestUrl, JSON.toJSONString(postParams));

        Logger.d("request JSON=>%s\n params=>%s", requestUrl, JSON.toJSONString(postParams));
    }

    /**
     * @param request
     * @param serviceCode
     * @param params
     * @throws NetError
     */
    private void post(IHttpRequest request, String serviceCode, String paramsKey, Map<String, Object> params) throws NetError {
        paramsKey = TextUtils.isEmpty(paramsKey) ? "params" : paramsKey;

       //Signature signature = generateSignature(paramsKey, params, serviceCode);
        //String requestUrl = createRequestUrl(signature);
        String requestUrl = createRequestUrl(serviceCode);
        Map<String, String> postParams = new HashMap<>();
       // postParams.put(paramsKey, signature.params);
        postParams=(Map)params;
        request.post(requestUrl, postParams);
        Logger.d("request JSON=>%s\n params=>%s", requestUrl,JSON.toJSONString(postParams));
    }

    /**
     * @param request
     * @param serviceCode
     * @param params
     * @throws NetError
     */
    private void put(IHttpRequest request, String serviceCode, Map<String, Object> params) throws NetError {
        //params = HttpUtils.getCommonParams(params);
        //Signature signature = generateSignature(params, serviceCode);
        String requestUrl = createRequestUrl(serviceCode);
        //requestUrl = MessageFormat.format("{0}?params={1}", requestUrl, signature.params);
        Map<String, String> postParams = new HashMap<>();
        //postParams.put("params", signature.params);
        postParams=(Map)params;
        request.put(requestUrl, JSON.toJSONString(postParams));
        Logger.i(requestUrl);
    }

    /**
     * @param request
     * @param serviceCode
     * @param params
     * @throws NetError
     */
    private void delete(IHttpRequest request, String serviceCode, Map<String, Object> params) throws NetError {
        //params = HttpUtils.getCommonParams(params);
        //Signature signature = generateSignature(params, serviceCode);
        String requestUrl = createRequestUrl(serviceCode);
       // requestUrl = MessageFormat.format("{0}?params={1}", requestUrl, signature.params);
        String paramStr="";
        if(params!=null && params.size()>0){
            for(Map.Entry<String ,Object> entry :params.entrySet()){
                if(StringUtil.isEmpty(paramStr)){
                    paramStr+=(entry.getKey()+"="+entry.getValue());
                }else{
                    paramStr+=("&"+entry.getKey()+"="+entry.getValue());
                }
            }
            requestUrl = MessageFormat.format("{0}?{1}", requestUrl,paramStr);
        }
        request.delete(requestUrl);
        Logger.i(requestUrl);
    }


    private final static String version = "1.0";//服务注册时定义的服务版本名
    private final static String accCode = "kayou";//调用方注册的账户ID
    private final static String appCode = "merchantApp";//调用方注册的应用ID
    private final static String accessKeyId = "8cd59103455345d1bba851bbafcdbb08";//用户从ESB申请的密钥的索引
    private final static Algorithm signType = Algorithm.HMAC;//签名算法类型，目前支持类型为：DES,HMAC,RSA,MD5
    private final static String privateKey = "b3f2399a288f4f10aad1bfa6891fc5f6";

    /**
     * 生成参数签名
     *
     * @param params      请求参数
     * @param serviceCode 接口代码
     * @return
     */
    public Signature generateSignature(Map<String, Object> params, String serviceCode) {
        if (params == null) {
            params = new HashMap<>();
        }
        Session session = Session.load();
        if (session != null) {
            if (!TextUtils.isEmpty(session.getMobile())) {
                params.put("AID", session.getMobile());
            }
            if (!TextUtils.isEmpty(session.getAuthToken())) {
                params.put("TOKEN", session.getAuthToken());
            }
                params.put("UID", session.getUserId());
        }
        //params.put("APP_TYPE", "MERCHANT_APP");
        return generateSignature(null, params, serviceCode);
    }

    /**
     * 生成参数签名
     *
     * @param paramsKey
     * @param params
     * @param serviceCode
     * @return
     */
    public Signature generateSignature(String paramsKey, Map<String, Object> params, String serviceCode) {
        Signature signature = new Signature();
        signature.timestamp = new Date().getTime();
        signature.serviceCode = serviceCode;
        Map<String, Object> signParams = new HashMap<>();
        /*signParams.put("version", version);
        signParams.put("accCode", accCode);
        signParams.put("appCode", appCode);
        signParams.put("accessKeyId", accessKeyId);
        signParams.put("signType", signType.getCode());
        signParams.put("algorithm", signType.getAlgorithm());
        signParams.put("timestamp", String.valueOf(signature.timestamp));
        signParams.put("serviceCode", serviceCode);*/
        signature.params = JSON.toJSONString(params);
        //signParams.put(TextUtils.isEmpty(paramsKey) ? "params" : paramsKey, signature.params);
        try {
           // signature.signatrue = SignatureUtil.sign(signType, signParams, privateKey);
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
        }
        return signature;
    }

    public String createRequestUrl(String  serviceCode) {
        String requestUrl = "{0}/{1}";
        requestUrl = MessageFormat.format(requestUrl,
                HttpUtils.SERVER_ADDRESS,
               serviceCode);
        return requestUrl;
    }

    /**
     * 创建请求URL
     * serviceCode 服务名称，服务系统在后台服务管理注入的名称
     * version 服务注册时定义的服务版本名
     * accCode 调用方注册的账户ID
     * appCode 调用方注册的应用ID
     * accessKeyId 用户从ESB申请的密钥的索引
     * signature 签名结果串，关于签名的计算方法，请参见<签名机制>。
     * signType 签名算法类型，目前支持类型为：DES,HMAC,RSA,MD5
     * algorithm 签名的具体算法，其中各加密类型下具体如下：
     * DES:DES,DESede,AES,Blowfish,RC2,RC4(ARCFOUR)
     * HMAC: HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
     * RSA:RSA
     * MD5:MD5
     * 推荐使用：HmacSHA256
     * timestamp 请求的时间戳。值为当前距离1970年1月1日原点的毫秒数
     * requestId 请求唯一标识，可用于消息跟踪。如果用户不填写，OpenAPI会为每一个请求自动生成唯一标识字符串。
     *
     * @return
     */

    public String createRequestUrl(Signature signature) {
        return createRequestUrl(signature, HttpUtils.SERVER_ADDRESS);
    }

    public String createRequestUrl(Signature signature, String serverAddress) {
        String requestUrl = "{0}/{1}";
        requestUrl = MessageFormat.format(requestUrl,
                serverAddress,
                signature.serviceCode);
       /* String requestUrl = "{0}/{1}/{2}/{3}/{4}/{5}/{6}/{7}/{8}/{9}";
        requestUrl = MessageFormat.format(requestUrl,
                serverAddress,
                signature.serviceCode,
                version,
                accCode,
                appCode,
                accessKeyId,
                signature.signatrue,
                signType.getCode(),
                signType.getAlgorithm(),
                String.valueOf(signature.timestamp));*/
        return requestUrl;
    }


    private static class SingletonHolder {
        private static HttpService instance = new HttpService();
    }

    public static HttpService getInstance() {
        return SingletonHolder.instance;
    }

    private HttpService() {

    }

    public class Signature {
        private String signatrue;
        private String params;
        private long timestamp;
        private String serviceCode;

        public String getSignatrue() {
            return signatrue;
        }

        public String getParams() {
            return params;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getServiceCode() {
            return serviceCode;
        }
    }


}
