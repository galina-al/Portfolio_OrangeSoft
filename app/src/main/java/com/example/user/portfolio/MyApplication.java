package com.example.user.portfolio;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageHelper.getImageLoaderConfig(this));
        DbHelper.init(this);
    }
}
