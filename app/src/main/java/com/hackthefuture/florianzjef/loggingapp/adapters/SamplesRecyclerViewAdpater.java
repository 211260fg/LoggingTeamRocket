package com.hackthefuture.florianzjef.loggingapp.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;

import java.util.List;

public class SamplesRecyclerViewAdpater extends RecyclerView.Adapter<SamplesRecyclerViewAdpater.SampleViewHolder>{

    private List<Sample> samples;
    private LogInteractionListener listener;

    public SamplesRecyclerViewAdpater(List<Sample> samples, LogInteractionListener listener) {
        this.samples = samples;
        this.listener=listener;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sample, parent, false);
        return new SampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SampleViewHolder holder, final int position) {
        final Sample sample = samples.get(position);
        holder.tvTitle.setText(sample.getName());
        holder.tvDate.setText(sample.getDatetime());

        ViewCompat.setTransitionName(holder.tvTitle, String.valueOf(position) + "_title");
        ViewCompat.setTransitionName(holder.tvDate, String.valueOf(position) + "_date");
        ViewCompat.setTransitionName(holder.cvSample, String.valueOf(position) + "_cv");

        holder.cvSample.setOnClickListener(new View.OnClickListener() {
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

    public class SampleViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvDate;
        public CardView cvSample;

        SampleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            cvSample = (CardView) itemView.findViewById(R.id.cvSample);
        }
    }

    public interface LogInteractionListener{
        void onLogClicked(SampleViewHolder holder, int pos);
    }
}
