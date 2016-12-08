package com.hackthefuture.florianzjef.loggingapp.models;


import java.io.Serializable;

public class Sample implements Serializable {
    private String name="";
    private String value="";
    private String remark ="";

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }
}
