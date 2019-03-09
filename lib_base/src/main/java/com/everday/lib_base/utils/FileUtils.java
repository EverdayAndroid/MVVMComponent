package com.everday.lib_base.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/5
* description: 文件管理
*/

public class FileUtils {
    private File mBaseDir;
    /** 拍照图片路径 */
    private File mBaseDirCamera;
    private static FileUtils fileUtils;
    private String cache = "cache";
    private String image = "Camera";
    private String video = "video";
    private String copress = "copress";
    private String exception = "exception";
    private String apk = "apk";
    private Context context;
    public final int SIZETYPE_B = 0;
    public final int SIZETYPE_KB = 1;
    public final int SIZETYPE_MB = 2;
    public final int SIZETYPE_GB = 3;
    public void init(Context context){
        this.context = context;
    }

    public static FileUtils getInstance()
    {
        if (fileUtils == null)
        {
            synchronized (FileUtils.class)
            {
                if (fileUtils == null)
                {
                    fileUtils = new FileUtils();
                }
            }
        }
        return fileUtils;
    }

    /**
     * 判断是否存储卡状态
     */
    public FileUtils()
    {
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState))
        {
            mBaseDir = new File(Environment.getExternalStorageDirectory(), "Assistant"); //自带存储卡
            mBaseDirCamera = new File(Environment.getExternalStorageDirectory(), "DCIM"); //自带存储卡
        }
        else
        {
            mBaseDir = context.getFilesDir();
        }
    }

    /**
     * 缓存文件夹
     * @return
     */
    public File getCacheDir(){
        File file = new File(mBaseDir,cache);
        if(!file.exists()){
            file.mkdirs();
        }
        return  file;
    }
    /**
     * 图片文件夹
     * @return
     */
    public File getImageDir(){
        File file = new File(mBaseDirCamera,image);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件
     * @param fl
     * @param fileName
     * @return
     */
    public File createFile(File fl,String fileName){
        File file = new File(fl,fileName);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getExceptionDir(){
        File file = new File(mBaseDir,exception);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 视频文件夹
     * @return
     */
    public File getVideDir(){
        File file = new File(mBaseDir,video);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
    public File getVideCompress(){
        File file = new File(getVideDir(),copress);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * apk路径
     * @return
     */
    public File getApkDir(){
        File file = new File(mBaseDir,apk);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
    /**
     * 创建文件夹
     *
     * @param file
     * @return
     */
    public File tryMakeDirs(File file)
    {
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public File getmBaseDir()
    {
        return mBaseDir;
    }

    public void setmBaseDir(File mBaseDir)
    {
        this.mBaseDir = mBaseDir;
    }


    /**
     * 获取文件或者是文件夹大小
     * @param filePath
     * @param sizeType b、kb、mb、gb
     * @return
     */
    public double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return FormetFileSize(blockSize, sizeType);
    }
    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private  long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }
    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public void clearFile(File file){
        if(file == null) {return;}
        File flist[] = file.listFiles();
        for (File fl:flist){
            fl.delete();
        }
    }
}
