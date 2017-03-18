package com.videoplayer.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.videoplayer.R;

/**
 * Created by Ketan on 3/17/17.
 */

public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTxt;

    public TextViewHolder(View v) {
        super(v);
        titleTxt = (TextView) v.findViewById(R.id.title_txt);
    }

    public TextView getLabel1() {
        return titleTxt;
    }

    public void setLabel1(TextView label1) {
        this.titleTxt = label1;
    }

}
