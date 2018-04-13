package com.example.user.portfolio.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.portfolio.Adapters.MyPagerAdapter;
import com.example.user.portfolio.DataBase.DbHelper;
import com.example.user.portfolio.DataBase.HeaderPhotoDao;
import com.example.user.portfolio.Entity.HeaderPhoto;
import com.example.user.portfolio.R;
import com.example.user.portfolio.util.ImageHelper;

import java.io.File;
import java.util.Collections;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

import static com.example.user.portfolio.util.CONSTANTS.PHOTO_FLAG;
import static com.example.user.portfolio.util.CONSTANTS.PHOTO_PATH_SHOW;
import static com.example.user.portfolio.util.CONSTANTS.PHOTO_POSITION_SHOW;

public class ShowPhotoActivity extends AppCompatActivity {

    private PhotoView photoView;
    private String path;
    private int position;
    private File file;
    private ViewPager photoPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        Bundle extras = getIntent().getExtras();
        path = extras.getString(PHOTO_PATH_SHOW);
        position = extras.getInt(PHOTO_POSITION_SHOW);
        file = new File(path);

        photoView = (PhotoView) findViewById(R.id.photo_view);
        ImageHelper.displayImage(Uri.fromFile(file).toString(), photoView, null);

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        Collections.reverse(headerPhotos);

        photoPager = (ViewPager) findViewById(R.id.photo_pager);
        photoPager.setAdapter(new MyPagerAdapter(this, headerPhotos, R.layout.photo_pager_item, R.id.show_photo, PHOTO_FLAG));
        photoPager.setCurrentItem(position);
    }

}
