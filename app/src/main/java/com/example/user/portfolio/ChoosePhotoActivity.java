package com.example.user.portfolio;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imageView;
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    private Uri picUri;
    private Button btn_takePhoto;
    private Button btn_choosePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);

        btn_takePhoto = (Button) findViewById(R.id.takePhoto);
        btn_choosePhoto = (Button) findViewById(R.id.choosePhoto);

    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_takePhoto.setOnClickListener(this);
        btn_choosePhoto.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        btn_takePhoto.setOnClickListener(null);
        btn_choosePhoto.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.takePhoto:
                try {
                    // Намерение для запуска камеры
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(captureIntent, CAMERA_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // Выводим сообщение об ошибке
                    String errorMessage = "Ваше устройство не поддерживает съемку";
                    Toast toast = Toast
                            .makeText(this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.choosePhoto:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Вернулись от приложения Камера
            if (requestCode == CAMERA_CAPTURE) {
                // Получим Uri снимка
                picUri = data.getData();
                // кадрируем его
                performCrop();
            }
            // Вернулись из операции кадрирования
            else if (requestCode == PIC_CROP) {
                Bundle extras = data.getExtras();
                // Получим кадрированное изображение
                Bitmap thePic = extras.getParcelable("data");
                // передаём его в ImageView
                ImageView picView = (ImageView) findViewById(R.id.photo);
                picView.setImageBitmap(thePic);
            }
        }
    }

    private void performCrop() {
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
