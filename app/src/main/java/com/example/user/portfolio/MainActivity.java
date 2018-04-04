package com.example.user.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private boolean start = true;
    public static final int BUTTONS_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    public static final int PICK_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.photo);


    }

    @Override
    protected void onStart() {
        super.onStart();
        imageView.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        imageView.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.photo:
                Intent intent = new Intent(this, ChoosePhotoActivity.class);
                if (start) {
                    startActivityForResult(intent, BUTTONS_REQUEST_CODE);
                    start = false;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(imageBitmap);
                    break;
                case PICK_REQUEST_CODE:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
