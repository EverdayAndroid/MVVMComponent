package com.everday.module_compress.utils;

import android.os.Environment;

public class Constants {
    //相机
    public final static int CAMERA_CODE = 1001;
    //相册
    public final static int ALBUM_CODE = 1002;
    //读写sdk
    public final static int READ_EXTERNAL_STORAGE = 1003;
    //
    public static final int REQUEST_CODE = 2000;
    //查询图片Code值
    public static final int FETCH_COMPLETED = 2002;

    public static final String INTENT_EXTRA_ALBUM = "album";
    public static final String INTENT_EXTRA_IMAGES = "images";
    //缓存根目录
    public final static String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/";
    //压缩后缓存路径
    public final static String COMPRESS_CACHE = "compress_cache";

    public static final String INTENT_EXTRA_LIMIT = "limit";
    public static final int DEFAULT_LIMIT = 10;

    public static int limit;
}
