package com.everday.module_compress.bean;

public class Photo {
    /**
     * 图片原始路径
     */
    private String originalPath;

    /**
     * 是否压缩
     */
    private boolean compressed;

    /**
     * 压缩后路径
     */
    private String compressPath;


    public String getOriginalPath() {
        return originalPath == null ? "" : originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath == null ? "" : compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }
}
