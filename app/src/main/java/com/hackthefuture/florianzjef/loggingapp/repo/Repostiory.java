package com.hackthefuture.florianzjef.loggingapp.repo;

import com.hackthefuture.florianzjef.loggingapp.models.Log;

import java.util.ArrayList;
import java.util.List;

public class Repostiory {

    private static List<Log> logs = new ArrayList<>();

    static{
        for(int i=0; i<5; i++)
            logs.add(new Log("log "+i, "description of log"+i));
    }

    public static List<Log> getLogs() {
        return logs;
    }
}
