package com.videoplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.videoplayer.R;
import com.videoplayer.activity.VideoPlayerActivity;
import com.videoplayer.base.AppConstants;
import com.videoplayer.helper.AppUtil;
import com.videoplayer.helper.BLog;
import com.videoplayer.helper.ViewUtil;
import com.videoplayer.helper.retrofit.UenApiClient;
import com.videoplayer.helper.retrofit.UenApiInterface;
import com.videoplayer.model.response.MovieResultResponse;
import com.videoplayer.model.response.VideoResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ketan on 3/18/17.
 */

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private View rootView;
    private ImageView dtImg, playImg;
    private TextView dtTitleTxt, dtDescTxt, dtTimeTxt;
    private Button buyBtn;
    private MovieResultResponse movieResultResponse = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detail_fragment_layout, container, false);
        initViews();
        Bundle bundle = getArguments();
        if(bundle != null){
            movieResultResponse = bundle.getParcelable("movie_model");
        }
        setData();
        return rootView;
    }

    private void initViews(){
        dtTitleTxt = (TextView)rootView.findViewById(R.id.dt_title);
        dtDescTxt = (TextView)rootView.findViewById(R.id.dt_desc);
        dtTimeTxt = (TextView)rootView.findViewById(R.id.dt_time);
        dtImg = (ImageView)rootView.findViewById(R.id.dt_img);
        playImg = (ImageView)rootView.findViewById(R.id.play_img);
        buyBtn = (Button)rootView.findViewById(R.id.dt_buy);
    }

    private void setData(){
        if(movieResultResponse != null){
            dtTitleTxt.setText(movieResultResponse.getTitle());
            dtDescTxt.setText(movieResultResponse.getOverview());
            dtTimeTxt.setText("Release date: "+movieResultResponse.getRelease_date());
            String img = movieResultResponse.getPoster_path();
            if(img != null && !img.isEmpty()){
                Picasso.with(getActivity()).load(UenApiClient.BASE_IMAGE_URL + img).into(dtImg);
            }
            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "It's a showcased project. Try BookMyShow to purchase the tickets ;)", Toast.LENGTH_LONG).show();
                }
            });
            playImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                    intent.putExtra("movie_model", movieResultResponse);
                    startActivity(intent);
                }
            });
        }
    }

    private void getVideoUrlApi(){
        if(!AppUtil.check_internet(getActivity(), true, false)){
            return;
        }
        ViewUtil.showLoadingProgressDialog(getActivity());
        UenApiInterface apiService = UenApiClient.getClient().create(UenApiInterface.class);
        Call<VideoResponse> call = apiService.getVideos(movieResultResponse.getId(), AppConstants.API_KEY, AppConstants.LANG);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                BLog.e(LOG_TAG, "body - "+response.body());
                BLog.e(LOG_TAG, "url - "+call.request().url());
                if(response.body() != null
                        && response.body().getMovieResultResponses() != null
                        && response.body().getMovieResultResponses().size() > 0){

                    Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                    intent.putExtra("movie_model", movieResultResponse);
//                    intent.putExtra("url_key", retriever.getInfo(YouTubeVideoInfoRetriever.KEY_DASH_VIDEO));
                    startActivity(intent);
                }else{
                }
                ViewUtil.hideLoadingProgressDialog();
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                BLog.e(LOG_TAG, "failure - "+call.request().url());
                ViewUtil.hideLoadingProgressDialog();
            }
        });
    }


}
