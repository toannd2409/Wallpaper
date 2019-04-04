package com.ndt.toane.assignment.models.about;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAbout {
    @SerializedName("HD_WALLPAPER")
    @Expose
    private List<AboutUs> listAbout = null;

    public List<AboutUs> getListAbout() {
        return listAbout;
    }

    public void setListAbout(List<AboutUs> listAbout) {
        this.listAbout = listAbout;
    }
}
