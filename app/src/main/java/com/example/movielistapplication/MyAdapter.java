package com.example.movielistapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static ArrayList<MovieResultObj> mDataset;
    private static Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView yearView;
        ImageView imageView;
        int position;

        public MyViewHolder(View v) {
            super(v);

            titleView = v.findViewById(R.id.info_text);
            yearView = v.findViewById(R.id.year_text);
            imageView = v.findViewById(R.id.image);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MovieDetail.class);

                    intent.putExtra("title", mDataset.get(position).getTitle());
                    intent.putExtra("image", mDataset.get(position).getImage());
                    intent.putExtra("rating", mDataset.get(position).getRating());
                    intent.putExtra("genre", mDataset.get(position).getGenre());
                    intent.putExtra("releaseYear", mDataset.get(position).getReleaseYear());
                    mContext.startActivity(intent);

                }
            });
        }
    }

    public MyAdapter(ArrayList<MovieResultObj> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieResultObj movieResultObj = mDataset.get(position);
        holder.position = position;
        holder.titleView.setText(movieResultObj.getTitle());
        holder.yearView.setText(movieResultObj.getReleaseYear());
        Picasso.get().load(movieResultObj.getImage()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
