package com.example.user.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    public static final int BUTTONS_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       imageView  = (ImageView) findViewById(R.id.photo);


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

        switch (view.getId()){
            case R.id.photo:
                Intent intent = new Intent(this, ChoosePhotoActivity.class);
                startActivityForResult(intent, BUTTONS_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == BUTTONS_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                int result = data.getIntExtra(ChoosePhotoActivity.TAKE_RESULT, 0);
                if(result == ChoosePhotoActivity.TAKE_PHOTO){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
                if(result == ChoosePhotoActivity.CHOOSE_PHOTO){

                }
            }
        }

        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
}
