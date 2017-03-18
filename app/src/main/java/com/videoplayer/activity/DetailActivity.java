package com.videoplayer.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.videoplayer.R;
import com.videoplayer.adapter.DetailViewPagerAdapter;
import com.videoplayer.fragment.DetailFragment;
import com.videoplayer.model.response.MovieResultResponse;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<MovieResultResponse> movieResultResponseList = new ArrayList<>();
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            position = bundle.getInt("position");
            movieResultResponseList = bundle.getParcelableArrayList("movie_list");
            initViews();
        }else{
            Toast.makeText(this, "Something went wrong. Try Again!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews(){
        viewPager = (ViewPager)findViewById(R.id.detail_viewpager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setPageMargin(-60);
        DetailViewPagerAdapter detailViewPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager());
        addFragmentsToAdapter(detailViewPagerAdapter);
        viewPager.setAdapter(detailViewPagerAdapter);
        viewPager.setCurrentItem(position);
    }

    private void addFragmentsToAdapter(DetailViewPagerAdapter detailViewPagerAdapter){
        if(detailViewPagerAdapter != null){
            if(movieResultResponseList.size() > 0){
                for (int i = 0; i < movieResultResponseList.size(); i++) {
                    DetailFragment menuListFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movie_model", movieResultResponseList.get(i));
                    menuListFragment.setArguments(bundle);
                    detailViewPagerAdapter.addFragment(menuListFragment, movieResultResponseList.get(i).getTitle());
                }
            }
        }
    }


}
