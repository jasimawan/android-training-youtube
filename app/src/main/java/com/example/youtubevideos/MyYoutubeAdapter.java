package com.example.youtubevideos;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyYoutubeAdapter extends RecyclerView.Adapter<MyYoutubeAdapter.MyViewHolder>{
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MyYoutubeAdapter.clickListener = clickListener;
    }
    Activity activity;
    ArrayList<videoDetails> videoList;


    public MyYoutubeAdapter(Activity activity, ArrayList<videoDetails> videoList) {
        this.videoList = videoList;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView title, description;
        public ImageView image;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            image = v.findViewById(R.id.imageView);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyYoutubeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.customtems, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        videoDetails videoData = videoList.get(i);


        Picasso.get().load(videoData.url).into(holder.image);
        holder.title.setText(videoData.title);

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return videoList.size();
    }

}
