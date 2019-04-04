package com.ndt.toane.assignment.models.latest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListLatest {

    @SerializedName("HD_WALLPAPER")
    @Expose
    public List<Latest> latestList = null;


    public List<Latest> getLatestList() {
        return latestList;
    }

    public void setLatestList(List<Latest> latestList) {
        this.latestList = latestList;
    }
}
