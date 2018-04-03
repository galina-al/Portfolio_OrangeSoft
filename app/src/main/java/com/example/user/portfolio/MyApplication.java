package com.example.user.portfolio;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by User on 03.04.2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageHelper.getImageLoaderConfig(this));
    }
}
