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
import com.hackthefuture.florianzjef.loggingapp.models.Log;


public class LogDetailsFragment extends Fragment {

    private static final String ARG_LOG = "LOG";
    private Log log;
    private View rootView;

    public static LogDetailsFragment newInstance(Log log) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOG, log);

        LogDetailsFragment fragment = new LogDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = (Log) getArguments().getSerializable(ARG_LOG);
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
        rootView = inflater.inflate(R.layout.fragment_log_details, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);

        tvTitle.setText(log.getTitle());
        tvDescription.setText(log.getDescription());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).setActionbarArrow(true);
    }

}
