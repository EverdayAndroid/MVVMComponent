package com.everday.module_compress;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.everday.module_compress.bean.Photo;
import com.everday.module_compress.config.CompressConfig;
import com.everday.module_compress.core.CompressImageUtils;
import com.everday.module_compress.listener.CompressImage;
import com.everday.module_compress.listener.CompressResultListener;

import java.io.File;
import java.util.ArrayList;

public class CompressImageManager implements CompressImage {

    private CompressImageUtils compressImageUtils; //压缩工具类
    private ArrayList<Photo> images; //需要压缩图片的集合
    private CompressImage.CompressListener listener; //压缩监听，告知activity
    private CompressConfig config;  //压缩配置
    private Context mContext; //上下文
    private ProgressDialog progressDialog; //dialog
    private CompressImageManager(Context context, ArrayList<Photo> image, CompressListener listener, CompressConfig config) {
        this.mContext = context;
        this.compressImageUtils = new CompressImageUtils(config);
        this.images = image;
        this.listener = listener;
        this.config = config;
    }

    public static CompressImageManager build(Context context, CompressConfig config,
                                             ArrayList<Photo> images, CompressListener listener){
        return new CompressImageManager(context,images,listener,config);
    }

    @Override
    public void compress() {
        if(images == null || images.isEmpty()){
            listener.onCompressFailed(images,"图片集合为空");
            return;
        }
        for (Photo image:images) {
            if(image == null){
                listener.onCompressFailed(images,"图片路径为空");
                return;
            }
        }
        compress(images.get(0));
    }

    private void compress(final Photo image){
        if(TextUtils.isEmpty(image.getOriginalPath())){
            continueCompress(image,false);
            return;
        }
        File file = new File(image.getOriginalPath());
        if(!file.exists() || !file.isFile()){
            continueCompress(image,false);
            return;
        }
        // <= 200kb
        if(file.length()< config.getMaxSize()){
            continueCompress(image,true);
            return;
        }
//        //是否显示dialog
//        if(config.isShowCompressDialog()){
//            progressDialog = CommonUtils.showProgressDialog((Activity) mContext, "正在压缩中....");
//        }
        compressImageUtils.compress(image.getOriginalPath(), new CompressResultListener() {
            @Override
            public void onCompressSuccess(String imgPath) {
                //压缩成功,给当前Photo对象赋值压缩路径
                image.setCompressPath(imgPath);
                continueCompress(image,true);
            }

            @Override
            public void onCompressFailed(String imgPath, String error) {

                continueCompress(image,false,error);
            }
        });
    }
    //递归压缩，判断index是否最后一张
    private void continueCompress(Photo image, boolean bool ,String... error) {
        image.setCompressed(bool);
        int index = images.indexOf(image);
        //判断是否最后一张
        if(index == images.size() - 1){
            handlerCallback(error);
        }else{
            compress(images.get(index+1));
        }
    }

    private void handlerCallback(String... error) {
        //如果存在错误信息
        if(error.length>0){
            listener.onCompressFailed(images,"某一张图片压缩失败...");
            return;
        }
        for (Photo photo:images) {
            //如果存在没有压缩的图片，或者压缩失败的
            if(!photo.isCompressed()){
                listener.onCompressFailed(images,"某一张图片压缩失败...");
                return;
            }
        }
        listener.onCompressSuccess(images);
    }
}
