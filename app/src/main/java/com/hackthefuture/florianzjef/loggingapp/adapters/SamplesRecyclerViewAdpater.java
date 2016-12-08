package com.hackthefuture.florianzjef.loggingapp.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;

import java.util.List;

public class SamplesRecyclerViewAdpater extends RecyclerView.Adapter<SamplesRecyclerViewAdpater.LogViewHolder>{

    private List<Sample> samples;
    private LogInteractionListener listener;

    public SamplesRecyclerViewAdpater(List<Sample> samples, LogInteractionListener listener) {
        this.samples = samples;
        this.listener=listener;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_log, parent, false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LogViewHolder holder, final int position) {
        final Sample log = samples.get(position);
        holder.tvTitle.setText(log.getName());

        ViewCompat.setTransitionName(holder.tvTitle, String.valueOf(position) + "_title");
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLogClicked(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(samples !=null)
            return samples.size();
        else
            return 0;
    }

    public void setSamples(List<Sample> logs) {
        this.samples = logs;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;

        LogViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    public interface LogInteractionListener{
        void onLogClicked(LogViewHolder holder, int pos);
    }
}
