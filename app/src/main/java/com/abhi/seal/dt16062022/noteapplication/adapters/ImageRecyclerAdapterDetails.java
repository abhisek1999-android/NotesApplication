package com.abhi.seal.dt16062022.noteapplication.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.seal.dt16062022.noteapplication.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageRecyclerAdapterDetails extends RecyclerView.Adapter<ImageRecyclerAdapterDetails.ViewHolder> {

    private List<String> uriList;

    Context mContext;
    public ImageRecyclerAdapterDetails(List<String> uriList, Context mContext) {
        this.uriList = uriList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_image_view_big,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    Glide.with(mContext).load(uriList.get(position).replace("[","").replace("]","").trim()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
        }
    }
}
