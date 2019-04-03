package com.everday.module_compress.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CachePathUtils {

    /**
     * 独立创建拍照路径
     * @param fileName
     * @return
     */
    public static File getCameraCacheDir(String fileName){
        File cache = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if(!cache.mkdirs()&& (!cache.exists() || !cache.isDirectory())){
            return null;
        }else {
            return new File(cache,fileName);
        }
    }

    /**
     * 获取图片文件名
     * @return
     */
    private static String getBaseFileName(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
    }

    /**
     * 获取拍照缓存文件
     * @return
     */
    public static File getCameraCacheFile(){
        String fileName = "camera_"+getBaseFileName()+".jpg";
        return getCameraCacheDir(fileName);
    }

    /**
     * 校验文件是否为文件，如果不是返回getCameraCacheDir
     * @param cache
     * @return
     */
    public static File checkFile(String cache){
        File file = new File(cache);
        if(file.isDirectory()){
            return getCameraCacheDir(cache);
        }
        return file;
    }

    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
