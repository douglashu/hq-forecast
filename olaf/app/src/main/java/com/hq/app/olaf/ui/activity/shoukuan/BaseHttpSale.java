package com.hq.app.olaf.ui.activity.shoukuan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.ui.activity.order.OrderActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.ui.enums.PayStatusEnum;
import com.hq.app.olaf.ui.enums.TradeTypeEnum;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.util.StringUtil;
import com.hq.component.network.KLog;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.app.olaf.net.HttpService;

import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.util.RequestFailedHandler;


/**
 * Created by Emir on 2016/8/17.
 */
public abstract class BaseHttpSale extends BaseSale {
    protected static  ScannerActivity activity;
    protected boolean isQuering=false;
    protected int queryDelay=5000;
    protected int querySumTimes=12;

    protected String amout;

    public boolean isQuering() {
        return isQuering;
    }

    protected int queryTimes=0;
    public BaseHttpSale(Activity activity, CallBack callback){
        super(activity,callback);
        this.activity=(ScannerActivity)activity;
    }

    protected void queryOrder(final  String oldOrderId){
        isQuering=true;
        queryTimes++;
        HttpTask query= HttpService.getInstance().orderQuery(oldOrderId);
        NetTools.excute(query, new NetTools.CallBack() {
            @Override
            public void onComplete(final  TaskResult taskResult) {
                super.onComplete(taskResult);
                if (taskResult.isSuccess() && taskResult.getCode() >= 200 && taskResult.getCode() < 300) {
                    final  String result = (String) taskResult.getResult().toString();
                    final ScannerOrderResp orderResp = com.alibaba.fastjson.JSON.parseObject((String) taskResult.getResult(), ScannerOrderResp.class);

                    if(TextUtils.isEmpty(result))
                    {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(TextUtils.isEmpty(result)){
                                    taskResult.setError("查询订单失败（Server）");
                                }
                                callBack.onError(taskResult.getError().toString(),false);
                                return;
                            }
                        });
                    }else if(orderResp.getTradeState().equals(PayStatusEnum.WAIT_PAY.getCode())) {

                        if (queryTimes >= querySumTimes) {
                            callBack.onError("查询时间超时",false);
                            KLog.d("查询时间超时");
                            isQuering = false;
                            dismissLoadingDialog();
                            showFailTips("");
                        } else {

                            KLog.d("第" + queryTimes + "次查询");
                            Message msg = new Message();
                            msg.what = ORDER_QUERY;
                            Bundle bundle = new Bundle();
                            bundle.putString(OLD_ORDERID, oldOrderId);
                            msg.setData(bundle);
                            handler.sendMessageDelayed(msg, queryDelay);
                        }
                    }else if(orderResp.getTradeState().equals(PayStatusEnum.UNKNOW.getCode())){
                        showSuccessTips();
                    }else if(orderResp.getTradeState().equals(PayStatusEnum.REVOKED.getCode())//已撤销
                     || orderResp.getTradeState().equals(PayStatusEnum.CLOSED.getCode() )) {//已关闭

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError("交易"+PayStatusEnum.getPayStatus(orderResp.getTradeState()).getDesc(),true);
                                return;
                            }
                        });

                    } //失败
                    else if(orderResp.getTradeState().equals(PayStatusEnum.FAIL.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(StringUtil.isEmpty(orderResp.getErrCodeDes())?orderResp.getTips():orderResp.getErrCodeDes(),true);
                                return;
                            }
                        });
                    }//成功
                   else if(orderResp.getTradeState().equals(PayStatusEnum.SUCCESS.getCode()) ) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSucess((String) taskResult.getResult());
                                return;
                            }
                        });
                    }else{
                        showFailTips("");
                    }
                }else if (taskResult.isUnauthorized() ) {//其他人登录账号下线
                    dismissLoadingDialog();
                    RequestFailedHandler.handleFailed(activity.getRootView(),taskResult);
                }else {

                    if (queryTimes >= querySumTimes) {
                        callBack.onError("查询时间超时",false);
                        KLog.d("查询时间超时");
                        isQuering = false;
                        dismissLoadingDialog();
                        showSuccessTips();
                    } else {
                        KLog.d("第" + queryTimes + "次查询");
                        Message msg = new Message();
                        msg.what = ORDER_QUERY;
                        Bundle bundle = new Bundle();
                        bundle.putString(OLD_ORDERID, oldOrderId);
                        msg.setData(bundle);
                        handler.sendMessageDelayed(msg, queryDelay);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeDialogText("正在查询交易结果...");
                                KLog.i("未知状态订单，继续查询");
                            }
                        });

                    }

                }


            }
        });
    }


    protected final int ORDER_QUERY=10;
    protected final String OLD_ORDERID="ORDERID";
    protected final String TOTALMONEY="TOTALMONEY";
    protected Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case ORDER_QUERY:
                    String orderId= msg.getData().getString(OLD_ORDERID);
                    amout= msg.getData().getString(TOTALMONEY);
                    queryOrder(orderId);
                    break;
            }
        }
    };
    public void close(){
        handler.removeMessages(ORDER_QUERY);
    }


    public  static   void forwardScan(String amout){

        Bundle bundle=new Bundle();
        bundle.putString("money",amout);
        activity.forwardForResult(ScannerActivity.class,bundle, ScannerActivity.BARCODE_RESULT);
    }

    public  static  void forwardOrder( ){
        activity.forward(OrderActivity.class);
    }

    protected void showFailTips( String tips ){
        tips=(TextUtils.isEmpty(tips)?"出了点小问题，请再试一次":tips);
        activity.showTipsDialog(tips, "返回首页", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                activity.forward(NewIndexActivity.class);
                activity.finish();
            }
        }, "重试", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dismissLoadingDialog();
                dialog.dismiss();
                activity.setComplete(false);
                //forwardScan(amout);
            }
        }, false, null);
        return;
    }


    protected void showSuccessTips(   ){
        activity.showTipsDialog("可能交易已经成功，请前往交易记录查看!", "返回首页", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                activity.forward(NewIndexActivity.class);
                activity.finish();
            }
        }, "查看交易记录", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                forwardOrder();
                activity.finish();
            }
        }, false, null);
        return;
    }

}
