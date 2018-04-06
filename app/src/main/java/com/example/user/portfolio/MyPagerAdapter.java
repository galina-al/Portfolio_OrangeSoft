package com.example.user.portfolio;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    List<HeaderPhoto> pages;

    public MyPagerAdapter(Context context, List<HeaderPhoto> pages) {
        this.context = context;
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item, container, false);
        ImageView headerPhoto = (ImageView) view.findViewById(R.id.headerPhoto);
        ImageHelper.displayImage(pages.get(position).localPath, headerPhoto, null);
        container.addView(view);
        Log.d("SHOW PHOTO ", pages.get(position).localPath.toString());
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
