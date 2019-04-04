package com.ndt.toane.assignment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.ndt.toane.assignment.R;
import com.ndt.toane.assignment.apiClient.ApiClient;
import com.ndt.toane.assignment.endpoint.ApiEndpoint;
import com.ndt.toane.assignment.models.about.ListAbout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {
    private TextView tvAppName;
    private TextView tvtextVersion;
    private TextView tvVersion;
    private TextView tvAuthor;
    private TextView tvEmail;
    private TextView tvContact;
    private TextView tvDecription;
    private ApiEndpoint apiEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        getAbout();
    }

    private void displayInfo(ListAbout body) {
        tvAppName.setText(body.getListAbout().get(0).getAppName());
        tvVersion.setText(body.getListAbout().get(0).getAppVersion());
        tvAuthor.setText(body.getListAbout().get(0).getAppAuthor());
        tvEmail.setText(body.getListAbout().get(0).getAppEmail());
        tvContact.setText(body.getListAbout().get(0).getAppContact());
        tvDecription.setText(Html.fromHtml((body.getListAbout().get(0).getAppDescription())));
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);



        tvAppName = (TextView) findViewById(R.id.tvAppName);
        tvtextVersion = (TextView) findViewById(R.id.tvtextVersion);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvContact = (TextView) findViewById(R.id.tvContact);
        tvDecription = (TextView) findViewById(R.id.tvDecription);
        apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getAbout() {
        retrofit2.Call<ListAbout> call = apiEndpoint.getAboutUs();
        call.enqueue(new Callback<ListAbout>() {
            @Override
            public void onResponse(Call<ListAbout> call, Response<ListAbout> response) {
                tvAppName.setText(response.body().getListAbout().get(0).getAppName());
                tvVersion.setText(response.body().getListAbout().get(0).getAppVersion());
                tvAuthor.setText(response.body().getListAbout().get(0).getAppAuthor());
                tvEmail.setText(response.body().getListAbout().get(0).getAppEmail());
                tvContact.setText(response.body().getListAbout().get(0).getAppContact());
                tvDecription.setText(Html.fromHtml((response.body().getListAbout().get(0).getAppDescription())));
            }

            @Override
            public void onFailure(Call<ListAbout> call, Throwable t) {

            }
        });
    }
}
