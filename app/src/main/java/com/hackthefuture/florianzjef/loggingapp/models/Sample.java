package com.hackthefuture.florianzjef.loggingapp.models;


import java.io.Serializable;

public class Sample implements Serializable {
    private String name="";
    private String value="";
    private String remark ="";
    private String dateTime ="";
    private String researcher ="";

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getValue() {
        return value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getResearcher() {
        return researcher;
    }
}
