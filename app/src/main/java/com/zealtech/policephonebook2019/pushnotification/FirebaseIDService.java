package com.zealtech.policephonebook2019.pushnotification;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by Paeng on 09-Jul-18.
 */

public class FirebaseIDService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        sendRegistrationToServer(token);
    }
    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}
