package com.hyt.moseaclass.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.hyt.moseaclass.MainActivity;
import com.hyt.moseaclass.R;

public class RunTimeService extends Service {

    //    通知渠道ID
    private static final String CHANNEL_ID = "1";
    //    通知ID
    private static final int ONGOING_NOTIFICATION_ID = 10;
    //    声明通知
    private Notification notification;

    public RunTimeService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //        创建通知渠道
        createNotificationChannel();
        showNotification();

        return START_STICKY;
    }

    /*
     * 创建通知渠道
     * */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
//            向系统注册信道
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    /*
     * 显示通知
     * */
    private void showNotification() {
//        通知可以点击前往应用首页
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)  // 小logo
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle()) // 默认样式
                .setContentTitle("山海课堂正在运行中") // 内容标题
                .setContentText("点击进入APP") //内容文字
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 优先级
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notification = builder.build();
//        启动通知
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (notification != null) {
            stopForeground(true);
        }
    }
}
