package com.everday.lib_base.utils;

import android.app.Activity;

import java.util.Stack;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/4
* description: Activity管理
*/

public class ActivityManagement {
    private static Stack<Activity> activityStack = new Stack<>();

    private static class ActivityManagementHolder {
        private static final ActivityManagement sInstance = new ActivityManagement();
    }

    public static ActivityManagement getInstance(){
        return ActivityManagementHolder.sInstance;
    }

    /**
     * 添加当前的Activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activityStack.push(activity);
    }

    /**
     * 指定关闭当前的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 指定关闭当前类
     * @param clzz
     */
    public void finishActivity(Class<?> clzz){
        for (Activity activity:activityStack) {
            if(activity.getClass().equals(clzz)){
                finishActivity(activity);
            }
        }
    }
}
