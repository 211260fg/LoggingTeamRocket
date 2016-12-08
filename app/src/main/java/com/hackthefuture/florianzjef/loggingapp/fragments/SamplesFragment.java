package com.hackthefuture.florianzjef.loggingapp.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.adapters.SamplesRecyclerViewAdpater;
import com.hackthefuture.florianzjef.loggingapp.animation.DetailsTransition;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.repo.OnSamplesLoadedListener;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.util.List;


public class SamplesFragment extends Fragment implements SamplesRecyclerViewAdpater.LogInteractionListener, OnSamplesLoadedListener{

    private View rootView;
    private RecyclerView rvLogs;
    private FloatingActionButton fab;

    public static SamplesFragment newInstance() {
        SamplesFragment fragment = new SamplesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_samples, container, false);

        rvLogs = (RecyclerView) rootView.findViewById(R.id.rvLogs);
        rvLogs.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvLogs.setAdapter(new SamplesRecyclerViewAdpater(Repository.getSamples(), this));

        Repository.addListener(this);
        Repository.loadSamples();

        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).toggleFABVisibility(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).toggleFABVisibility(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).toggleFABVisibility(false);
        Repository.removeListener(this);
    }

    @Override
    public void onLogClicked(SamplesRecyclerViewAdpater.LogViewHolder holder, int pos) {

        SampleDetailsFragment logDetailsFragment = SampleDetailsFragment.newInstance(Repository.getSamples().get(pos));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            logDetailsFragment.setSharedElementEnterTransition(new DetailsTransition());
            logDetailsFragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            logDetailsFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        ((MainActivity) getActivity()).toggleFABVisibility(false);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.tvTitle, "tvTitle")
                .replace(R.id.fragmentPane, logDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSamplesLoadSuccess(List<Sample> samples) {
        ((SamplesRecyclerViewAdpater) rvLogs.getAdapter()).setSamples(samples);
        rvLogs.getAdapter().notifyDataSetChanged();

            SamplesRecyclerViewAdpater adapter = (SamplesRecyclerViewAdpater) rvLogs.getAdapter();
            adapter.setSamples(samples);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onSamplesLoadFailed(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
