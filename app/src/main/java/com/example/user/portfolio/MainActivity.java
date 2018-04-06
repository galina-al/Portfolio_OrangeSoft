package com.example.user.portfolio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean start = true;
    public static final int BUTTONS_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    public static final int PICK_REQUEST_CODE = 3;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(this, headerPhotos));
        pager.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoosePhotoActivity.class);
                if (start) {
                    startActivityForResult(intent, BUTTONS_REQUEST_CODE);
                    start = false;
                }
                return false;
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
                    Log.d("CREATE PHOTO: ", uri.toString());
                    break;
                case PICK_REQUEST_CODE:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        dao.setHeaderPhoto(new HeaderPhoto(imageUri.toString()));
                        Log.d("PICK PHOTO: ", imageUri.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
