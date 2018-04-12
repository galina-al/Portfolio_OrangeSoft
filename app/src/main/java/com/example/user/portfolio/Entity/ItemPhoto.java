package com.example.user.portfolio.Entity;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ItemPhoto {
    public String name;
    public String size;
    public String date;
    public String path;

    public ItemPhoto(String name, String size, String date, String path) {
        this.name = name;
        this.size = size;
        this.date = date;
        this.path = path;
    }

    public ItemPhoto() {
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }

    public String getPhotoUri() {
        return path;
    }

//    public static List<ItemPhoto> getItems(List<HeaderPhoto> photos){
//
//
//        return
//    }

    public static ItemPhoto getItemPhoto(Context context, HeaderPhoto headerPhoto) {

        String fileName = null;
        String fileSize = null;
        String fileDate = null;
        String filePath = headerPhoto.localPath;
        File file = new File(headerPhoto.localPath);
        Uri fileUri = Uri.fromFile(file);
        DateFormat TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (fileUri != null) {
            if (ContentResolver.SCHEME_FILE.equals(fileUri.getScheme())) {
                fileName = file.getName();
                fileSize = String.valueOf(file.length() / 1024) + "КБ";
                fileDate = TIMESTAMP.format(new Date(Long.valueOf(file.lastModified())));

                Log.d("IMAGE_DESCRIPTION_FILE", fileName + "..." + fileSize + "..." + fileDate);
            } else if (ContentResolver.SCHEME_CONTENT.equals(fileUri.getScheme())) {
                Cursor returnCursor =
                        context.getContentResolver().query(fileUri, null, null, null, null);
                if (returnCursor != null && returnCursor.moveToFirst()) {
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    int dataIndex = returnCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);

                    fileName = returnCursor.getString(nameIndex);
                    fileSize = String.valueOf(returnCursor.getLong(sizeIndex));
                    fileDate = TIMESTAMP.format(new Date(Long.valueOf(returnCursor.getString(dataIndex)) * 1000));

                    Log.d("IMAGE_DESCRIPTION", fileName + "..." + Integer.valueOf(fileSize) / 1024 + " KB..." + fileDate);
                    returnCursor.close();
                }
            }
        }
        return new ItemPhoto(fileName, fileSize, fileDate, filePath);
    }

}
