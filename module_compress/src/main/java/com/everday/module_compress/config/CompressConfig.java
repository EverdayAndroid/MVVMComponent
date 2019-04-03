package com.everday.module_compress.config;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/18
 * description: 压缩配置
 */

public class CompressConfig {


    /**
     * 长或宽不超过最大像素，单位px
     */
    private int maxPixel = 1200;

    /**
     * 压缩到最大大小，单位B
     */
    private int maxSize = 200 * 1024;

    /**
     * 是否启用像素压缩
     */
    private boolean enablePixelCompress = true;
    /**
     * 是否启用质量压缩
     */
    private boolean enaleQualityCompress = true;

    /**
     * 是否保留源文件
     */
    private boolean enableReserveRaw = false;

    /**
     * 压缩后缓存图片目录，非文件路径
     */
    private String cacheDir;
    /**
     * 是否显示压缩进度条
     */
    private boolean showCompressDialog;

    public static CompressConfig getDefaulteConfig() {
        return new CompressConfig();
    }


    public int getMaxPixel() {
        return maxPixel;
    }

    public void setMaxPixel(int maxPixel) {
        this.maxPixel = maxPixel;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEnablePixelCompress() {
        return enablePixelCompress;
    }

    public void setEnablePixelCompress(boolean enablePixelCompress) {
        this.enablePixelCompress = enablePixelCompress;
    }

    public boolean isEnaleQualityCompress() {
        return enaleQualityCompress;
    }

    public void setEnaleQualityCompress(boolean enaleQualityCompress) {
        this.enaleQualityCompress = enaleQualityCompress;
    }

    public boolean isEnableReserveRaw() {
        return enableReserveRaw;
    }

    public void setEnableReserveRaw(boolean enableReserveRaw) {
        this.enableReserveRaw = enableReserveRaw;
    }

    public String getCacheDir() {
        return cacheDir == null ? "" : cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public boolean isShowCompressDialog() {
        return showCompressDialog;
    }

    public void setShowCompressDialog(boolean showCompressDialog) {
        this.showCompressDialog = showCompressDialog;
    }

    public static class Builder {
        private CompressConfig config;

        public Builder() {
            config = new CompressConfig();
        }

        /**
         * 最大像素，单位px
         * @param maxPixel
         * @return
         */
        public Builder setMaxPixel(int maxPixel) {
            config.setMaxPixel(maxPixel);
            return this;
        }

        /**
         * 图片大小，单位b
         * @param maxSize
         * @return
         */
        public Builder setMaxSize(int maxSize) {
            config.setMaxSize(maxSize);
            return this;
        }
        //像素压缩
        public Builder setEnablePixelCompress(boolean enablePixelCompress){
            config.setEnablePixelCompress(enablePixelCompress);
            return this;
        }
        //质量压缩
        public Builder setEnaleQualityCompress(boolean enaleQualityCompress) {
            config.setEnaleQualityCompress(enaleQualityCompress);
            return this;
        }
        //是否保留源文件
        public Builder setEnableReserveRaw (boolean enableReserveRaw){
            config.setEnableReserveRaw(enableReserveRaw);
            return this;
        }
        //压缩目标路径
        public Builder setCacheDir (String cacheDir){
            config.setCacheDir(cacheDir);
            return this;
        }
        //是否启用dialog
        public Builder setShowCompressDialog (boolean showCompressDialog){
            config.setShowCompressDialog(showCompressDialog);
            return this;
        }
        public CompressConfig create(){
            return config;
        }
    }
}
