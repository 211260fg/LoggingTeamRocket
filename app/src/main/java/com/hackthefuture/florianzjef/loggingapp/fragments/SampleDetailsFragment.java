package com.hackthefuture.florianzjef.loggingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;


public class SampleDetailsFragment extends Fragment {

    private static final String ARG_LOG = "LOG";
    private Sample sample;
    private View rootView;

    public static SampleDetailsFragment newInstance(Sample log) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOG, log);

        SampleDetailsFragment fragment = new SampleDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sample = (Sample) getArguments().getSerializable(ARG_LOG);
        setHasOptionsMenu(true);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample_details, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        TextView tvDatetime = (TextView) rootView.findViewById(R.id.tvDate);

        tvTitle.setText(sample.getName());
        tvDescription.setText(sample.getRemark());
        tvDescription.setText(sample.getRemark());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).setActionbarArrow(true);
    }

}
