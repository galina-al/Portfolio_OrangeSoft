package com.example.user.portfolio.DataBase;


import com.example.user.portfolio.Entity.HeaderPhoto;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class HeaderPhotoDao {

    PortfolioDataBase dataBase;

    public HeaderPhotoDao(PortfolioDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<HeaderPhoto> getAll(){
        return cupboard().withDatabase(dataBase.getWritableDatabase()).query(HeaderPhoto.class).list();
    }

    public void  setHeaderPhoto(HeaderPhoto object){
        cupboard().withDatabase(dataBase.getWritableDatabase()).put(object);
    }

    public HeaderPhoto getHeaderPhoto(HeaderPhoto headerPhoto){
        return cupboard().withDatabase(dataBase.getWritableDatabase()).get(headerPhoto);
    }

    public void deleteHeaderPhoto(HeaderPhoto headerPhoto){
        cupboard().withDatabase(dataBase.getWritableDatabase()).delete(headerPhoto);
    }

    public void deleteAll(){
        cupboard().withDatabase(dataBase.getWritableDatabase()).delete(HeaderPhoto.class, null);
    }

}
