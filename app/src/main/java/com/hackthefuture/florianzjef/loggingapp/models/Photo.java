package com.hackthefuture.florianzjef.loggingapp.models;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zjef on 8/12/2016.
 */

public class Photo {

    private String description;
    private String base64imagedata;
    private String datetime;

    public Photo(String description, String base64imagedata, String datetime) {
        this.description = description;
        this.base64imagedata = base64imagedata;
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64imagedata() {
        return base64imagedata;
    }

    public void setBase64imagedata(String base64imagedata) {
        this.base64imagedata = base64imagedata;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
