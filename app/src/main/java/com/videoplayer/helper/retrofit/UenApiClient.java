package com.videoplayer.helper.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UenApiClient {
    private static final String LOG_TAG = "UenApiClient";
    public static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    public static String BASE_YOUTUBE_URL = "https://www.youtube.com/";
    private static final String URL_YOUTUBE_GET_VIDEO_INFO = "http://www.youtube.com/get_video_info?&video_id=";
//    https://www.youtube.com/watch?v=
    private static Retrofit mRetrofit = null;
    private static Gson gson;


    public static Retrofit getClient(){
        if(gson == null){
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }

}
