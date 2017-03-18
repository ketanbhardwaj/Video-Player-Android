package com.videoplayer.viewholder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.videoplayer.R;
import com.videoplayer.helper.AppUtil;
import com.videoplayer.views.WrappingViewPager;

/**
 * Created by Ketan on 3/17/17.
 */

public class PagerViewHolder extends RecyclerView.ViewHolder {

    private WrappingViewPager viewPager;

    public PagerViewHolder(View v) {
        super(v);
        viewPager = (WrappingViewPager) v.findViewById(R.id.viewpager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100, 0 ,100, 0);
        viewPager.setPageMargin(0);
    }

    public WrappingViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(WrappingViewPager viewPager) {
        this.viewPager = viewPager;
    }
}

