package com.videoplayer.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videoplayer.R;
import com.videoplayer.helper.BLog;
import com.videoplayer.model.HomeModel;
import com.videoplayer.model.SectionModel;
import com.videoplayer.model.response.MovieResultResponse;
import com.videoplayer.viewholder.PagerViewHolder;
import com.videoplayer.viewholder.TextViewHolder;

import java.util.List;

/**
 * Created by Ketan on 3/17/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = HomeAdapter.class.getSimpleName();
    private List<HomeModel> items;
    private final int SECTION = 0, PAGER = 1;
    private Context context;
    private FragmentManager fragmentManager;

    public HomeAdapter(List<HomeModel> items, Context context, FragmentManager fragmentManager) {
        this.items = items;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getType() == 1) {
            return SECTION;
        } else if (items.get(position).getType() == 2) {
            return PAGER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case SECTION:
                View v1 = inflater.inflate(R.layout.text_holder_layout, viewGroup, false);
                viewHolder = new TextViewHolder(v1);
                break;
            case PAGER:
                View v2 = inflater.inflate(R.layout.pager_holder_layout, viewGroup, false);
                viewHolder = new PagerViewHolder(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.text_holder_layout, viewGroup, false);
                viewHolder = new TextViewHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case SECTION:
                TextViewHolder vh1 = (TextViewHolder) viewHolder;
                configureTextViewHolder(vh1, position);
                break;
            case PAGER:
                PagerViewHolder vh2 = (PagerViewHolder) viewHolder;
                configurePagerViewHolder(vh2, position);
                break;
            default:
                TextViewHolder vh = (TextViewHolder) viewHolder;
                configureTextViewHolder(vh, position);
                break;
        }
    }

    private void configureTextViewHolder(TextViewHolder vh1, int position) {
        SectionModel sectionModel = items.get(position).getSectionModel();
        if (sectionModel != null) {
            vh1.getLabel1().setText(sectionModel.getName());
        }
    }

    private void configurePagerViewHolder(PagerViewHolder vh2, int position) {
        BLog.e(LOG_TAG, "configurePagerViewHolder - "+position);
        List<MovieResultResponse> movieModelList = items.get(position).getMovieModelList();
        BLog.e(LOG_TAG, "movieModelList size - "+items.get(position).getMovieModelList().size());
        TestPagerAdapter testPagerAdapter = new TestPagerAdapter(context, movieModelList);
        vh2.getViewPager().setAdapter(testPagerAdapter);
    }

}