package com.ndt.toane.assignment.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ndt.toane.assignment.models.category.Category;
import com.ndt.toane.assignment.models.latest.Latest;

import java.util.List;

public class ListCategory {
    @SerializedName("HD_WALLPAPER")
    @Expose
    public List<Category> categoryList = null;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
