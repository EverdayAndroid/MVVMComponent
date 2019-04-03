package com.everday.module_compress.listener;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/18
* description: 单张图片压缩监听
*/

public interface CompressResultListener {
    //成功
    void onCompressSuccess(String imgPath);

    //失败
    void onCompressFailed(String imgPath,String error);

}
