package com.example.user.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAKE_RESULT = "take result";
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Button btn_takePhoto;
    private Button btn_choosePhoto;
    private View closeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);

        btn_takePhoto = (Button) findViewById(R.id.takePhoto);
        btn_choosePhoto = (Button) findViewById(R.id.choosePhoto);
        closeView = (View) findViewById(R.id.close_view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_takePhoto.setOnClickListener(this);
        btn_choosePhoto.setOnClickListener(this);
        closeView.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        btn_takePhoto.setOnClickListener(null);
        btn_choosePhoto.setOnClickListener(null);
        closeView.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.takePhoto:
                intent.putExtra(TAKE_RESULT, TAKE_PHOTO);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.choosePhoto:
                intent.putExtra(TAKE_RESULT, CHOOSE_PHOTO);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.close_view:
                finish();
                break;
        }
    }
}
