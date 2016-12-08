package com.hackthefuture.florianzjef.loggingapp.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.activities.MainActivity;
import com.hackthefuture.florianzjef.loggingapp.adapters.LogsRecyclerViewAdpater;
import com.hackthefuture.florianzjef.loggingapp.animation.DetailsTransition;
import com.hackthefuture.florianzjef.loggingapp.repo.Repostiory;


public class LogsFragment extends Fragment implements LogsRecyclerViewAdpater.LogInteractionListener{

    private View rootView;
    private RecyclerView rvLogs;
    private FloatingActionButton fab;

    public static LogsFragment newInstance() {
        LogsFragment fragment = new LogsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_logs, container, false);

        rvLogs = (RecyclerView) rootView.findViewById(R.id.rvLogs);
        rvLogs.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvLogs.setAdapter(new LogsRecyclerViewAdpater(Repostiory.getLogs(), this));

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
    }

    @Override
    public void onLogClicked(LogsRecyclerViewAdpater.LogViewHolder holder, int pos) {

        LogDetailsFragment logDetailsFragment = LogDetailsFragment.newInstance(Repostiory.getLogs().get(pos));

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
}
