package com.hackthefuture.florianzjef.loggingapp.models;


import java.io.Serializable;

public class Log implements Serializable {
    private String title;
    private String description;

    public Log(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
