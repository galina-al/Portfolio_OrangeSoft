package com.example.user.portfolio.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.portfolio.Entity.HeaderPhoto;
import com.example.user.portfolio.util.ImageHelper;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

import static com.example.user.portfolio.util.CONSTANTS.*;

public class MyPagerAdapter extends PagerAdapter {

    private final String flagView;
    private Context context;
    private List<HeaderPhoto> pages;
    private int viewId ;
    private int imageViewId ;

    public MyPagerAdapter(Context context, List<HeaderPhoto> pages, int viewId, int imageViewId, String flagView) {
        this.context = context;
        this.pages = pages;
        this.viewId = viewId;
        this.imageViewId = imageViewId;
        this.flagView = flagView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position ) {
        View view = LayoutInflater.from(context).inflate(viewId, container, false);
        if(this.flagView == IMAGE_FLAG){
            ImageView imageView = (ImageView) view.findViewById(imageViewId);
            ImageHelper.displaySmallImage(Uri.fromFile(new File(pages.get(position).localPath)).toString(), imageView);
        } else if (this.flagView == PHOTO_FLAG){
            PhotoView photoView = (PhotoView) view.findViewById(imageViewId);
            ImageHelper.displaySmallImage(Uri.fromFile(new File(pages.get(position).localPath)).toString(), photoView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pages.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
