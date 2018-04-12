package com.example.user.portfolio.util;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageHelper {

    private static final float AVAILABLE_MEMORY_PERCENT = 1 / 8f;

    private static final DisplayImageOptions SMALL_IMAGE_OPTIONS = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new FadeInBitmapDisplayer(300))
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .resetViewBeforeLoading(true)
            .build();

    public static void displaySmallImage(String url, ImageView image) {
        ImageLoader.getInstance().displayImage(url, image, SMALL_IMAGE_OPTIONS);
    }

    public static void displaySmallImage(@DrawableRes int drawableResource, ImageView image) {
        ImageLoader.getInstance().displayImage("drawable://" + drawableResource, image, SMALL_IMAGE_OPTIONS);
    }

    public static void clearCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public static void displayImage(String url, ImageView imageView, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(url, imageView, listener);
    }

    public static ImageLoaderConfiguration getImageLoaderConfig(Context context) {
        return new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(getImageDefaultOptions())
                .memoryCache(newMemoryCache(context))
                .diskCache((new UnlimitedDiscCache(context.getCacheDir())))
                //.imageDownloader(new OkHttpImageDownloader(context)) // default
                .writeDebugLogs()
                .build();
    }

    // TODO add default image stub
    private static DisplayImageOptions getImageDefaultOptions() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .resetViewBeforeLoading(true)
                .build();
    }

    private static MemoryCache newMemoryCache(Context context) {
        int memoryClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int memoryCacheSize = (int) (MemorySize.MEGABYTE.bytes(memoryClass) * AVAILABLE_MEMORY_PERCENT);
        return new UsingFreqLimitedMemoryCache(memoryCacheSize);
    }
}
