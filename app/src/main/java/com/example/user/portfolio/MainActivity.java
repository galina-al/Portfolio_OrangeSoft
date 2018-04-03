package com.example.user.portfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       imageView  = (ImageView) findViewById(R.id.imageProfile);


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
            case R.id.imageProfile:
                Intent intent = new Intent(this, ChoosePhotoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
