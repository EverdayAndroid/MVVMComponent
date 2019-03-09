package com.everday.lib_base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/5
* description: 电池优化
*/

public class ScreenLockUtils {

    static private HashMap<SoftReference<Activity>, PowerManager.WakeLock> mWakeLockArray = new HashMap<>();
    static private HashMap<SoftReference<Activity>, Boolean> mIsUnlockArray = new HashMap<>();

    /**
     * 保持系统不进入休眠
     *
     * @param activity
     */
    public static void keepScreenOn(Activity activity) {
        PowerManager.WakeLock wakeLock = mWakeLockArray.get(activity);
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, activity.getClass().getName());
        }
        if (!wakeLock.isHeld()) {
            //唤醒
            wakeLock.acquire();
        }
        SoftReference<Activity> softReference = new SoftReference<>(activity);
        mWakeLockArray.put(softReference, wakeLock);
    }

    /**
     * 释放资源，让系统重新进入休眠状态
     * @param activity
     */
    public static void cancelKeepSccreen(Activity activity){
        SoftReference<Activity> softReference = new SoftReference<>(activity);
        Activity aty = softReference.get();
        if(aty == null){
            return;
        }
        PowerManager.WakeLock wakeLock = mWakeLockArray.get(activity);
        if(wakeLock.isHeld()){
            //释放
            wakeLock.release();
        }
    }
}
