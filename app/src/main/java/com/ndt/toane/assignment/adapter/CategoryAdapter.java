package com.ndt.toane.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.common.Connstants;
import com.ndt.toane.assignment.fragment.ListCategoryFragment;
import com.ndt.toane.assignment.models.category.Category;
import com.ndt.toane.assignment.models.category.ListCategory;
import com.ndt.toane.assignment.models.latest.Latest;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Connstants {
    Context context;
    List<Category> categories;
    int rowLayouts;

    public CategoryAdapter(Context context, List<Category> categories, int rowLayouts) {
        this.context = context;
        this.categories = categories;
        this.rowLayouts = rowLayouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(rowLayouts,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categories.get(position);
        holder.tvCoutItem.setText("("+category.getTotalWallpaper()+")");
        holder.tvNameCategory.setText(category.getCategoryName());
        Glide.with(context).load(category.getCategoryImageThumb())
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_black).error(R.drawable.ic_image_black))
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String id = category.getCid();
                String name = category.getCategoryName();
                bundle.putString(ID_CAT,id);
                bundle.putString(CAT_NAME,name);
                Fragment fragment = new ListCategoryFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        if(position%2==0){
            holder.tvNameCategory.setTextColor(Color.RED);
        }else {
            holder.tvNameCategory.setTextColor(Color.BLUE);
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvNameCategory,tvCoutItem;
        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgItemCategory);
            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            tvCoutItem = itemView.findViewById(R.id.tvCountCatogry);



        }
    }
}
