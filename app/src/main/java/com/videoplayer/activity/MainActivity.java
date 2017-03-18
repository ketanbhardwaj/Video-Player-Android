package com.videoplayer.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.videoplayer.R;
import com.videoplayer.adapter.HomeAdapter;
import com.videoplayer.base.AppConstants;
import com.videoplayer.helper.AppUtil;
import com.videoplayer.helper.BLog;
import com.videoplayer.helper.ViewUtil;
import com.videoplayer.helper.retrofit.UenApiClient;
import com.videoplayer.helper.retrofit.UenApiInterface;
import com.videoplayer.model.HomeModel;
import com.videoplayer.model.SectionModel;
import com.videoplayer.model.response.UpcomingMovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<HomeModel> items = new ArrayList<>();
    private int page = 1;
    private LinearLayout retry_ll;
    private Button retryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar_pro);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initViews();

        getUpcomingMoviesApi();
    }

    private void initViews(){
        retry_ll = (LinearLayout)findViewById(R.id.retry_ll);
        retryBtn = (Button)findViewById(R.id.retry_btn);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                getUpcomingMoviesApi();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void getUpcomingMoviesApi(){
        if(!AppUtil.check_internet(this, true, false)){
            return;
        }
        retry_ll.setVisibility(View.GONE);
        ViewUtil.showLoadingProgressDialog(this);
        UenApiInterface apiService = UenApiClient.getClient().create(UenApiInterface.class);
        Call<UpcomingMovieResponse> call = apiService.getUpcomingMovies(AppConstants.API_KEY, AppConstants.LANG, String.valueOf(page));
        call.enqueue(new Callback<UpcomingMovieResponse>() {
            @Override
            public void onResponse(Call<UpcomingMovieResponse> call, Response<UpcomingMovieResponse> response) {
                BLog.e(LOG_TAG, "body - "+response.body());
                BLog.e(LOG_TAG, "url - "+call.request().url());
                if(response.body() != null){
                    retry_ll.setVisibility(View.GONE);
                    items.add(new HomeModel(1, new SectionModel("Upcoming"), null));
                    items.add(new HomeModel(2, null, response.body().getMovieResultResponses()));
                    HomeAdapter homeAdapter = new HomeAdapter(items, MainActivity.this, getSupportFragmentManager());
                    recyclerView.setAdapter(homeAdapter);
                }else{
                    retry_ll.setVisibility(View.VISIBLE);
                }
                ViewUtil.hideLoadingProgressDialog();
            }

            @Override
            public void onFailure(Call<UpcomingMovieResponse> call, Throwable t) {
                BLog.e(LOG_TAG, "failure - "+call.request().url());
                ViewUtil.hideLoadingProgressDialog();
                retry_ll.setVisibility(View.VISIBLE);
            }
        });
    }

}
