package com.videoplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.videoplayer.R;
import com.videoplayer.activity.DetailActivity;
import com.videoplayer.helper.BLog;
import com.videoplayer.helper.retrofit.UenApiClient;
import com.videoplayer.model.response.MovieResultResponse;
import com.videoplayer.viewholder.GridItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ketan on 3/17/17.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = HomeAdapter.class.getSimpleName();
    private List<MovieResultResponse> items = new ArrayList<>();
    private Context context;
    private int pagerPosition = 0;

    public GridAdapter(List<MovieResultResponse> items, Context context, int pagerPosition) {
        this.items = items;
        this.context = context;
        this.pagerPosition = pagerPosition;
    }

    @Override
    public int getItemCount() {
        BLog.e(LOG_TAG, "grid size - "+items.size());
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v2 = inflater.inflate(R.layout.grid_item_layout, viewGroup, false);
        viewHolder = new GridItemViewHolder(v2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GridItemViewHolder vh2 = (GridItemViewHolder) viewHolder;
        configurePagerViewHolder(vh2, position);
    }

    private void configurePagerViewHolder(GridItemViewHolder vh2, final int position) {
        BLog.e(LOG_TAG, "configurePagerViewHolder - "+position);
        vh2.getLabel1().setText(items.get(position).getTitle());
        String img = items.get(position).getPoster_path();
        if(img != null && !img.isEmpty()){
            Picasso.with(context).load(UenApiClient.BASE_IMAGE_URL + img).into(vh2.getImg());
        }
        vh2.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("movie_list", (ArrayList<? extends Parcelable>) items);
                context.startActivity(intent);
            }
        });
    }

}