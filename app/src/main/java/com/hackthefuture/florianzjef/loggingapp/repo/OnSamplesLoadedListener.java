package com.hackthefuture.florianzjef.loggingapp.repo;

import com.hackthefuture.florianzjef.loggingapp.models.Sample;

import java.util.List;

public interface OnSamplesLoadedListener {

    void onSamplesLoadSuccess(List<Sample> samples);

    void onSamplesLoadFailed(String message);
}
