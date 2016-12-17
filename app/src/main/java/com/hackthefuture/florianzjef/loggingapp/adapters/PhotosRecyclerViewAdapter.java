package com.hackthefuture.florianzjef.loggingapp.adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.rest.Values;

import java.util.List;

public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<PhotosRecyclerViewAdapter.PhotoViewHolder>{

    private Context context;
    private List<Sample> samples;
    private PhotosRecyclerViewAdapter.LogInteractionListener listener;

    public PhotosRecyclerViewAdapter(Context context, List<Sample> samples, PhotosRecyclerViewAdapter.LogInteractionListener listener) {
        this.context=context;
        this.samples = samples;
        this.listener=listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        final Sample sample = samples.get(position);
        holder.tvTitle.setText(sample.getName());
        holder.tvDate.setText(sample.getDatetime());
        Glide.with(context).load(Values.BASE_URL+sample.getValue()).into(holder.ivPhoto);

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

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPhoto;
        public CardView cvSample;

        PhotoViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            cvSample = (CardView) itemView.findViewById(R.id.cvSample);
        }
    }

    public interface LogInteractionListener{
        void onLogClicked(PhotoViewHolder holder, int pos);
    }
}
