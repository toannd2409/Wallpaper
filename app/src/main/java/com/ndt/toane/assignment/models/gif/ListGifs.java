package com.ndt.toane.assignment.models.gif;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListGifs {

    @SerializedName("HD_WALLPAPER")
    @Expose
    private List<Gifs> gifsList = null;

    public List<Gifs> getGifsList() {
        return gifsList;
    }

    public void setGifsList(List<Gifs> gifsList) {
        this.gifsList = gifsList;
    }
}
