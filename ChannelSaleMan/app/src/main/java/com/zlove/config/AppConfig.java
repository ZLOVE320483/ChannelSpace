package com.zlove.config;

import com.zlove.base.util.ApplicationUtil;

import java.io.File;


public class AppConfig {

    public static final String IMG_PREFIX  = "img_";
    public static final String IMG_EXTENSION = ".jpg";
    
    public static String getExternalCacheDir(){
        return ApplicationUtil.getApplicationContext().getExternalCacheDir().getAbsolutePath();
    }
    
    public static File generateTmFile(String prefix, String extension) {
        File sampleDir = new File(getExternalCacheDir());
        if (!sampleDir.canWrite()) {
            return null;
        }
        return new File(sampleDir, prefix + System.currentTimeMillis() + extension);
    }
}
