package com.everday.module_compress.listener;

import com.everday.module_compress.bean.Photo;

import java.util.ArrayList;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/18
* description: 图片集合压缩监听
*/

public interface CompressImage {
    //压缩
    void compress();
    interface CompressListener {

        //成功
        void onCompressSuccess(ArrayList<Photo> images);

        //失败
        void onCompressFailed(ArrayList<Photo> images,String error);

    }
}
