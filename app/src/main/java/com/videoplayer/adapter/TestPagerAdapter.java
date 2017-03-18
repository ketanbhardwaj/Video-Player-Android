package com.videoplayer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videoplayer.R;
import com.videoplayer.helper.AppUtil;
import com.videoplayer.helper.BLog;
import com.videoplayer.model.MovieModel;
import com.videoplayer.model.response.MovieResultResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ketan on 3/17/17.
 */

public class TestPagerAdapter extends PagerAdapter {

    private static final String LOG_TAG = ViewPagerAdapter.class.getSimpleName();
    private List<MovieResultResponse> movieModelArrayList = new ArrayList<>();
    private Context context;
    private int totalPages = 0;
    private static final int MAX_ITEMS_PER_PAGE = 4;

    public TestPagerAdapter(Context context, List<MovieResultResponse> movieModelArrayList){
        this.context = context;
        this.movieModelArrayList = movieModelArrayList;
        if(movieModelArrayList != null){
            totalPages = AppUtil.getTotalFragments(movieModelArrayList.size());
        }
    }

    @Override
    public int getCount() {
        BLog.e(LOG_TAG, "totalPages - "+totalPages);
        return totalPages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.grid_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView)itemView.findViewById(R.id.grid_recycler);
        GridAdapter gridAdapter = new GridAdapter(getSegregatedDataList(position), context, position);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gridAdapter);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    private List<MovieResultResponse> getSegregatedDataList(int position){
        List<MovieResultResponse> tempList = new ArrayList<>();
        int z = position + 1;
        int UPPER_LIMIT = z * MAX_ITEMS_PER_PAGE;
        int LOWER_LIMIT = UPPER_LIMIT - MAX_ITEMS_PER_PAGE;
        BLog.e(LOG_TAG, "LOWER_LIMIT - "+LOWER_LIMIT);
        BLog.e(LOG_TAG, "UPPER_LIMIT - "+UPPER_LIMIT);
        if(UPPER_LIMIT > movieModelArrayList.size()){
            UPPER_LIMIT = movieModelArrayList.size();
        }
        for (int i = LOWER_LIMIT; i < UPPER_LIMIT && UPPER_LIMIT <= movieModelArrayList.size(); i++) {
            tempList.add(movieModelArrayList.get(i));
        }
        return tempList;
    }

}
