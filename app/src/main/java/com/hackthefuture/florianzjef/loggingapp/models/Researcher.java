package com.hackthefuture.florianzjef.loggingapp.models;

public class Researcher {

    private String name="";
    private String password="";

    public Researcher(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
