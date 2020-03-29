package com.zlove.base.util;

import java.io.File;
import java.io.FileInputStream;

import Decoder.BASE64Encoder;


public class FileUtil {
    
    /**
     * 将文件转成base64 字符串
     * 
     * @param path文件路径
     * @return *
     * @throws Exception
     */

    public static String encodeBase64File(String path) {
        byte[] buffer = null;
        try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (Exception e) {
        }
        return new BASE64Encoder().encode(buffer);

    }
}
