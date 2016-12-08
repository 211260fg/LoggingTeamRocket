package com.hackthefuture.florianzjef.loggingapp.repo;

import com.hackthefuture.florianzjef.loggingapp.models.Sample;

import java.util.ArrayList;
import java.util.List;

public class Repostiory {

    private static List<Sample> logs = new ArrayList<>();

    static{
        for(int i=0; i<5; i++)
            logs.add(new Sample("log "+i, "description of log"+i));
    }

    public static List<Sample> getLogs() {
        return logs;
    }
}
