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

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private List<HeaderPhoto> pages;
    private int viewId ;
    private int imageViewId ;

    public MyPagerAdapter(Context context, List<HeaderPhoto> pages, int view_id, int image_view_id) {
        this.context = context;
        this.pages = pages;
        this.viewId = view_id;
        this.imageViewId = image_view_id;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position ) {
        View view = LayoutInflater.from(context).inflate(viewId, container, false);
        ImageView headerPhoto = (ImageView) view.findViewById(imageViewId);
        ImageHelper.displaySmallImage(Uri.fromFile(new File(pages.get(position).localPath)).toString(), headerPhoto);
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
