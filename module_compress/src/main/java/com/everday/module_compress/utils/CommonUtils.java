package com.everday.module_compress.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_compress.activity.AlbumSelectActivity;

import java.io.File;

public class CommonUtils {

    /**
     * 许多定制的Android系统，并不带相机功能，如果强行调用，程序会崩溃
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void hasCameera(Activity activity, Intent intent,int requestCode){
        if(activity == null){throw new IllegalArgumentException("activity 为空");}

        PackageManager pm = activity.getPackageManager();
        boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                ||pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Camera.getNumberOfCameras() > 0;

        if(hasCamera){
            activity.startActivityForResult(intent,requestCode);
        }else{
            Toast.makeText(activity, "当前设备没有相机", Toast.LENGTH_SHORT).show();
            throw new IllegalStateException("当前设备没有相机");
        }
    }


    /**
     * 获取拍照的Intent
     * @param filePath 拍照后图片输出路径
     * @return  返回Intent
     */
    public static Intent getCameraIntent(Context context, File filePath){
        Uri outPutUri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            outPutUri = FileProvider.getUriForFile(context,getFileProviderName(context),new File(filePath.getAbsolutePath()));
        }else{
            outPutUri = Uri.fromFile(filePath);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);  //设置action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outPutUri); //将拍取的照片保存在制定目录
        return intent;
    }
    private static String getFileProviderName(Context context){
        return context.getPackageName()+".provider";
    }
    /**
     * 跳转到图库选择
     * @param activity
     * @param requestCode
     */
    public static void openAlbum(Activity activity,int requestCode){
        if(activity == null) return;

        ARouter.getInstance().build(RouterActivityPath.MODULE_COMPRESS.MODULE_ALBUMSELECTACTIVITY)
                .withInt(Constants.INTENT_EXTRA_LIMIT, 6)
                .navigation(activity,requestCode);

//        Intent intent = new Intent(activity,AlbumSelectActivity.class);
//        intent.putExtra(Constants.INTENT_EXTRA_LIMIT,6);
//        intent.setAction(Intent.ACTION_PICK);  //设置action为图库
//        intent.setType("image/*");
//        activity.startActivityForResult(intent,requestCode);
    }

    /**
     * 显示圆形进度对话框
     * @param activity
     * @param progressTitle
     * @return
     */
    public static ProgressDialog showProgressDialog(Activity activity,String... progressTitle){
        if(activity == null || activity.isFinishing()) return null;
        String title = "正在压缩中...";
        if(progressTitle!=null && progressTitle.length>0) title = progressTitle[0];

        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }
}
