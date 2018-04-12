package com.example.user.portfolio.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.portfolio.Adapters.MyPagerAdapter;
import com.example.user.portfolio.DataBase.DbHelper;
import com.example.user.portfolio.DataBase.HeaderPhotoDao;
import com.example.user.portfolio.Entity.HeaderPhoto;
import com.example.user.portfolio.R;
import com.example.user.portfolio.util.Logic;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private boolean start = true;
    public static final int BUTTONS_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    public static final int PICK_REQUEST_CODE = 3;
    public static final String FILE_PATH_KEY = "file_path";
    private ViewPager pager;
    private DrawerLayout drawerLayout;
    private File file;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(FILE_PATH_KEY) != null) {
                file = new File(savedInstanceState.getString(FILE_PATH_KEY));
            }
        } else {
            file = null;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                intent = new Intent(MainActivity.this, ListPhotoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_wiki:
                                intent = new Intent(MainActivity.this, WikiActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        Collections.reverse(headerPhotos);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(this, headerPhotos, R.layout.view_pager_item , R.id.headerPhoto));
        pager.setOnTouchListener(new View.OnTouchListener() {
            private boolean moved;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    moved = false;
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    moved = true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (!moved) {
                        view.performClick();
                    }
                }
                return false;
            }
        });

        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoosePhotoActivity.class);
                if (start) {
                    startActivityForResult(intent, BUTTONS_REQUEST_CODE);
                    start = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (file != null) {
            outState.putString(FILE_PATH_KEY, file.getAbsolutePath());
        } else {
            outState.putString(FILE_PATH_KEY, null);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        dao.getAll();
        List<HeaderPhoto> newPhotos;
        start = true;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case BUTTONS_REQUEST_CODE:
                    int result = data.getIntExtra(ChoosePhotoActivity.TAKE_RESULT, 0);
                    if (result == ChoosePhotoActivity.TAKE_PHOTO) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            file = Logic.createImageFile(MainActivity.this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
                    }
                    if (result == ChoosePhotoActivity.PICK_PHOTO) {
                        Intent intentPick = new Intent(Intent.ACTION_PICK);
                        intentPick.setType("image/*");
                        startActivityForResult(intentPick, PICK_REQUEST_CODE);
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    HeaderPhoto photo = new HeaderPhoto(file.getAbsolutePath());
                    dao.setHeaderPhoto(photo);
                    newPhotos = dao.getAll();
                    Collections.reverse(newPhotos);
                    pager.setAdapter(new MyPagerAdapter(this, newPhotos, R.layout.view_pager_item, R.id.headerPhoto));
                    break;
                case PICK_REQUEST_CODE:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        File file = null;
                        try {
                            file = Logic.createImageFile(this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            ByteStreams.copy(imageStream, new FileOutputStream(file));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        dao.setHeaderPhoto(new HeaderPhoto(file.getAbsolutePath()));
                        newPhotos = dao.getAll();
                        Collections.reverse(newPhotos);
                        pager.setAdapter(new MyPagerAdapter(this, newPhotos, R.layout.view_pager_item, R.id.headerPhoto));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

}
