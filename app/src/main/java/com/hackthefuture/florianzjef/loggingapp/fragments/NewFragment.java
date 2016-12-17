package com.hackthefuture.florianzjef.loggingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;

public class NewFragment extends Fragment {

    private View rootView;
    private FragmentTabHost mTabHost;

    public static NewFragment newInstance() {
        NewFragment fragment = new NewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new, container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("New Sample").setIndicator("New Sample"),
                NewSampleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("New Photo").setIndicator("New Photo"),
                NewPhotoFragment.class, null);

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
        ((MainActivity)getActivity()).setActionbarArrow(true);
    }
}
