package com.hackthefuture.florianzjef.loggingapp.models;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample implements Serializable {
    private String name="";
    private String value="";
    private String remark ="";
    private String datetime ="";
    private String researcher ="";

    public Sample(String name, String value, String remark, String researcher) {
        this.name = name;
        this.value = value;
        this.remark = remark;
        this.datetime = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new Date());;
        this.researcher = researcher;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getValue() {
        return value;
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

    public String getResearcher() {
        return researcher;
    }
}
