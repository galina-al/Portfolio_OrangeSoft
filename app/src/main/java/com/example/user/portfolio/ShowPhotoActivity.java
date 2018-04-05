package com.example.user.portfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ShowPhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView show_photo;
    private View close_photo_up;
    private View close_photo_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        show_photo = (ImageView) findViewById(R.id.show_photo);
        close_photo_up = (View) findViewById(R.id.close_photo_up);
        close_photo_down = (View) findViewById(R.id.close_photo_down);

    }

    @Override
    protected void onStart() {
        super.onStart();
        close_photo_up.setOnClickListener(this);
        close_photo_down.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        close_photo_up.setOnClickListener(null);
        close_photo_down.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_photo_up:
                break;
            case R.id.close_photo_down:
                break;
        }
    }
}
