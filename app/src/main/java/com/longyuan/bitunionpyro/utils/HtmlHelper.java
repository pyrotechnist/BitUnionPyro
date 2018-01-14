package com.longyuan.bitunionpyro.utils;

import android.os.Build;
import android.text.Html;
import android.util.Log;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static com.longyuan.bitunionpyro.utils.Constant.DEFAULT_BU_URL;
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

        if(out.contains("img"))
        {
            out = out.replace("..","http://out.bitunion.org");
            Log.d("img ",out);
            return out;
        }

      /*  Log.d("before",out);
        String stringOut = htmlDecode(out);
        Log.d("after",stringOut);*/

        out = out.replace("<br>", "<br/>");
        return  out;
    }

    public static String avatarUrlUpdate(String tString){

        String aString = null;

        if(tString != null && tString.contains("img"))
        {
            tString = replaceImage(tString);
        }


        if (tString == null || tString.isEmpty())
        {
            aString = Constant.DEFAULT_AVATAR;

        }else if(!tString.contains(Constant.HTTP_TAG))
        {
            aString = DEFAULT_BU_URL + tString;
        } else
        {
            aString = tString;
        }

        return (aString);

    }

    private static String replaceImage(String str) {

        Pattern p = Pattern.compile("<img src=\"([^>\"]+)\"[^>]*(width>)?[^>]*\">");
        Matcher m = p.matcher(str);
       /* while (m.find()) {
            String url = parseLocalImage(m.group(1));
            if (url.contains("file:///android_asset/"))
                url = "<img id = 'face' src='" + url + "'>";
            else
                url = "<a href = '" + DEFAULT_BU_URL + url + "'>" + "<img src='" + url + "'></a>";
            str = str.replace(m.group(0), url);
            m = p.matcher(str);
        }*/

        // 删除前后的 br
      /*  str = str.replaceAll("(<br>)+<img", "<br><img");*/

      if(m.find())
      {
          String url = m.group(1);
          if(!url.contains(Constant.HTTP_TAG))
          {
              str = DEFAULT_BU_URL + url;
          }
      }

        return str;
    }


    private static String replaceBackGround(String str) {

        Pattern p = Pattern.compile("<img src=\"([^>\"]+)\"[^>]*(width>)?[^>]*\">");
        Matcher m = p.matcher(str);
       /* while (m.find()) {
            String url = parseLocalImage(m.group(1));
            if (url.contains("file:///android_asset/"))
                url = "<img id = 'face' src='" + url + "'>";
            else
                url = "<a href = '" + DEFAULT_BU_URL + url + "'>" + "<img src='" + url + "'></a>";
            str = str.replace(m.group(0), url);
            m = p.matcher(str);
        }*/

        // 删除前后的 br
      /*  str = str.replaceAll("(<br>)+<img", "<br><img");*/

        if(m.find())
        {
            String url = m.group(1);
            if(!url.contains(Constant.HTTP_TAG))
            {
                str = DEFAULT_BU_URL + url;
            }
        }

        return str;
    }


    /**
     * 替换表情 URL 为本地 URL（file:///android_asset/faces/），避免重复下载
     *
     * @param imgUrl 原始表情图片 URL
     * @return 处理之后的 URL
     */
    private static String parseLocalImage(String imgUrl) {
        // 检查是否为本地表情文件
        Pattern p = Pattern.compile("\\.\\./images/(smilies|bz)/(.+?)\\.gif$");
        Matcher m = p.matcher(imgUrl);
        if (m.find()) {
            // Use local assets for emotions
            Log.d(TAG, "parseLocalImage >> " + imgUrl + " - " + "file:///android_asset/faces/" + m.group(1) + "_" + m.group(2) + ".gif");
            imgUrl = "file:///android_asset/faces/" + m.group(1) + "_" + m.group(2) + ".gif";
        }
        return imgUrl;
    }


    /**
     * 基础 HTML 文本处理，包含去除 Open API 标志，双引号转单引号，多个换行转换成一个换成等等
     *
     * @param str 原始 HTML 文本
     * @return 处理之后的 HTML 文本
     */
    private static String replaceBase(String str) {
        // 单引号双引号
        str = str.replace("\"", "'");
        // str = str.replaceAll("&nbsp;", "");

        // Open API 标志
        if (str.contains("From BIT-Union Open API Project"))
            str = str.replace("<br/><span id='id_open_api_label'>..:: <a href=http://www.bitunion.org>From BIT-Union Open API Project</a> ::..<br/>", "");

        // 换行
        str = str.replace("\r\n", "");
        str = str.replace("\n", "");
        Log.d(TAG, "Body Before = " + str);
        str = str.replace("<br />", "<br>");
        str = str.replace("<br/>", "<br>");
        str = str.replaceAll("(<br>\\s*){2,}", "<br>");
        str = str.replace("<br>", "<br><br>");

        return str;
    }
}
