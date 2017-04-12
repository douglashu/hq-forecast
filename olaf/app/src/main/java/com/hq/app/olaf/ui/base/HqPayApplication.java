package com.hq.app.olaf.ui.base;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.service.DaemonService;
import com.hq.app.olaf.ui.activity.order.OrderDetailActivity;
import com.hq.app.olaf.util.StringUtil;
import com.hq.component.base.AppActManager;
import com.hq.component.base.BaseActivity;
import com.hq.component.base.CrashHandler;
import com.hq.component.cache.IMemoryCache;
import com.hq.component.cache.MemoryCache;
import com.hq.component.db.DBUtil;
import com.hq.component.db.DbException;
import com.hq.component.db.DbManager;
import com.hq.component.db.DbManagerImpl;
import com.hq.component.network.HTTP;
import com.hq.component.network.KLog;
import com.hq.component.network.net.cookie.NetCookieJar;
import com.hq.component.network.net.cookie.PersistentCookieStore;
import com.hq.component.network.net.interceptors.LoggingInterceptors;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.sign.EncryptManager;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
/*import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;*/

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.internal.tls.OkHostnameVerifier;

/**
 * Created by huwentao on 16/6/13.
 */
public class HqPayApplication extends MultiDexApplication {
    private String crashLogDirectory = "/merchant/crash/";
    private static HqPayApplication mContext = null;
    private IMemoryCache<String, Object> caches = MemoryCache.lruCache(100);
    private WindowManager windowManager;
    private String DB_PATH = "";
    private final static String DB_NAME = "app_data.db";
    private DbManager.DaoConfig daoConfig;
    public static final String UPDATE_STATUS_ACTION = "com.hq.app.olaf.action.UPDATE_STATUS";
    public static final String HAVE_NEW_MESSAGE = "HAVE_NEW_MESSAGE";
    private boolean isRunInBackground = true;
    private int runningCount;

    public boolean isRunInBackground() {
        return isRunInBackground;
    }

    public void setRunInBackground(boolean runInBackground) {
        isRunInBackground = runInBackground;
    }

    /**
     * @param obj
     */
    public void saveCache(Object obj) {
        caches.put(obj.getClass().getName(), obj);
    }

    /**
     * @param key
     * @param obj
     */
    public void saveCache(String key, Object obj) {
        caches.put(key, obj);
    }


    /**
     * @param key
     * @return
     */
    public Object getCache(String key) {
        return caches.get(key);
    }

    /**
     * @param key
     * @return
     */
    public <T> T getCache(String key, Class<T> tClass) {
        Object o = getCache(key);
        if (tClass.isInstance(o)) {
            return tClass.cast(o);
        }
        return null;
    }

    /**
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getCache(Class<T> tClass) {
        Object o = getCache(tClass.getName());
        if (tClass.isInstance(o)) {
            return tClass.cast(o);
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public Object getCacheClear(String key) {
        return caches.remove(key);
    }

    /**
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getCacheClear(Class<T> tClass) {
        Object obj = getCacheClear(tClass.getName());
        return tClass.cast(obj);
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        caches.clear();
    }

    /**
     * 清除指定缓存
     *
     * @param key
     */
    public void clearCache(String key) {
        caches.remove(key);
    }

    /**
     * 清除指定缓存
     * @param tClass
     * @param <T>
     */
    public <T> void clearCache(Class<T> tClass) {
        clearCache(tClass.getName());
    }


