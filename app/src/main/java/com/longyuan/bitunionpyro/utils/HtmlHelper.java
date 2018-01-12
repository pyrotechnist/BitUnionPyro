package com.longyuan.bitunionpyro.utils;

import android.os.Build;
import android.text.Html;

import java.net.URLDecoder;

import static java.net.URLDecoder.decode;

/**
 * Created by loxu on 12/01/2018.
 */

public class HtmlHelper {

    public static String htmlDecode(String htmlString){

        if (Build.VERSION.SDK_INT >= 24)
        {
            return Html.fromHtml(htmlString , Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else
        {
            return Html.fromHtml(htmlString).toString();
        }
    }

    public static  String urlDecode(String tString){


        String out = "";
        try {
             out= URLDecoder.decode(tString, "UTF-8");
        }catch(Exception ex){


        }
        return  out;
    }
}
