package com.example.user.portfolio;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView size;
        TextView date;
        ImageView imageMini;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.photo_name);
            size = (TextView) itemView.findViewById(R.id.photo_size);
            date = (TextView) itemView.findViewById(R.id.photo_date);
            imageMini = (ImageView) itemView.findViewById(R.id.image_mini);
        }
    }

}
