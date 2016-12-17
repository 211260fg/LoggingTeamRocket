package com.hackthefuture.florianzjef.loggingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Photo;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.rest.Values;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class SampleDetailsFragment extends Fragment {

    private static final String ARG_LOG = "LOG";
    private static final String ARG_ISPHOTO = "ISPHOTO";
    private Sample sample;
    private boolean isPhoto;
    private OnFragmentInteractionListener mListener;
    private View rootView;



    public static SampleDetailsFragment newInstance(Sample log, boolean isPhoto) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOG, log);
        args.putBoolean(ARG_ISPHOTO, isPhoto);

        SampleDetailsFragment fragment = new SampleDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPhoto = getArguments().getBoolean(ARG_ISPHOTO);
        sample = (Sample) getArguments().getSerializable(ARG_LOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample_details, container, false);

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        TextView tvValue = (TextView) rootView.findViewById(R.id.tvValue);
        ImageView ivValue = (ImageView) rootView.findViewById(R.id.ivValue);
        TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        TextView tvResearcher = (TextView) rootView.findViewById(R.id.tvResearcher);


        tvTitle.setText(sample.getName());
        tvDescription.setText(sample.getRemark());
        tvDate.setText(sample.getDatetime());
        tvResearcher.setText(sample.getResearcher());

        if(isPhoto){
            tvValue.setVisibility(View.GONE);
            ivValue.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(Values.BASE_URL+sample.getValue()).into(ivValue);
        }else{
            ivValue.setVisibility(View.GONE);
            tvValue.setVisibility(View.VISIBLE);
            tvValue.setText(sample.getValue());
        }

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
        ((MainActivity) getActivity()).toggleFABVisibility(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




}