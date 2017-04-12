package com.hq.app.olaf.ui.activity.shoukuan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.app.olaf.net.HttpMessage;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.ui.enums.PayStatusEnum;
import com.hq.app.olaf.util.DoubleParseUtil;
import com.hq.app.olaf.util.IPUtil;
import com.hq.app.olaf.util.NetWorkUtil;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.util.StringUtil;
import com.hq.app.olaf.util.ToastHelper;

/**
 * Created by Administrator on 2017/2/8.
 */

public class ScannerPay extends BaseHttpSale {

    private  ScannerActivity activity;

    protected CallBack callBack;

    private HttpTask pay;
    private HttpService httpService;

    public ScannerPay(Activity context, CallBack callBack,String authCode, String amout) {
        super(context, callBack);
        final  String  money=amout;
        this.activity =(ScannerActivity)context;
        this.callBack=callBack;
        httpService = HttpService.getInstance();
        //判断是否有网络
        if(!NetWorkUtil.isNetworkAvailable(context)){
            //ToastHelper.showToast("无网络连接", context);
            if (callBack != null) {
                callBack.onError("无网络连接",false);
            }
            showAlertDialog(activity,"无网络连接");
            return;
        }

        showLoadingDialog("交易处理中");

        //发起支付请求
        scannerPay(authCode,money);

    }



    private void scannerPay( String authCode,final  String amount) {

        //下单金额以分为单位
        final  double totalAmount = ((Double) (DoubleParseUtil.parse(amount) * 100)).intValue();
             /*ip地址*/
        String ipAddress = IPUtil.getIPAddress(activity);
        // 得到用户付款码，调用后台接口发起支付
        pay = httpService.tradeOrders(authCode, totalAmount, ipAddress);

        NetTools.excute(pay, null, new NetTools.CallBack() {

            @Override
            public void onComplete(final  TaskResult taskResult) {

               if(taskResult.isTimeOut()) {//网络超时
                    //返回成功提示
                    showSuccessTips();
                    //code 400 并且key 400400 中断轮询
                }else if(taskResult.getStatus().isFailure() ){
                   dismissLoadingDialog();
                   showAlertDialog(activity,TextUtils.isEmpty(taskResult.getError())?"调用服务出错":taskResult.getError());
                }else if (taskResult.isUnauthorized() || taskResult.getCode() > 400) {
                   dismissLoadingDialog();
                   RequestFailedHandler.handleFailed(activity.getRootView(),taskResult);
               }
               else if (taskResult.isSuccess() && taskResult.getCode() >= 200 && taskResult.getCode() < 300) {
                   final ScannerOrderResp orderResp = com.alibaba.fastjson.JSON.parseObject((String) taskResult.getResult(), ScannerOrderResp.class);

                   if(StringUtil.isEmpty(orderResp.getTradeState())){return;}
                   //成功
                    if(orderResp.getTradeState().equals(PayStatusEnum.SUCCESS.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSucess((String) taskResult.getResult());
                                return;
                            }
                        });
                    }

                    //等待客户端输入密码
                    if(orderResp.getTradeState().equals(PayStatusEnum.WAIT_PAY.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            changeDialogText(StringUtil.isEmpty(orderResp.getTips())?"等待客户输入密码":orderResp.getTips());
                            }
                        });
                        //发起轮询
                        lunxun(amount,orderResp.getTradeId());
                    }

                    //正在查询交易结果
                    if(orderResp.getTradeState().equals(PayStatusEnum.UNKNOW.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            changeDialogText(StringUtil.isEmpty(orderResp.getTips())?"正在查询交易结果":orderResp.getTips());
                            }
                        });

                        //发起轮询
                        lunxun(amount,orderResp.getTradeId());
                    }


                    //失败
                    if(orderResp.getTradeState().equals(PayStatusEnum.FAIL.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(StringUtil.isEmpty(orderResp.getErrCodeDes())?orderResp.getTips():orderResp.getErrCodeDes(),true);
                                return;
                            }
                        });
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(RequestFailedHandler.handleFailedMsg(activity.getRootView(),taskResult),false);
                            return;
                        }
                    });
                    showFailTips("");
                }

            }
        });

    }


    private  void lunxun(String amount,String ordId){
        //发起轮询
        Message msg = new Message();
        msg.what = ORDER_QUERY;
        Bundle bundle = new Bundle();
        bundle.putString(OLD_ORDERID, ordId);
        bundle.putString(TOTALMONEY, amount);
        msg.setData(bundle);
        handler.sendMessageDelayed(msg, queryDelay);

    }


}
