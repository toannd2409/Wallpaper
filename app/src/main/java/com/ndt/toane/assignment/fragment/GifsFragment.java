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
import com.ndt.toane.assignment.adapter.GifAdapter;
import com.ndt.toane.assignment.adapter.LatestAdapter;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.gif.Gifs;
import com.ndt.toane.assignment.models.gif.ListGifs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GifsFragment extends Fragment {
    private RecyclerView rvGifs;
    private List<Gifs> gifsList;
    private GifAdapter adapter;
    private ApiEndpoint apiEndpoint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.gif);
            }
        }
        View view = inflater.inflate(R.layout.fragment_gifs, container, false);
        initView(view);
        getGifs();
        // Inflate the layout for this fragment
        return view;
    }

    private void initView(View view) {
        rvGifs = view.findViewById(R.id.recyler_gifs);
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
    }

    public void getGifs() {
        retrofit2.Call<ListGifs> call = apiEndpoint.getGifs();
        call.enqueue(new Callback<ListGifs>() {
            @Override
            public void onResponse(Call<ListGifs> call, Response<ListGifs> response) {
                //Khởi tạo list
                gifsList = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                gifsList = response.body().getGifsList();

                //Add list vào adapter
                rvGifs.setHasFixedSize(true);
                adapter = new GifAdapter(getContext(),gifsList,R.layout.item_latest);
                rvGifs.setLayoutManager(new GridLayoutManager(getContext(),2));
                rvGifs.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ListGifs> call, Throwable t) {

            }
        });
    }
}
