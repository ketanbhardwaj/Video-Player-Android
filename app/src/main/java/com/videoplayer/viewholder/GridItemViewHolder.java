package com.videoplayer.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.videoplayer.R;

/**
 * Created by Ketan on 3/17/17.
 */

public class GridItemViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTxt;
    private ImageView img;
    private CardView cardView;

    public GridItemViewHolder(View v) {
        super(v);
        titleTxt = (TextView) v.findViewById(R.id.mov_name);
        img = (ImageView) v.findViewById(R.id.img);
        cardView = (CardView)v.findViewById(R.id.cardview);
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public TextView getLabel1() {
        return titleTxt;
    }

    public void setLabel1(TextView label1) {
        this.titleTxt = label1;
    }

}
