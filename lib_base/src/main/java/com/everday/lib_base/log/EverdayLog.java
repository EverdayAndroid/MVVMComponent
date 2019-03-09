package com.everday.lib_base.log;

import android.util.Log;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/9
* description: 日志打印
*/
public class EverdayLog {
    private static boolean openLog = true;
    private static String TAG = "EverdayLog";
    public static void initOpenLog(boolean isOpen){
        openLog = isOpen;
    }

    /**
     * error日志打印
     * @param msg
     */
    public static void EverdayError(String msg){
        if(openLog) {
            Log.e(TAG, msg);
        }
    }

}
