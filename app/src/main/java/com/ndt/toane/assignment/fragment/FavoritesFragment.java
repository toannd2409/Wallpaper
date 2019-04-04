package com.ndt.toane.assignment.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.adapter.FavoriteAdapter;
import com.ndt.toane.assignment.database.Database;
import com.ndt.toane.assignment.models.favorite.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerview;
    private Database databaseHelper;
    private List<Favorite> favoriteList;
    FavoriteAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.favorite);
            }
        }
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        initView(view);
        getFavorites();
        return view;
    }

    private void initView(View view) {
        recyclerview = view.findViewById(R.id.recyler_favorites);
        databaseHelper = new Database(getContext());

    }

    public void getFavorites() {
        favoriteList = new ArrayList<>();
        favoriteList = databaseHelper.getAllImage();
        adapter = new FavoriteAdapter(getContext(), favoriteList, R.layout.item_latest);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
