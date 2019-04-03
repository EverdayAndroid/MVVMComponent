package com.everday.lib_base.utils;

import android.content.Context;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/13
* description: 鲁班压缩工具类
*/

public class LuBanUtils {
    private static Context mContext;
    public static void initLuBan(Context context){
        mContext = context;
    }

    /**
     * 压缩图片
     * @param filePath
     * @param tagerPath
     */
    public static void compress(File filePath,String tagerPath){
        Luban.with(mContext)
                .load(filePath)
                .setTargetDir(tagerPath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //压缩开始前调用，可以在方法内启动
                    }

                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();


    }
}
