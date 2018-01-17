package com.longyuan.bitunionpyro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static com.longyuan.bitunionpyro.utils.Constant.PREF_PASSWORD;

/**
 * Created by LONGYUAN on 2018/1/17.
 */

public class SharedPreferencesHelper {

    public static String getPrefValue(Context context,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String password = prefs.getString(key, null);
        return password;
    }

    public static void setPrefValue(Context context, String key, String value) {
        //Log.d(TAG, "setCachePassword >> " + BUApplication.password);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value == null ? "" : value);
        editor.commit();
    }
}
