package com.hq.app.olaf.ui.activity.shoukuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.R;
import com.orhanobut.logger.Logger;


/**
 * Created by jyl on 2016/5/11.
 */
public class BaseSale {

    protected Activity context;
    protected CallBack callBack;

    public BaseSale(Activity context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;

    }

    public interface CallBack {
        void onError(String erro,boolean isShow);

        void onSucess(String json);
    }


    private  MaterialDialog materialDialog;

    public  void dismissLoadingDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
    }
    public void changeDialogText(String title) {
        if (materialDialog != null&&!TextUtils.isEmpty(title)) {
            materialDialog.show();
            materialDialog.setTitle(title);
        }
    }

    public void showAlertDialog(final ScannerActivity activity,String message) {
        dismissLoadingDialog();
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.setComplete(false);
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                activity.setComplete(false);
            }
        });
        builder.show();
    }

    /**
     * 显示弹窗
     * @param content
     */
    public void showLoadingDialog(String content) {
        try
        {

            if (materialDialog == null) {
                materialDialog = new MaterialDialog.Builder(context)
                        .title("提示")
                        .content(content)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false).build();
                materialDialog.setCancelable(false);
                //设置高度
                //setHeight(materialDialog);
                materialDialog.show();
            } else {
                //设置高度
                //setHeight(materialDialog);
                materialDialog.setTitle("提示");
                materialDialog.setContent(content);
                materialDialog.show();
            }
        }catch (Exception e)
        {
            Logger.wtf(e.toString());
        }


    }


    private void setHeight(MaterialDialog materialDialog){
        WindowManager m =  materialDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = materialDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); //高度设置为屏幕的0.3
        materialDialog.getWindow().setAttributes(p); //设置生效
    }

}
