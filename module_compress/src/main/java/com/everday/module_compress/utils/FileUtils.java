package com.everday.module_compress.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param tagerFile  目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File tagerFile) throws IOException {
        //新建文件输入流对它进行缓冲
        FileInputStream inputStream = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(inputStream);


        //新建文件输出流并对它进行缓冲
        FileOutputStream outputStream = new FileOutputStream(tagerFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(outputStream);

        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }

        outBuff.flush();

        inBuff.close();
        outBuff.close();
        outputStream.close();
        inputStream.close();
    }
}