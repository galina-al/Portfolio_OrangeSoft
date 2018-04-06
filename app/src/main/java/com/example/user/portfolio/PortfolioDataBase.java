package com.example.user.portfolio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class PortfolioDataBase extends SQLiteOpenHelper {

    public PortfolioDataBase(Context context) {
        super(context, "Db.bin", null, 1);
    }

    static {
        cupboard().register(HeaderPhoto.class);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }
}
