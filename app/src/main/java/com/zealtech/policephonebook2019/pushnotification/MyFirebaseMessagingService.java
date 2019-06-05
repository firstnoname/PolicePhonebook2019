package com.zealtech.policephonebook2019.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.policephonebook2019.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zealtech.policephonebook2019.Activities.MainActivity;
import com.zealtech.policephonebook2019.Activities.NotificationActivity;
import com.zealtech.policephonebook2019.Activities.NotificationDetail;
import com.zealtech.policephonebook2019.Helpers.Helper;
import com.zealtech.policephonebook2019.Model.Notification;
import com.zealtech.policephonebook2019.Util.AppUtils;

import java.util.Map;

/**
 * Created by Paeng on 09-Jul-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        test(remoteMessage.getData(), remoteMessage);

    }

    public void test(Map<String, String> messageBody, RemoteMessage data) {
        String message = "";
        String title = "";
        String id = "";
        String contentType = "";

        Map<String, String> handleMsg = data.getData();
        String myCustomKey = handleMsg.get("key1");


        try {
            message = data.getNotification().getBody();
            try {
                title = data.getNotification().getTitle();
            } catch (Exception e) {
                title = " ";
            }
            contentType=messageBody.get("contentType");
            id = messageBody.get("id");
        } catch (Throwable tx) {

        }

        String title_;
        if (title.equals(" ")) {
            title_ = message;
        } else {
            title_ = title;
        }

        title = data.getNotification().getTitle();
        message = data.getNotification().getBody();
        Intent intent;
        boolean openMain = false;

        if (Helper.isAppRunning(this, "com.example.policephonebook2019")) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, NotificationActivity.class);
        }

        Bundle bundle = new Bundle();
//        bundle.putString("notification", "notification");
//        bundle.putString("messageBody", message);
        bundle.putString("id", message);
//        bundle.putString("contentType", contentType);
//        bundle.putBoolean("openMain", openMain);
        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Default";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
