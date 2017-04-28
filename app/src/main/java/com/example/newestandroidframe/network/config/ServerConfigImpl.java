package com.example.newestandroidframe.network.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class ServerConfigImpl implements ServerConfig {

    public static final String PREF_FILE_NAME = "ServerConfig.xml";
    public static final String PREF_ACCESS_TOKEN = "accessToken";

    private SharedPreferences pref;
    private Bundle appMetaData;
    private String appVersionName;
    private String uuid;

    public ServerConfigImpl(Context context) {
        tryLoadConfig(context);
    }

    private void tryLoadConfig(Context context) {
        try {
            loadConfig(context);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig(Context context) throws PackageManager.NameNotFoundException {
        pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        appMetaData = context.getPackageManager()
            .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
            .metaData;
        appVersionName = context.getPackageManager()
            .getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS)
            .versionName;
        uuid = Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ANDROID_ID);
    }

    @Override
    public String getAccessToken() {
        String accessToken = pref.getString("accessToken", "");
        return accessToken;
    }

    @Override
    public String getContentType() {
        return "application/json;charset=utf-8";
    }

    @Override
    public String getAppId() {
        return appMetaData.getString("appId", "");
    }

    @Override
    public String getAppVersion() {
        return appVersionName;
    }

    @Override
    public String getAppKey() {
        return appMetaData.getString("appKey", "");
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setAccessToken(String accessToken) {
        pref.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
    }
}
