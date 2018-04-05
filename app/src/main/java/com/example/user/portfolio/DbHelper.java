package com.example.user.portfolio;

import android.content.Context;

public class DbHelper {

    private static DbHelper db;
    private PortfolioDataBase dataBase;
    private static Context context;

    private DbHelper() {
        dataBase = new PortfolioDataBase(context);
    }

    public static DbHelper getDb(){
        if (db == null){
            db = new DbHelper();
        }
        return db;
    }

    public static void init(Context context){
        DbHelper.context = context;
    }

    public HeaderPhotoDao getHeaderPhotoDao(){
        return new HeaderPhotoDao(dataBase);
    }
}
