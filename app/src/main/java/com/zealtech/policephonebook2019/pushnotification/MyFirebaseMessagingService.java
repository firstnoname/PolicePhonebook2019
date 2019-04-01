package com.zealtech.policephonebook2019.pushnotification;

import android.app.Notification;
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
import com.zealtech.policephonebook2019.Activities.NotificationDetail;
import com.zealtech.policephonebook2019.Util.AppUtils;

import java.util.Map;

/**
 * Created by Paeng on 09-Jul-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

//        MyPreferenceManager myPreferenceManager=new MyPreferenceManager(this);
//        //Check if user is login
//        if(!myPreferenceManager.getUserProfile().equals("0")) {
//            // Check if message contains a data payload.
//            if (remoteMessage.getData() != null) {
                sendNotification(remoteMessage.getData());
//            } else {
//                sendNotificationFromWeb(remoteMessage.getNotification().getBody());
//            }
//        }
    }

    private void sendNotification(Map<String, String> messageBody) {

     /*   sharedPref = this.getSharedPreferences(
                getString(R.string.pref_id), Context.MODE_PRIVATE);

        String editGroup = sharedPref.getString(getString(R.string.pref_editGroup), "");
        ArrayList<String> editGroupArray = new ArrayList<String>(Arrays.asList(editGroup.split(",")));*/

        String message = "";
        String title = "";
        String id = "";
        String contentType = "";

        try {
            message = messageBody.get("message");
            try {
                title = messageBody.get("title");
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

        Intent intent = new Intent();
        boolean openMain=false;

        if (AppUtils.isAppRunning(this, "com.example.policephonebook2019")) {
            openMain = false;
//            intent=AppUtils.openShop(this,contentType);
        } else {
//            openMain = true;
            intent = new Intent(this, Notification.class);

        }

        Bundle bundle = new Bundle();
//        bundle.putString("notification", "notification");
//        bundle.putString("messageBody", message);
        bundle.putString("id", id);
//        bundle.putString("contentType", contentType);
//        bundle.putBoolean("openMain", openMain);
        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Default";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.icon_app)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(title_)
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

    /*private void sendNotificationFromWeb(String messageBody) {

        *//*sharedPref = this.getSharedPreferences(
                getString(R.string.pref_id), Context.MODE_PRIVATE);

        String editGroup = sharedPref.getString(getString(R.string.pref_editGroup), "");
        ArrayList<String> editGroupArray = new ArrayList<String>(Arrays.asList(editGroup.split(",")));
*//*
        Intent intent;
//

        intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("notification", "notification");
        bundle.putString("messageBody", messageBody);
//        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon_app)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
//                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/
}
