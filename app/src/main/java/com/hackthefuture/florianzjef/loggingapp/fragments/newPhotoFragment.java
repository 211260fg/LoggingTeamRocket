package com.hackthefuture.florianzjef.loggingapp.fragments;

import android.Manifest;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Photo;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewPhotoFragment extends Fragment {

    private final int PERMISSIONS = 1;
    private static final String ARG_LOG = "LOG";
    private Sample sample;
    private OnFragmentInteractionListener mListener;
    private View rootView;

    private ImageButton btnTakepicture;
    private Button btnSave;
    private EditText input_Description;
    private EditText input_Name;

    public static NewSampleFragment newInstance() {
        NewSampleFragment fragment = new NewSampleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_sample, container, false);
        input_Description = (EditText) rootView.findViewById(R.id.input_description);
        input_Name = (EditText) rootView.findViewById(R.id.input_title);

        btnTakepicture = (ImageButton) rootView.findViewById(R.id.btnTakepicture);
        btnTakepicture.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,},PERMISSIONS);
                }
                sendTakePictureIntent();
            }
        });

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                savePhoto();
            }
        });
        setHasOptionsMenu(true);
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        ((MainActivity)getActivity()).setActionbarArrow(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void sendTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException ex){
                Toast.makeText(getContext(),"can't create photo file",Toast.LENGTH_SHORT);
            }
            if(photoFile!=null){
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }

    private void savePhoto() {
        if(mCurrentPhotoPath== null && mCurrentPhotoPath ==""){
            Toast.makeText(getContext(),"you didn't take a picture", Toast.LENGTH_LONG);
        }
        if (input_Name.getText().toString()=="" && input_Description.getText().toString()==""){
            Toast.makeText(getContext(),"fill in all te fields", Toast.LENGTH_LONG);
        }
        Photo photo = new Photo();
        File file = new File(mCurrentPhotoPath);
        photo.photoFile=file;
        photo.description=input_Description.getText().toString();
        photo.name= input_Name.getText().toString();
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
