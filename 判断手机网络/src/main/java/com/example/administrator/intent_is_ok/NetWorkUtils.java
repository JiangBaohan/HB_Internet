package com.example.administrator.intent_is_ok;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * data:2017/7/6
 * author:汉堡(Administrator)
 * function:
 */

public class NetWorkUtils {
    //判断网络是否连接
    public static boolean isNetWorkAvailable(Context context) {
        //网络连接管理器
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return true;
        }
        return false;
    }


    //判断是否时wifi
    public static boolean isWifi(Context context) {
         ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null&&info.getType()==manager.TYPE_WIFI){
            return true;
        }
        return false;
    }


    //判断是否是手机流量
    public static boolean isMobile(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&& info.getType()==manager.TYPE_MOBILE) {
            return  true;
        }
            return false;
        }
    }

