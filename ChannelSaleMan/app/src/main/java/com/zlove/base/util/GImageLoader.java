package com.zlove.base.util;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zlove.channelsaleman.R;


public class GImageLoader {
    
    private static GImageLoader instance;
    private ImageLoader imageLoader;
    private DisplayImageOptions normalOptions;
    private DisplayImageOptions roundOptions;
    private DisplayImageOptions avatarOptions;
    
    public synchronized static GImageLoader getInstance() {
        if (instance == null) {
            instance = new GImageLoader();
            instance.normalOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_image_download)
            .showImageForEmptyUri(R.drawable.ic_image_download)
            .showImageOnFail(R.drawable.ic_image_download_fail)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .build();
            
            instance.roundOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_launcher)
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(20))
            .build();
            
            instance.imageLoader = ImageLoader.getInstance();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(ApplicationUtil.getApplicationContext())
            .threadPriority(Thread.NORM_PRIORITY - 2)
            .denyCacheImageMultipleSizesInMemory()
            .discCacheFileNameGenerator(new Md5FileNameGenerator())
            .tasksProcessingOrder(QueueProcessingType.LIFO)
            .build();
            
            instance.avatarOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.user_center_icon_bg)
            .showImageForEmptyUri(R.drawable.user_center_icon_bg)
            .showImageOnFail(R.drawable.user_center_icon_bg)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .build();
            
            instance.imageLoader.init(config);
        }
        return instance;
    }
    
    
    public DisplayImageOptions getNormalOptions() {
        return normalOptions;
    }
    
    
    public DisplayImageOptions getRoundOptions() {
        return roundOptions;
    }
    
    public DisplayImageOptions getAvatarOptions() {
        return avatarOptions;
    }
    
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
