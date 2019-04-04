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
import com.ndt.toane.assignment.adapter.ListCatAdapter;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.common.Connstants;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.listcategory.CatId;
import com.ndt.toane.assignment.models.listcategory.ListCatId;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListCategoryFragment extends Fragment implements Connstants {
    private RecyclerView rvListCat;
    private List<CatId> listCatid;
    private ListCatAdapter adapter;
    private ApiEndpoint apiEndpoint;
    private String wallpaperId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            String name = this.getArguments().getString(CAT_NAME);
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(name);
            }
        }

        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        initView(view);
        getListCatid();
        return view;
    }

    private void initView(View view) {
        wallpaperId = this.getArguments().getString(ID_CAT);
        rvListCat = view.findViewById(R.id.recyler_list_cat);
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
    }

    public void getListCatid() {
        Call<ListCatId> call = apiEndpoint.getListCatId(wallpaperId);
        call.enqueue(new Callback<ListCatId>() {
            @Override
            public void onResponse(Call<ListCatId> call, Response<ListCatId> response) {
                listCatid = new ArrayList<>();
                listCatid = response.body().getListCatId();

                rvListCat.setHasFixedSize(true);
                adapter = new ListCatAdapter(getContext(),listCatid,R.layout.item_latest);
                rvListCat.setLayoutManager(new GridLayoutManager(getContext(),2));
                rvListCat.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ListCatId> call, Throwable t) {
            }
        });


    }
}
