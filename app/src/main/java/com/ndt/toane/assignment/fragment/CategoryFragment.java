package com.ndt.toane.assignment.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.adapter.CategoryAdapter;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.category.Category;
import com.ndt.toane.assignment.models.category.ListCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private RecyclerView rvListCategory;
    private CategoryAdapter adapter;
    private List<Category> listCategories;
    private ApiEndpoint apiEndpoint ;
    private List<Category> newlistCategories;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(R.string.category);
            }
        }
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        getCategories();
        getNewCate();
        return view;
    }

    private void initView(View view) {
        rvListCategory = view.findViewById(R.id.recyclerviewCategory);
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
    }

    public void getCategories() {
        retrofit2.Call<ListCategory> call = apiEndpoint.getCategory();
        call.enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                //Khởi tạo list
                listCategories = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                listCategories = response.body().getCategoryList();

                //Add list vào adapter
                rvListCategory.setHasFixedSize(true);
                adapter = new CategoryAdapter(getContext(), listCategories,R.layout.item_category);
                rvListCategory.setLayoutManager(new GridLayoutManager(getContext(),1));
                rvListCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ListCategory> call, Throwable t) {

            }
        });
    }

    public void getNewCate() {
        retrofit2.Call<ListCategory> call = apiEndpoint.getCategory();
        call.enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                //Khởi tạo list
                listCategories = new ArrayList<>();
                //Get dữ liệu JSON trả về list
                newlistCategories = new ArrayList<>();
                listCategories = response.body().getCategoryList();

                //Add list vào adapter
                rvListCategory.setHasFixedSize(true);
                for (int i =0;i < listCategories.size();i++){
                    if (i>5)
                        break;
                    newlistCategories.add(listCategories.get(i));
                }

                adapter = new CategoryAdapter(getContext(), newlistCategories,R.layout.item_category);
                rvListCategory.setLayoutManager(new GridLayoutManager(getContext(),1));
                rvListCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ListCategory> call, Throwable t) {

            }
        });
    }
}