    /**
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getArraysClear(String key, Class<T> tClass) {
        Object o = getCacheClear(key);
        return JSON.parseArray(JSON.toJSONString(o), tClass);
    }

    /**
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getArrays(String key, Class<T> tClass) {
        Object o = getCache(key);
        return JSON.parseArray(JSON.toJSONString(o), tClass);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this.lifecycleCallbacks);
        ThreadPoolTool.getInstance().init();
        mContext = this;
        try {
            OkHttpClient.Builder builder= new OkHttpClient.Builder();
            HTTP.getInstance().init(this, null).setSslSocketFactory(getAssets().open(""));
            HTTP.getInstance().setTimeout(60);
        } catch (IOException e) {
            KLog.e(e);
        }
        //测试用临时数据库文件存放在SD卡
        Logger.init().hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
        EncryptManager.getInstance().initEncrypt();
        openComponentLog();

        //MobclickAgent.setSessionContinueMillis(60 * 1000);
        //MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //SDKInitializer.initialize(getApplicationContext());

        daoConfig = new DbManager.DaoConfig().setDbVersion(1)
                .setAllowTransaction(true)
                .setDbName(DB_NAME)
//                .setDbDir(dbFile)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager dbManager, int i, int i1) {
                        try {
                            dbManager.dropDb();
                        } catch (DbException e) {
                            Logger.e(e, e.getMessage());
                        }
                    }
                });
        DbManagerImpl.init(this, daoConfig);
        MobclickAgent.onEvent(this, "ClientLaunch");

        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
//                saveCache(HAVE_NEW_MESSAGE,msg);
//                Intent intent = new Intent("com.hq.app.olaf.HAVE_NEW_MESSAGE");
//                context.sendBroadcast(intent);
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.layout_notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.build();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void launchApp(Context context, UMessage uMessage) {
                Map<String,String> extra = uMessage.extra;
                boolean launchDefault = true;
                if(extra!=null){
                    String custom = extra.get("custom");
                    Map<String, Object> customMap = JSON.parseObject(custom);
                    String type = "";
                    if(customMap!=null){
                        type = (String) customMap.get("type");
                        if(!StringUtil.isEmpty(type)) {
                            launchDefault = false;
                            switch (type) {
                                case "diego.trade.success":

                                    Intent intent = new Intent(context, OrderDetailActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("orderId",(String)customMap.get("orderId"));
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                default:
                                    launchDefault = true;
                                    break;

                            }
                        }
                    }
                }
                if(launchDefault) {
                    super.launchApp(context, uMessage);
                }
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

//        mPushAgent.setNotificaitonOnForeground(true);
        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Logger.d("device token: " + deviceToken);
                Intent intent = new Intent(UPDATE_STATUS_ACTION);
                intent.putExtra("deviceToken",deviceToken);
                sendBroadcast(intent);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.d("register failed: " + s + " " +s1);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        //此处是完全自定义处理设置
//        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//        Intent serviceIntent = new Intent(this,DaemonService.class);
//        startService(serviceIntent);
    }

    /**
     * 监听ACTIVITY生命周期
     */
    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppActManager.getInstance().add((BaseActivity) activity);
        }

        public void onActivityStarted(Activity activity) {
            runningCount++;
            if(isRunInBackground&&runningCount==1){
                isRunInBackground = false;
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
            runningCount--;
            if(!isRunInBackground&&runningCount==0){
                isRunInBackground = true;
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
            ButterKnife.unbind(activity);
            AppActManager.getInstance().remove((BaseActivity) activity);
        }
    };

    protected void openComponentLog() {
        KLog.init(this, true);
    }

    protected void openCrashHandler(CrashHandler.OnCrashListener onCrashListener) {
        CrashHandler.getInstance().init(this, this.crashLogDirectory, onCrashListener);
    }

    public static HqPayApplication getAppContext() {
        return mContext;
    }

    public void setCrashLogDirectory(String crashLogDirectory) {
        this.crashLogDirectory = crashLogDirectory;
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        caches.clear();
        try {
            DBUtil.getInstance().close();
        } catch (IOException e) {
            Logger.e(e, e.getMessage());
        }
        MobclickAgent.onKillProcess(this);
        ThreadPoolTool.getInstance().close();
        AppActManager.getInstance().exit();
//        System.exit(0);
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    /**
     * 初始化Httpclient
     *
     * @param context
     * @param builder
     */
    public OkHttpClient.Builder initBuilder(Context context, OkHttpClient.Builder builder,int timeout) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            builder.addInterceptor(new LoggingInterceptors())
                    .cookieJar(new NetCookieJar(cookieStore))
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .hostnameVerifier(OkHostnameVerifier.INSTANCE);
        }
        return builder;
    }
}
