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
import com.ndt.toane.assignment.adapter.LatestAdapter;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.latest.Latest;
import com.ndt.toane.assignment.models.latest.ListLatest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {

    private RecyclerView rvLatest;
    private List<Latest> listLatest;
    private LatestAdapter adapter;
    private ApiEndpoint apiEndpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.latest);
            }
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        initView(view);
        getLatests();
        return view;

    }

    private void getLatests() {
        Call<ListLatest> call = apiEndpoint.getLatest();
        call.enqueue(new Callback<ListLatest>() {
            @Override
            public void onResponse(Call<ListLatest> call, Response<ListLatest> response) {
                //Khởi tạo list
                listLatest = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                listLatest = response.body().getLatestList();
                //Add list vào adapter

                rvLatest.setHasFixedSize(true);
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                adapter = new LatestAdapter(listLatest, getContext(), R.layout.item_latest);
                rvLatest.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rvLatest.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ListLatest> call, Throwable t) {

            }
        });

    }
    private void initView(View view) {
        rvLatest = view.findViewById(R.id.recyler_latest);
            apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);

    }

}
