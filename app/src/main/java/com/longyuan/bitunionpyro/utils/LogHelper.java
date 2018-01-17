package com.longyuan.bitunionpyro.utils;

import android.util.Log;

/**
 * Created by LONGYUAN on 2018/1/17.
 */

public class LogHelper {

    public static void LogInfo(String tag,String info){
        Log.i(tag,HtmlHelper.urlDecode(info));
    }
}
