package com.hq.app.olaf.ui.activity.shoukuan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cardinfo.reddy.util.Validations;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.activity.about.ShoukuanMaActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.util.Const;
import com.hq.app.olaf.util.StringUtil;
import com.hq.app.olaf.util.ToastHelper;
import com.hq.component.annotation.Layout;
import com.hq.component.network.KLog;
import com.hq.app.library.scanner.CaptureManager;
import com.hq.app.olaf.R;
import com.hq.app.library.scanner.BarcodeCallback;
import com.hq.app.library.scanner.BarcodeResult;
import com.hq.app.library.scanner.CompoundBarcodeView;
import com.google.zxing.ResultPoint;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.component.utils.JSON;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liaob on 2016/5/23.
 */
@Layout(layoutId = R.layout.activity_continuous_scanner)
public class ScannerActivity extends HqPayActivity{
    private static final String TAG = ScannerActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.barcode_scanner)
    CompoundBarcodeView barcodeView;

    public static  final int BARCODE_RESULT=2016;
    public static  final String BARCODE_TAG="BARCODE";
    private boolean isComplete;//是否扫码完成

    private  String money;
    String scanner_type;
    String errorPayType="扫码支付";

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(barcodeView==null)
            {
                return;
            }
            if (result!=null&&result.getText() != null) {
               // barcodeView.setStatusText(result.getText());
            }
            //Added preview of scanned barcode
//            ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
//            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
            if(isComplete){
                return;
            }else
            {
                isComplete=true;
            }
          /*  Intent intent=new Intent();
            intent.putExtra(BARCODE_TAG,result.getResult().getText());
            setResult(BARCODE_RESULT,intent);*/
            KLog.i("扫码成功"+result.getText());
            pay(result.getResult().getText());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarWithoutListener(toolbar);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
//        View parentView = contentFrameLayout.getChildAt(0);
//        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
//            parentView.setFitsSystemWindows(true);
//        }
        if (ContextCompat.checkSelfPermission(ScannerActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ScannerActivity.this,
                            new String[]{Manifest.permission.CAMERA},1
                            );

            }else{
                initScan();
            }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward(ShoukuanActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });
    }

    private void initScan() {
//        initToolbar(toolbar);
//        title.setText("扫码支付");

        Bundle bundle = getIntent().getExtras();
        money = bundle.getString("money");
        barcodeView.setMoneyViewText(money);
       /* CaptureManager capture = new CaptureManager(this, barcodeView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode(callback);*/
       barcodeView.decodeContinuous(callback);
    }

    protected void pay(String authCode) {
        KLog.i("收到扫码确认信息");
        if(Validations.isAlipayPayCode(authCode)) {
            scanner_type= Const.ALI_PAY_TYPE;
            KLog.i("请求支付宝扫码交易");
            errorPayType="支付宝支付";

        }else if(Validations.isTenpayPayCode(authCode) ) {
            KLog.i("请求微信扫码交易");
            scanner_type=Const.WEI_CHART_TYPE;
            errorPayType="微信支付";
        }else {
            //ToastHelper.showToast("暂不支持该付款码类型",getBaseContext());
            this.showTipsDialog("暂不支持该付款码类型", "返回首页", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                    ScannerActivity.this.forward(NewIndexActivity.class);
                }
            }, "重试", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                    ScannerActivity.this.setComplete(false);
                }
            }, false, null);
            return;
        }

        new ScannerPay(ScannerActivity.this, new BaseSale.CallBack() {

            @Override
            public void onError(String erroMsg,boolean isShow) {
                dismissLoadingDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(ScannerPayResultActivity.RETURN_CODE, -1);
                bundle.putString(ScannerPayResultActivity.SANNER_TYPE,scanner_type);
                bundle.putString(ScannerPayResultActivity.CASH_NUMBER, money);
                bundle.putString(ScannerPayResultActivity.RETURN_MESSAGE, StringUtil.isEmpty(erroMsg)?errorPayType+"失败" :erroMsg);
                if(isShow) {
                    forward(ScannerPayResultActivity.class, bundle);
                    finish();
                }
            }

            @Override
            public void onSucess(String json) {
                dismissLoadingDialog();
                ScannerOrderResp scannerOrderResp = JSON.parseObject(json, ScannerOrderResp.class);
                if (scannerOrderResp != null) {
                    //支付成功
                    Bundle bundle = new Bundle();
                    bundle.putString(ScannerPayResultActivity.SANNER_TYPE,scanner_type);
                    bundle.putString(ScannerPayResultActivity.CASH_NUMBER, money);
                    bundle.putInt(ScannerPayResultActivity.RETURN_CODE, 1);
                    bundle.putString(ScannerPayResultActivity.RETURN_MESSAGE,   "");
                    bundle.putParcelable(ScannerPayResultActivity.RETURN_DATA, scannerOrderResp);
                    forward(ScannerPayResultActivity.class, bundle);
                    finish();
                }
            }
        },authCode, money);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==1){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScan();

            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(ScannerActivity.this,
                        Manifest.permission.CAMERA)) {

                    new InfoDialog(this, "提示", "收款需要获得相机的权限")
                            .setContentColor(R.color.black)
                            .setConfirm("确定", new InfoDialog.OnClickListener() {
                                @Override
                                public void onClick(InfoDialog infoDialog, View button) {
                                    finish();
                                }
                            }).show();

                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * 设置返回键事件
         */
        if(keyCode==KeyEvent.KEYCODE_BACK){
            forward(ShoukuanActivity.class);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            finish();
            return true;
        }
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
