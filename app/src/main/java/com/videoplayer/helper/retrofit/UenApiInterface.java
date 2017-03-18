package com.videoplayer.helper.retrofit;

import com.google.gson.JsonElement;
import com.videoplayer.model.response.UpcomingMovieResponse;
import com.videoplayer.model.response.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UenApiInterface {

    @GET("upcoming/")
    Call<UpcomingMovieResponse> getUpcomingMovies(@Query("api_key") String api_key, @Query("language") String language, @Query("page") String page);

    @GET("{id}/videos")
    Call<VideoResponse> getVideos(@Path("id") String id,
                                  @Query("api_key") String api_key,
                                  @Query("language") String language);

    @GET("get_video_info")
    Call<JsonElement> getVideoInfo(@Query("video_id") String video_id);

}
