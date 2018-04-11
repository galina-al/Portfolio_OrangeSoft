package com.example.user.portfolio;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    List<ItemPhoto> photos;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rv;
        TextView name;
        TextView size;
        TextView date;
        ImageView imageMini;

        public MyViewHolder(View itemView) {
            super(itemView);

//          rv = (RecyclerView) itemView.findViewById(R.id.rv);
            name = (TextView) itemView.findViewById(R.id.photo_name);
            size = (TextView) itemView.findViewById(R.id.photo_size);
            date = (TextView) itemView.findViewById(R.id.photo_date);
            imageMini = (ImageView) itemView.findViewById(R.id.image_mini);
        }
    }

    public MyRecyclerAdapter(List<ItemPhoto> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(photos.get(position).name);
        holder.size.setText(photos.get(position).size);
        holder.date.setText(photos.get(position).date);
        ImageHelper.displayImage(Uri.fromFile(new File(photos.get(position).path)).toString(), holder.imageMini, null);
    }

    @Override
    public int getItemCount() {

        return photos.size();
    }


}
