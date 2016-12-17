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

public class NewPhotoFragment extends Fragment {

    private final int PERMISSIONS = 1;
    private static final String ARG_LOG = "LOG";
    private OnFragmentInteractionListener mListener;
    private View rootView;

    private Bitmap photo = null;

    private ImageButton btnTakepicture;
    private ImageView ivImage;
    private Button btnSave;
    private EditText input_Name;

    private static final int CAMERA_REQUEST = 1888;


    public static NewSampleFragment newInstance() {
        NewSampleFragment fragment = new NewSampleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_photo, container, false);
        input_Name = (EditText) rootView.findViewById(R.id.input_title);
        ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
        btnTakepicture = (ImageButton) rootView.findViewById(R.id.btnTakepicture);
        btnTakepicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,}, PERMISSIONS);
                }
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String description = input_Name.getText().toString();
                String base64imagedata = convertBipmapToString();
                String dateTime = new SimpleDateFormat("yyyyMMdd'-'hhmmss").format(new Date());

                Photo photo = new Photo(description, base64imagedata, dateTime);
                Repository.postPhoto(photo);
                Repository.loadAllPhotos();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageBitmap(photo);
        }
    }

    private String convertBipmapToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
