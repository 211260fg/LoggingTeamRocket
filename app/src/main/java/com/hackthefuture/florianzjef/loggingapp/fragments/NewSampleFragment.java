package com.hackthefuture.florianzjef.loggingapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Photo;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewSampleFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;

    private ImageView ivImage;
    private Button btnSave;
    private EditText input_Description;
    private EditText input_Name;
    private EditText input_Value;


    public static NewSampleFragment newInstance() {
        NewSampleFragment fragment = new NewSampleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_sample, container, false);
        input_Description = (EditText) rootView.findViewById(R.id.input_description);
        input_Name = (EditText) rootView.findViewById(R.id.input_title);
        input_Value = (EditText) rootView.findViewById(R.id.input_value);
        ivImage= (ImageView) rootView.findViewById(R.id.ivImage);

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                    String name = input_Name.getText().toString();
                    String value =input_Value.getText().toString();
                    String remark =input_Description.getText().toString();
                    String dateTime = new SimpleDateFormat("yyyyMMdd'-'hhmmss").format(new Date());

                    Sample sample = new Sample(name, value, remark, dateTime);
                    Repository.postSample(sample);
                    Repository.loadAllSamples();
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        ((MainActivity) getActivity()).toggleFABVisibility(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
