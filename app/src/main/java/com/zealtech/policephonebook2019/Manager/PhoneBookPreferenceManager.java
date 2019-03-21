package com.zealtech.policephonebook2019.Manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PhoneBookPreferenceManager {
    public static final String PhoneBookPreferences = "policePhoneBook";
    private SharedPreferences sharedPreferences;

    private final String userProfile = "userProfile";
    private final String userId = "userId";
    private final String token = "token";
    private final String language = "language";
    private final String isAppRunning = "isAppRunning";

    private final String appStatus = "appStatus"; // 1 running, 2 close

    public PhoneBookPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PhoneBookPreferences, Context.MODE_PRIVATE);
    }

    public static String getPhoneBookPreferences() {
        return PhoneBookPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getUserProfile() {
        if (sharedPreferences.contains(userProfile)) {
            return sharedPreferences.getString(userProfile, "0");
        }
        return "0";
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getLanguage() {
        return language;
    }

    public String getIsAppRunning() {
        return isAppRunning;
    }

    public String getAppStatus() {
        return appStatus;
    }
}
