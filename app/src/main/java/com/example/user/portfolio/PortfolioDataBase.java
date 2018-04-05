package com.example.user.portfolio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PortfolioDataBase extends SQLiteOpenHelper {

    public static final String TABLE_HEADER_PHOTO = "header_photo";
    public static final String HEADER_PHOTO_ID = "header_photo_id";
    public static final String HEADER_PHOTO_LOCATION = "header_photo_location";
    public PortfolioDataBase(Context context) {
        super(context, "Db.bin", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_HEADER_PHOTO + "("
                + HEADER_PHOTO_ID + " integer primary key autoincrement, "
                + HEADER_PHOTO_LOCATION + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HEADER_PHOTO);
        this.onCreate(sqLiteDatabase);
    }
}
