package com.example.user.portfolio;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private final int CAMERA_RESULT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);

        Button btn_takePhoto = (Button) findViewById(R.id.takePhoto);
        btn_takePhoto.setOnClickListener(this);
        Button btn_choosePhoto = (Button) findViewById(R.id.choosePhoto);
        btn_choosePhoto.setOnClickListener(this);
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
}
