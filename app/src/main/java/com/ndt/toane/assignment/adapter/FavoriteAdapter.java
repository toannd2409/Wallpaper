package com.ndt.toane.assignment.adapter;

import android.content.Context;
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
import com.ndt.toane.assignment.database.Database;
import com.ndt.toane.assignment.models.favorite.Favorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    Context context;
    List<Favorite> favorite;
    int layout;

    public FavoriteAdapter(Context context, List<Favorite> favorite, int layout) {
        this.context = context;
        this.favorite = favorite;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_latest, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCountView.setText(favorite.get(position).getCountView());
        holder.tvCountLike.setText(favorite.get(position).getCountLike());
        Glide.with(context).load(favorite.get(position).getLink())
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_black).error(R.drawable.ic_image_black))
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return favorite.size();
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
