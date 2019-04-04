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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.activity.DetailWallpaperActivity;
import com.ndt.toane.assignment.common.Connstants;
import com.ndt.toane.assignment.models.listcategory.CatId;
import com.ndt.toane.assignment.models.listcategory.ListCatId;

import java.util.List;

public class ListCatAdapter extends RecyclerView.Adapter<ListCatAdapter.ViewHolder> implements Connstants {
    Context context;
    List<CatId> listCatIds;
    int rowLayouts;

    public ListCatAdapter(Context context, List<CatId> listCatIds, int rowLayouts) {
        this.context = context;
        this.listCatIds = listCatIds;
        this.rowLayouts = rowLayouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(rowLayouts,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CatId catId = listCatIds.get(position);
        Glide.with(context)
                .load(catId.getWallpaperImageThumb()).apply(new RequestOptions().placeholder(R.drawable.ic_image_black).error(R.drawable.ic_image_black))
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = catId.getWallpaperImage();
                Intent intent = new Intent(context, DetailWallpaperActivity.class);
                intent.putExtra(CAT_ID,catId.getWallpaperImage());
                intent.putExtra(URL,url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCatIds.size();
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
