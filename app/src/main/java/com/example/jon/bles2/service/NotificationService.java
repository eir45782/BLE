package com.example.jon.bles2.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

@SuppressLint("NewApi")
public class NotificationService extends NotificationListenerService {
    public static final String SEND_WX_BROADCAST="SEND_WX_BROADCAST";
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        Log.e("AAA", "=2==onNotificationPosted   ID :"
                +"1.\t"+ sbn.getId() + "   2.\t"
                + sbn.getNotification().tickerText + "   3.\t"
                + sbn.getNotification().extras.get("android.title") + "   4.\t"
                + sbn.getNotification().extras.get("android.text") + "   5.\t"
                + sbn.getPackageName());
        String packageName=sbn.getPackageName();
        String notificationTitle = sbn.getNotification().extras.getString("android.title");//通知標題
        String notificationText = sbn.getNotification().extras.getString("android.text");//通知內容
        Intent intent=new Intent();
        intent.setAction(SEND_WX_BROADCAST);
        Bundle bundle=new Bundle();
        bundle.putString("packageName",packageName);
        bundle.putString("Title",notificationTitle);
        bundle.putString("Text",notificationText);
        intent.putExtras(bundle);
        this.sendBroadcast(intent);
    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        Log.e("AAA", "=4==onNotificationRemoved   ID :"
                + sbn.getId() + "\t"
                + sbn.getNotification().tickerText
                + "\t" + sbn.getPackageName());
    }

}
