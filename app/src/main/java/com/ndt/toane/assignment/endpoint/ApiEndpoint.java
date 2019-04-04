package com.ndt.toane.assignment.endpoint;

import com.ndt.toane.assignment.models.about.ListAbout;
import com.ndt.toane.assignment.models.category.ListCategory;
import com.ndt.toane.assignment.models.gif.ListGifs;
import com.ndt.toane.assignment.models.latest.ListLatest;
import com.ndt.toane.assignment.models.listcategory.ListCatId;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {
    //latest
    @GET("api.php?latest")
    Call<ListLatest> getLatest();
    //category
    @GET("api.php?cat_list")
    Call<ListCategory> getCategory();
    //gifs
    @GET("api.php?gif_list")
    Call<ListGifs> getGifs();
    // about
    @GET("api.php")
    Call<ListAbout> getAboutUs();

    // Detail Image
    @GET("api.php?")
    Call<ListLatest> getLatestDetail(@Query("wallpaper_id") String id);

    @GET("api.php?")
    Call<ListGifs> getGifDetail(@Query("gif_id") String id);

    // list by category ID
    @GET("api.php?")
    Call<ListCatId> getListCatId(@Query("cat_id") String id);
}
