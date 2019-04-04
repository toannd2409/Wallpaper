package com.ndt.toane.assignment.activity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.clans.fab.FloatingActionMenu;
import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.common.Connstants;
import com.ndt.toane.assignment.control.SaveImage;
import com.ndt.toane.assignment.database.Database;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.favorite.Favorite;
import com.ndt.toane.assignment.models.gif.Gifs;
import com.ndt.toane.assignment.models.gif.ListGifs;
import com.ndt.toane.assignment.models.latest.Latest;
import com.ndt.toane.assignment.models.latest.ListLatest;
import com.ndt.toane.assignment.models.listcategory.CatId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailWallpaperActivity extends AppCompatActivity implements Connstants, View.OnClickListener {

    private FloatingActionMenu floatMenu;
    private com.github.clans.fab.FloatingActionButton btnLike;
    private com.github.clans.fab.FloatingActionButton btnShare;
    private com.github.clans.fab.FloatingActionButton btnSave;
    private com.github.clans.fab.FloatingActionButton btnSetWallpaper;


    private List<Latest> listLatests;
    private List<Gifs> listGifs;
    private List<CatId> listCatIds;
    private ApiEndpoint apiEndpoint;
    private Toolbar toolbar;
    private ImageView imgImage;
    private String wallpaperId;
    private String url;
    private String id;
    private RelativeLayout layout;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        initViews();
        btnLike.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnSetWallpaper.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initViews() {
        //intit float
        floatMenu = findViewById(R.id.floatMenu);
        btnLike = findViewById(R.id.btnLike);
        btnShare = findViewById(R.id.btnShare);
        btnSave = findViewById(R.id.btnSave);
        btnSetWallpaper = findViewById(R.id.btnSetWallpaper);
        layout = findViewById(R.id.layout);
        database = new Database(this);
        // set image
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
        Intent intent = getIntent();
        imgImage = findViewById(R.id.img_image);
        if (intent.hasExtra(WALLPAPER_ID)) {
            wallpaperId = intent.getStringExtra(WALLPAPER_ID);
            url = intent.getStringExtra(URL);
            getDetail();
        } else if (intent.hasExtra(GIF_ID)) {
            wallpaperId = intent.getStringExtra(GIF_ID);
            url = intent.getStringExtra(URL);
            getGifDetail();

        } else {
            wallpaperId = intent.getStringExtra(CAT_ID);
            url = intent.getStringExtra(URL);
            getCatIdDetail();
        }

    }

    public void getDetail() {
        Call<ListLatest> call = apiEndpoint.getLatestDetail(wallpaperId);
        call.enqueue(new Callback<ListLatest>() {
            @Override
            public void onResponse(Call<ListLatest> call, Response<ListLatest> response) {
                listLatests = new ArrayList<>();
                listLatests = response.body().getLatestList();
                for (Latest latest : listLatests) {
                    Glide.with(getApplicationContext())
                            .load(latest.getWallpaperImageThumb())
                            .into(imgImage);
                }
            }

            @Override
            public void onFailure(Call<ListLatest> call, Throwable t) {

            }
        });
    }

    public void getGifDetail() {
        Call<ListGifs> call = apiEndpoint.getGifDetail(wallpaperId);
        call.enqueue(new Callback<ListGifs>() {
            @Override
            public void onResponse(Call<ListGifs> call, Response<ListGifs> response) {
                listGifs = new ArrayList<com.ndt.toane.assignment.models.gif.Gifs>();
                listGifs = response.body().getGifsList();
                for (Gifs gifs : listGifs) {
                    Glide.with(getApplicationContext())
                            .load(gifs.getGifImage())
                            .into(imgImage);
                }
            }

            @Override
            public void onFailure(Call<ListGifs> call, Throwable t) {

            }
        });
    }

    public void getCatIdDetail() {
        Glide.with(getApplicationContext()).load(wallpaperId).into(imgImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLike:
                addLike();
                floatMenu.close(true);
                break;
            case R.id.btnShare:
                shareImage(url, this);
                floatMenu.close(true);
                break;
            case R.id.btnSave:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
//                SaveImage saveImage=new SaveImage(DetailWallpaperActivity.this,url,getResources().getString(R.string.app_name));
//                saveImage.downloadImage();
                floatMenu.close(true);
                break;
            case R.id.btnSetWallpaper:
                Glide.with(DetailWallpaperActivity.this)
                        .asBitmap()
                        .load(url)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_image_black).error(R.drawable.ic_image_black))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                try {
                                    WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);
                                    Snackbar.make(layout, "Wallpaper was set !", Snackbar.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                floatMenu.close(true);
                break;
        }
    }

    private void shareImage(String url, final Context context) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Uri uri = getLocalBitmapUri(resource, DetailWallpaperActivity.this);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }
        });
    }

    private Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void addLike() {
        Favorite favorite = new Favorite(wallpaperId, url);
//        Favorite favorite = new Favorite(url, "0", "0");
        if (database.addImage(favorite)) {
            Toast.makeText(this, "Added Image", Toast.LENGTH_SHORT).show();
        }
    }
}
