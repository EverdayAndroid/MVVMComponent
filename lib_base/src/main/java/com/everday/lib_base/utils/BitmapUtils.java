package com.everday.lib_base.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/13
* description: 图片压缩工具类，推荐使用鲁班压缩
*/

public class BitmapUtils {

    /**
     * 质量压缩
     * @param filePath      源文件地址
     * @param tagerFile     目标文件地址
     * @return
     */
    public static void compressQuality(String filePath,File tagerFile){
        Bitmap bitmap = fileToBitmap(filePath);
        //0-100  100为不压缩
        int options = 10;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tagerFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 尺寸压缩
     * @param filePath    源文件地址
     * @param tagerFile   目标文件地址
     */
    public static void compressSize(String filePath,File tagerFile){
        int ratio = 6;
        Bitmap bitmap = fileToBitmap(filePath);
        //根据宽高重新创建bitmap
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / ratio, bitmap.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        //初始化画布
        Canvas canvas = new Canvas();
        Rect rect = new Rect(0,0,result.getWidth(),result.getHeight());
        //重新bitmap
        canvas.drawBitmap(result,null,rect,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG,100,baos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tagerFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 图片格式压缩
     * @param filePath
     * @param tagerFile
     */
    public static void compressRGB(String filePath,File tagerFile){
        BitmapFactory.Options options = new BitmapFactory.Options();
//        ALPHA_8每个像素都存储为一个半透明（alpha）通道ALPHA_8
//        ARGB_4444 此字段已在API级别13中弃用。由于此配置的质量较差，建议使用ARGB_8888
//        ARGB_8888每个像素存储在4个字节。
//        RGB_565每个像素存储在2个字节中，只有RGB通道被编码：红色以5位精度存储（32个可能值），绿色以6位精度存储（64个可能值），蓝色存储为5位精确。
        options.inPreferredConfig  = Bitmap.Config.RGB_565;

        Bitmap result = BitmapFactory.decodeFile(filePath, options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.PNG,100,baos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tagerFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 图片路径转换成bitmap
     * @param filePath    图片路径
     * @return
     */
    private static Bitmap fileToBitmap(String filePath){
        Bitmap sourceBitmap = BitmapFactory.decodeFile(filePath);
        return sourceBitmap;
    }

    /**
     * 资源文件转换bitmap
     * @param resources   资源
     * @param resId       资源文件id
     * @return
     */
    public static Bitmap resourceToBitmap(Resources resources,int resId){
        Bitmap sourceBitmap = BitmapFactory.decodeResource(resources,resId);
        return sourceBitmap;
    }



}
