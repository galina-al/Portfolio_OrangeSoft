package com.example.user.portfolio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener{

    public final static int CAMERA_RESULT = 0;
    public ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);

        Button btn_takePhoto = (Button) findViewById(R.id.takePhoto);
        btn_takePhoto.setOnClickListener(this);
        Button btn_choosePhoto = (Button) findViewById(R.id.choosePhoto);
        btn_choosePhoto.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.photo);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.takePhoto:
                Intent intentTake = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentTake, CAMERA_RESULT);

                break;
            case R.id.choosePhoto:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT) {
            Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnailBitmap);
        }
    }
}
