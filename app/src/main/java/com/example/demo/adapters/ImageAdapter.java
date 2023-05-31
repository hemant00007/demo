package com.example.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.ImageDB;
import com.example.demo.utils.ImageStore;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.viewHolder> {

    private Context context;
    private List<ImageDB> imagelist = new ArrayList<>();

    public ImageAdapter(Context context, List<ImageDB> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageitem,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.imageView.setImageBitmap(ImageStore.loadImageFromStorage(imagelist.get(position).getImagePath()));


    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagelayout);
        }
    }
}
