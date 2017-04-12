package com.hq.app.olaf.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.bean.merchant.Merchant;
import com.hq.app.olaf.ui.widget.InfoDialog;

/**
 * Created by huwentao on 16/9/23.
 */

public class IndexDialogUtil {
    /**
     * 过件提示Dialog
     *
     * @param context
     * @param session
     */
    public static void showDialog(final Context context, Session session) {
        Merchant merchant = null;//session.getSettle();
        if (merchant != null && !TextUtils.isEmpty(merchant.getState())) {
                if ("1".equals(merchant.getState())) {
                    new InfoDialog(context, "提示", "商家认证还有未完成信息")
                        .setContentColor(R.color.black)
                        .setConfirm("继续填写", new InfoDialog.OnClickListener() {
                            @Override
                            public void onClick(InfoDialog infoDialog, View button) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("isCreateAccount", true);
                                forward(context, NewIndexActivity.class, bundle);
                            }
                        }).show();
            }
            if ("4".equals(merchant.getState())) {
                new InfoDialog(context, "提示", "商家认证审核不通过")
                        .setContentColor(R.color.black)
                        .setConfirm("重新认证", new InfoDialog.OnClickListener() {
                            @Override
                            public void onClick(InfoDialog infoDialog, View button) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("isCreateAccount", true);
                                forward(context, NewIndexActivity.class, bundle);
                            }
                        }).show();
            }
            if ("2".equals(merchant.getState())) {
                new InfoDialog(context, "提示", "商家认证审核中，请耐心等待")
                        .setContentColor(R.color.black)
                        .setConfirm("商家认证", new InfoDialog.OnClickListener() {
                            @Override
                            public void onClick(InfoDialog infoDialog, View button) {
                                infoDialog.dismiss();
                            }
                        }).show();
            }
        }else if(session.getRole().getRoleName().equals("店员")){
            new InfoDialog(context, "提示", "你尚未拥有该功能权限！")
                    .setContentColor(R.color.black)
                    .setConfirm("确定", new InfoDialog.OnClickListener() {
                        @Override
                        public void onClick(InfoDialog infoDialog, View button) {

                        }
                    }).show();
        } else {
            new InfoDialog(context, "提示", "该功能尚在开发中，敬请期待！")
                    .setContentColor(R.color.black)
                    .setConfirm("确定", new InfoDialog.OnClickListener() {
                        @Override
                        public void onClick(InfoDialog infoDialog, View button) {

                        }
                    }).show();
        }
    }

    private static void forward(Context context, Class<? extends HqPayActivity> aClass, Bundle bundle) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent, bundle);
    }


}
