package com.hq.app.olaf.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.activity.LoadingActivity;

public class DaemonService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("ticker");
        builder.setContentTitle("收款码收款通知已开启");
        final Intent notificationIntent = new Intent(this, LoadingActivity.class);
        final PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pi);
        final Notification notification = builder.build();
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, notification);//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, notification);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setTicker("ticker");
            builder.setContentTitle("收款码收款通知已开启");
            final Intent notificationIntent = new Intent(this, LoadingActivity.class);
            final PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            builder.setContentIntent(pi);
            final Notification notification = builder.build();
            startForeground(GRAY_SERVICE_ID, notification);
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }
}