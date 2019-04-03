package com.everday.module_compress.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.everday.module_compress.config.CompressConfig;
import com.everday.module_compress.listener.CompressResultListener;
import com.everday.module_compress.utils.CachePathUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/18
 * description: 压缩照片
 */
public class CompressImageUtils {
    private CompressConfig config;
    //图片原始宽度和高度
    private int srcWidth, srcHeight;
    //压缩后的图片路径
    private String cacheImgPath;
    public CompressImageUtils(CompressConfig config) {
        this.config = config == null ? CompressConfig.getDefaulteConfig() : config;
    }

    /**
     * 压缩图片，采样点压缩，质量压缩
     * @param imgPath  图片路径
     * @param listener  监听压缩是否成功
     */
    public void compress(String imgPath, CompressResultListener listener) {

        //是否开启采样点压缩
        if (config.isEnablePixelCompress()) {
            try {
                compressImageByPixel(imgPath, listener);
            } catch (Exception e) {
                listener.onCompressFailed(imgPath, String.format("质量图片压缩失败，%s", e.toString()));
            }
        } else {
            try {
                //质量压缩
                compressImageByQuality(BitmapFactory.decodeFile(imgPath), imgPath, listener);
            }catch (Exception e){
                listener.onCompressFailed(imgPath,String.format("尺寸图片压缩失败，%s", e.toString()));
            }
        }
        //是否保留源文件
        if(!config.isEnableReserveRaw()){
            CachePathUtils.deleteFile(imgPath);
        }
    }

    /**
     * 质量压缩
     * @param bitmap
     * @param imgPath
     * @param listener
     */
    private void compressImageByQuality(Bitmap bitmap, String imgPath, CompressResultListener listener) {
        //0-100之间
        int quality = 100;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        while (stream.toByteArray().length > config.getMaxSize()){
            //处理小于10直接return不再进行压缩
            if(quality <= 10){
                return;
            }
            //重置流
            stream.reset();
            quality -= 10;
            //再次进行压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        }
        //回收bitmap资源
        bitmap.recycle();
        FileOutputStream fos = null;
        try {
            //指定压缩后的路径
            cacheImgPath = isCache();
            fos = new FileOutputStream(cacheImgPath);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            listener.onCompressFailed(imgPath, String.format("压缩异常,%s", e.toString()));
        } finally {
            try {
                fos.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listener.onCompressSuccess(cacheImgPath);
    }

    /**
     * 尺寸压缩
     * @param imgPath
     * @param listener
     */
    private void compressImageByPixel(String imgPath, CompressResultListener listener) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置不消耗内存获取图片宽高
        options.inJustDecodeBounds = true;
        options.inSampleSize = computeSize(imgPath, options);
        //根据采样点重新获取宽和高
        BitmapFactory.decodeFile(imgPath,options);
        //重新获取宽和高
        srcWidth = options.outWidth;
        srcHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        //旋转角度
        int rotate = getExifOrientation(imgPath);
        Matrix matrix = new Matrix();
        if(rotate > 0){
            matrix.setRotate(rotate);
        }

        bitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(imgPath,options), 0, 0, srcWidth, srcHeight, null, true);
        cacheImgPath = isCache();
        //判断是否开启质量压缩
        if(!config.isEnaleQualityCompress()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(cacheImgPath);
                fos.write(stream.toByteArray());
                fos.flush();
                fos.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                listener.onCompressFailed(imgPath, String.format("压缩异常,%s", e.toString()));
            } finally {
                try {
                    fos.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            listener.onCompressSuccess(cacheImgPath);
        }else{
            compressImageByQuality(bitmap,imgPath,listener);
        }
    }


    /**
     * 判断是否指定目标文件
     * @return
     */
    private String isCache(){
        String path;
        if(!config.getCacheDir().isEmpty()){
            String cacheDir = config.getCacheDir();
            File cameraCacheDir = CachePathUtils.checkFile(cacheDir);
            path = cameraCacheDir.getAbsolutePath();
        }else{
            path = CachePathUtils.getCameraCacheFile().getAbsolutePath();
        }
        return path;
    }


    /**
     * 返回采样点
     * LuBan采样点计算  没明白其中设置的值
     * @param imagePaht
     * @param options
     * @return
     */
    private int computeLuBanSize(String imagePaht, BitmapFactory.Options options) {
        BitmapFactory.decodeFile(imagePaht, options);
        srcWidth = options.outWidth;
        srcHeight = options.outHeight;

        int maxSize = Math.max(srcWidth, srcHeight);
        int minSize = Math.min(srcWidth, srcHeight);

        float scale = (float) minSize / maxSize;


        if (scale <= 1 && scale > 0.5625) {
            if (maxSize < 1664) {
                return 1;
            } else if (maxSize < 4990) {
                return 2;
            } else if (maxSize > 4990 && maxSize < 10240) {
                return 3;
            } else {
                return maxSize / 1280 == 0 ? 1 : maxSize / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return maxSize / 1280 == 0 ? 1 : maxSize / 1280;
        } else {
            return (int) Math.ceil(maxSize / (1280.0 / scale));
        }

    }

    /**
     * 获取采样点
     * @param imagePaht
     * @param options
     * @return
     */
    private int computeSize(String imagePaht, BitmapFactory.Options options){
        //设置默认采样size
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(imagePaht, options);
        srcWidth = options.outWidth;
        srcHeight = options.outHeight;
        int max = Math.max(srcWidth, srcHeight);
        int min = Math.min(srcWidth, srcHeight);
        //根据指定宽高设置采样点
        while (max/options.inSampleSize > config.getMaxPixel() && min/options.inSampleSize> config.getMaxPixel()){
            options.inSampleSize *= 2;
        }
        return options.inSampleSize;
    }



    /**
     * 获取图片角度
     * @param imagePaht
     * @return
     */
    private int getExifOrientation(String imagePaht) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePaht);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

}
