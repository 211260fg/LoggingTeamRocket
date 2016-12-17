package com.hackthefuture.florianzjef.loggingapp.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.adapters.PhotosRecyclerViewAdapter;
import com.hackthefuture.florianzjef.loggingapp.adapters.SamplesRecyclerViewAdapter;
import com.hackthefuture.florianzjef.loggingapp.animation.DetailsTransition;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.repo.OnSamplesLoadedListener;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.util.List;


public class PhotosFragment extends Fragment implements PhotosRecyclerViewAdapter.LogInteractionListener, OnSamplesLoadedListener {

    private View rootView;
    private RecyclerView rvLogs;
    private FloatingActionButton fab;
    private static final String ARG_ALLSAMPLES="ALLSAMPLES";

    private boolean loadAllSamples=true;

    private SwipeRefreshLayout swipeRefreshLayout;


    public static PhotosFragment newInstance(boolean allSamples){
        Bundle args = new Bundle();
        args.putBoolean(ARG_ALLSAMPLES, allSamples);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAllSamples = getArguments().getBoolean(ARG_ALLSAMPLES);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_samples, container, false);

        rvLogs = (RecyclerView) rootView.findViewById(R.id.rvLogs);
        rvLogs.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvLogs.setAdapter(new PhotosRecyclerViewAdapter(getContext(), Repository.getSamples(), this));

        Repository.addListener(this);

        if (loadAllSamples)
            Repository.loadAllPhotos();
        else
            Repository.loadResearcherPhotos();


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(loadAllSamples)
                    Repository.loadAllPhotos();
                else
                    Repository.loadResearcherPhotos();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setEnabled(true);

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
        Repository.removeListener(this);
    }

    public void reloadSamples(boolean loadAllSamples){
        this.loadAllSamples=loadAllSamples;
        swipeRefreshLayout.setRefreshing(true);
        if(loadAllSamples)
            Repository.loadAllSamples();
        else
            Repository.loadResearcherSamples();
    }

    @Override
    public void onLogClicked(PhotosRecyclerViewAdapter.PhotoViewHolder holder, int pos) {

        SampleDetailsFragment logDetailsFragment = SampleDetailsFragment.newInstance(Repository.getSamples().get(pos), true);

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
                .addSharedElement(holder.tvDate, "tvDate")
                .addSharedElement(holder.cvSample, "cvSample")
                .replace(R.id.fragmentPane, logDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSamplesLoadSuccess(List<Sample> samples) {
        ((PhotosRecyclerViewAdapter) rvLogs.getAdapter()).setSamples(samples);
        rvLogs.getAdapter().notifyDataSetChanged();

        PhotosRecyclerViewAdapter adapter = (PhotosRecyclerViewAdapter) rvLogs.getAdapter();
        adapter.setSamples(samples);
        adapter.notifyDataSetChanged();
        if(swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }



    @Override
    public void onSamplesLoadFailed(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        if(swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
