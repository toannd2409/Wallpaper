package com.ndt.toane.assignment.models.listcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCatId {

    @SerializedName("HD_WALLPAPER")
    @Expose
    private List<CatId> listCatId = null;

    public List<CatId> getListCatId() {
        return listCatId;
    }

    public void setListCatId(List<CatId> listCatId) {
        this.listCatId = listCatId;
    }
}
