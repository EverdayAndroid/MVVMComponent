package com.everday.lib_base.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/5
* description: 分辨率
*/

public class ScreenUtils {
    public static DisplayMetrics displayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
    /**
     * px转dp
     * @param context
     * @param px
     * @return
     */
    public static int pxTodp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5);
    }
    //dp转px
    public static int dpTopx(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px*density+0.5);
    }

    public static int sp2px(Context context, int spVal) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }
}
