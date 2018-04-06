package com.example.user.portfolio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean start = true;
    public static final int BUTTONS_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    public static final int PICK_REQUEST_CODE = 3;
    private ViewPager pager;
    private DrawerLayout drawerLayout;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        Collections.reverse(headerPhotos);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(this, headerPhotos));
        pager.setOnTouchListener(new View.OnTouchListener() {
            private boolean moved;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    moved = false;
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
    public void onClick(View view) {

       /* switch (view.getId()) {
            case R.id.pager:
                Intent intent = new Intent(this, ChoosePhotoActivity.class);
                if (start) {
                    startActivityForResult(intent, BUTTONS_REQUEST_CODE);
                    start = false;
                }
                break;
        }*/
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
                        startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
                    }
                    if (result == ChoosePhotoActivity.PICK_PHOTO) {
                        Intent intentPick = new Intent(Intent.ACTION_PICK);
                        intentPick.setType("image/*");
                        startActivityForResult(intentPick, PICK_REQUEST_CODE);
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    //Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    /*String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        outputStream.write(data.getData().toString().getBytes());
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileInputStream  inputStream = openFileInput(fileName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/
                    Uri uri = data.getData();
                    HeaderPhoto photo = new HeaderPhoto(uri.toString());
                    dao.setHeaderPhoto(photo);
                    newPhotos = dao.getAll();
                    Collections.reverse(newPhotos);
                    pager.setAdapter(new MyPagerAdapter(this, newPhotos));
                    break;
                case PICK_REQUEST_CODE:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        dao.setHeaderPhoto(new HeaderPhoto(imageUri.toString()));
                        newPhotos = dao.getAll();
                        Collections.reverse(newPhotos);
                        pager.setAdapter(new MyPagerAdapter(this, newPhotos));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
