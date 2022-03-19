package com.example.imagepicker;

import android.net.Uri;

public class ImageCraft {
    private Uri uri;
    private String name;
    private int count;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Uri getUri(){
        return uri;
    }

    public void setUri(Uri uri){
        this.uri = uri;
    }

}
