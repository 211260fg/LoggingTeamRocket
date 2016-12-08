package com.hackthefuture.florianzjef.loggingapp.models;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zjef on 8/12/2016.
 */

public class Photo {

    public String path="";
    public String name="";
    public String description="";
    public File photoFile;
    public String datetime="";

    public Photo() {
    }

    public Photo(String name, String description, File file, String mCurrentPhotoPath) {
        this.name= name;
        this.description= description;
        this.photoFile=file;
        this.path=mCurrentPhotoPath;
        this.datetime=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new Date());

    }

    public String getDatetime() {
        String date=datetime;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date newDate = format.parse(date);
            format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



}
