package com.example.user.portfolio;


public class HeaderPhoto {
    protected Long _id;
    protected String localPath;

    public HeaderPhoto() {
    }

    public HeaderPhoto(String localPath) {
        this._id = null;
        this.localPath = localPath;
    }


}
