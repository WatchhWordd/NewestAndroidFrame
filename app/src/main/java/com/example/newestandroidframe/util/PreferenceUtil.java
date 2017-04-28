package com.example.newestandroidframe.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newestandroidframe.FrameApp;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class PreferenceUtil {

    private static PreferenceUtil preferenceUtil;

    public static PreferenceUtil getInstance() {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferenceUtil();
        }
        return preferenceUtil;
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = FrameApp.getInstance().
            getSharedPreferences(Constants.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = FrameApp.getInstance().
            getSharedPreferences(Constants.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    private void putString(String key, String value) {
        SharedPreferences sharedPreferences = FrameApp.getInstance().
            getSharedPreferences(Constants.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = FrameApp.getInstance().
            getSharedPreferences(Constants.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
