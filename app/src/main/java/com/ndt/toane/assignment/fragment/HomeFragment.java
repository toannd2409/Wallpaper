package com.ndt.toane.assignment.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.adapter.GifAdapter;
import com.ndt.toane.assignment.adapter.LatestAdapter;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.gif.Gifs;
import com.ndt.toane.assignment.models.gif.ListGifs;
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
public class HomeFragment extends Fragment {
    RecyclerView rvLatest, rvGif;
    private List<Latest> latestList;
    private List<Gifs> gifsList;
    private List<Latest> newLatestList;
    private List<Gifs> newGifsList;
    private LatestAdapter latestAdapter;
    private GifAdapter gifAdapter;

    private Button btnMoreLatest;
    private Button btnMoreGifs;



    // cái đêchk lúc chiều nay gặp lỗi này rồ mà giờ vẫn ko ngộ ra dc hả em. Đã khởi tạo cáo apiEndpoint đâu

    private ApiEndpoint apiEndpoint;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.home);
            }
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        initView(view);
        getLatests();
        getGifs();
        btnMoreLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mes","mes");
                Fragment  fragment = new LatestFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnMoreGifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mes","mes");
                Fragment  fragment = new GifsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void initView(View view) {
        rvGif = view.findViewById(R.id.recycler_gif);
        rvLatest = view.findViewById(R.id.recycler_latest);
        latestList = new ArrayList<>();
        gifsList = new ArrayList<>();
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
        btnMoreLatest = (Button) view.findViewById(R.id.btnMoreLatest);
        btnMoreGifs = (Button) view.findViewById(R.id.btnMoreGifs);
    }

    public void getLatests() {
        Call<ListLatest> call = apiEndpoint.getLatest();
        call.enqueue(new Callback<ListLatest>() {
            @Override
            public void onResponse(Call<ListLatest> call, Response<ListLatest> response) {
                //Khởi tạo list
                latestList = new ArrayList<>();
                newLatestList = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                latestList = response.body().getLatestList();
                //Add list vào adapter

                //Cách thủ công
                newLatestList.add(latestList.get(0));
                newLatestList.add(latestList.get(1));
                newLatestList.add(latestList.get(2));
                newLatestList.add(latestList.get(3));

                rvLatest.setHasFixedSize(true);
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

                latestAdapter = new LatestAdapter(newLatestList, getContext(), R.layout.item_latest);
                rvLatest.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rvLatest.setAdapter(latestAdapter);
            }

            @Override
            public void onFailure(Call<ListLatest> call, Throwable t) {

            }
        });
    }

    public void getGifs() {
        Call<ListGifs> call = apiEndpoint.getGifs();
        call.enqueue(new Callback<ListGifs>() {
            @Override
            public void onResponse(Call<ListGifs> call, Response<ListGifs> response) {
                //Khởi tạo list
                gifsList = new ArrayList<>();
                newGifsList = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                gifsList = response.body().getGifsList();
                //Add list vào adapterbe
                rvGif.setHasFixedSize(true);

                //Cách tư duy nghệ thuật
                for (int i =0;i < gifsList.size();i++){
                    if (i>3)
                        break;
                    newGifsList.add(gifsList.get(i));
                }

                gifAdapter = new GifAdapter(getContext(), newGifsList, R.layout.item_latest);
                rvGif.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rvGif.setAdapter(gifAdapter);
            }

            @Override
            public void onFailure(Call<ListGifs> call, Throwable t) {

            }
        });
    }
}
