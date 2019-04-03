package com.everday.module_home.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_base.base.BaseActivity;
import com.everday.lib_base.base.BaseViewModel;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_compress.CompressImageManager;
import com.everday.module_compress.bean.Photo;
import com.everday.module_compress.config.CompressConfig;
import com.everday.module_compress.listener.CompressImage;
import com.everday.module_compress.utils.CachePathUtils;
import com.everday.module_compress.utils.CommonUtils;
import com.everday.module_compress.utils.Constants;
import com.everday.module_home.R;
import com.everday.module_home.BR;
import com.everday.module_home.app.HomeApplication;
import com.everday.module_home.databinding.HomeActivityModuleHomeBinding;

import java.io.File;
import java.util.ArrayList;

@Route(path = RouterActivityPath.MODULE_HOME.MODULE_HOMEACTVITY)
public class ModuleHomeActivity extends BaseActivity implements CompressImage.CompressListener {
    //    @BindView(R2.id.home_tv)
//    TextView textView;
    @Autowired(name = "name")
    String name;
    @Autowired(name = "age")
    int age;
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_module_home);
//    }
    private ProgressDialog progressDialog; //dialog

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.home_activity_module_home;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        setObjectVariable(BR.name, name);
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permission[0]) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permission, 100);
        }
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "姓名：" + name + "   年龄：" + age, Toast.LENGTH_SHORT).show();
    }

    File cameraCacheFile = null;

    public void camera(View view) {
        cameraCacheFile = CachePathUtils.getCameraCacheFile();
        CommonUtils.hasCameera(this, CommonUtils.getCameraIntent(HomeApplication.getInstanceHomeApplication(), cameraCacheFile), Constants.CAMERA_CODE);
    }

    public void album(View view) {
//        CommonUtils.openAlbum(this, Constants.ALBUM_CODE);
        ARouter.getInstance().build(RouterActivityPath.MODULE_COMPRESS.MODULE_ALBUMSELECTACTIVITY)
                .withInt(Constants.INTENT_EXTRA_LIMIT, 6)
                .navigation(this,Constants.ALBUM_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(cameraCacheFile.getAbsolutePath());
            Log.e("TAG", cameraCacheFile.getAbsolutePath());
            //拍完照片更新图片库
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(cameraCacheFile)));
            String absolutePath = CachePathUtils.getCameraCacheFile().getAbsolutePath();
            CompressConfig config = new CompressConfig.Builder()
                    .setMaxPixel(1200)
                    .setMaxSize(300 * 1024)
                    .setCacheDir(absolutePath)
                    .setEnablePixelCompress(true)
                    .setEnaleQualityCompress(true)
                    .setEnableReserveRaw(true)
                    .setShowCompressDialog(true)
                    .create();

            ArrayList<Photo> photoArrayList = new ArrayList<>();
            Photo photo = new Photo();
            photo.setCompressed(true);
            photo.setOriginalPath(cameraCacheFile.getAbsolutePath());
            photo.setCompressPath("");
            photoArrayList.add(photo);

            //是否显示dialog
            if (config.isShowCompressDialog()) {
                progressDialog = CommonUtils.showProgressDialog(this, "正在压缩中....");
            }

            CompressImageManager.build(this, config,
                    photoArrayList, this).compress();
        }

        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            String path = FilePathUtils.parsePicturePath(this, data.getData());
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Log.e("TAG", path);
        }

    }

    @Override
    public void onCompressSuccess(ArrayList<Photo> images) {
        if(progressDialog!=null && !isFinishing()){
            progressDialog.dismiss();
        }
        Toast.makeText(this, "压缩成功", Toast.LENGTH_SHORT).show();
        Log.e("TAG", images.size() + "");
    }

    @Override
    public void onCompressFailed(ArrayList<Photo> images, String error) {
        Log.e("TAG", images.size() + "");
    }
}
