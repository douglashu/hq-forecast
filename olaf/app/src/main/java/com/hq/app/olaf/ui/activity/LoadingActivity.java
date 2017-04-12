package com.hq.app.olaf.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.component.annotation.Layout;
import com.hq.component.db.DbManager;
import com.hq.component.db.DbManagerImpl;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.io.InputStream;
import com.hq.app.olaf.R;
import okio.BufferedSource;
import okio.Okio;
/**
 * Created by liaob on 2016/4/26.
 */
@Layout(layoutId = R.layout.activity_loading)
public class LoadingActivity extends HqPayActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = getWindowManager();
        getApp().setWindowManager(windowManager);
        /*forward(NewImportActivity.class);
        finish();*/
        /** 设置是否对日志信息进行加密, 默认false(不加密).*/
        //MobclickAgent.enableEncrypt(true);//6.0.0版本及以后
        //MobclickAgent.onEvent(this, "ClientLaunch");
        if (Build.VERSION.SDK_INT >= 23) {
            if (requestStoragePermission()) {
                initDatabase();
            }
        } else {
            initDatabase();
        }
    }

    @Override
    public void onStorageGranted() {
        Logger.d("onStorageGranted获得授权");
//        showSnackBar("onStorageGranted获得授权");
        initDatabase();
    }

    @Override
    public void onStorageDenied() {
        Logger.d("onStorageDenied授权拒绝");
        initDatabase();
    }

    private void initDatabase() {
        DbManager dbManager = DbManagerImpl.getInstance();
        if (dbManager == null) {
            DbManagerImpl.init(this, HqPayApplication.getAppContext().getDaoConfig());
        }
        queryMerchantInfo();
    }


    public void queryMerchantInfo() {
        Session session = Session.load();
        if (session != null && !TextUtils.isEmpty(session.getAuthToken())) {
            toIndex();
        } else {
            toLogin();
        }
    }


    /**
     *
     */
    private void toLogin() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //判断是否进入欢迎界面
                        forward(LoginActivity.class);
                        finish();
                    }
                }, 1);
    }

    /**
     *checkDownDate
     */
    private void toIndex() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //判断是否进入欢迎界面
                        forward(NewIndexActivity.class);
                        finish();
                    }
                }, 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getApp().exitApp();
    }

    private void loadCitysData() {
        InputStream jsonStream = null;
        try {
            jsonStream = getAssets().open("provs_citys_new.json");
            BufferedSource source = Okio.buffer(Okio.source(jsonStream));
            String json = source.readUtf8();
            JSONObject jsonObject = JSON.parseObject(json);
            getApp().saveCache("provs_citys_new", jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                queryMerchantInfo();
            }
        }
    }


}
