package com.ndt.toane.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.activity.DetailWallpaperActivity;
import com.ndt.toane.assignment.common.Connstants;
import com.ndt.toane.assignment.models.latest.Latest;

import java.util.List;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> implements Connstants{

    Context context;
    List<Latest> latests;
    int rowLayouts;

    public LatestAdapter(List<Latest> latests, Context context, int rowLayouts) {
        this.latests = latests;
        this.context = context;
        this.rowLayouts = rowLayouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(rowLayouts, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Latest latest = latests.get(position);
        holder.tvCountView.setText(latest.getTotalViews());
        Glide.with(context)
                .load(latest.getWallpaperImageThumb())
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_black).error(R.drawable.ic_image_black))
                .into(holder.img);
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = latest.getWallpaperImage();
                Intent intent = new Intent(context, DetailWallpaperActivity.class);
                intent.putExtra(WALLPAPER_ID,latest.getId());
                intent.putExtra(URL,url);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return latests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvCountLike, tvCountView;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgLatest);
            tvCountLike = itemView.findViewById(R.id.tvFavorite);
            tvCountView = itemView.findViewById(R.id.tvViewCount);


        }
    }
}
