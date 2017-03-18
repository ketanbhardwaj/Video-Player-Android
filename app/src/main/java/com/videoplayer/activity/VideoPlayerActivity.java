package com.videoplayer.activity;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.util.Util;
import com.videoplayer.R;
import com.videoplayer.helper.BLog;
import com.videoplayer.model.response.MovieResultResponse;

import java.io.IOException;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final String LOG_TAG = VideoPlayerActivity.class.getSimpleName();
    private SimpleExoPlayerView simpleExoPlayerView;
    private MovieResultResponse movieResultResponse = null;
    private SimpleExoPlayer player;


    private ExoPlayer.EventListener exoPlayerEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            movieResultResponse = bundle.getParcelable("movie_model");
        }
        initViews();
    }

    private void initViews(){
        simpleExoPlayerView = (SimpleExoPlayerView)findViewById(R.id.player_view);
    }

    private void initVideoPlayer(){

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

        //Set media controller
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();

        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        Uri mp4VideoUri =Uri.parse("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
        Uri dashUri = Uri.parse("http://yt-dash-mse-test.commondatastorage.googleapis.com/media/feelings_vp9-20130806-manifest.mpd");

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();

        //Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeterA);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, bandwidthMeterA, buildHttpDataSourceFactory(bandwidthMeterA));

        //Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //FOR LIVESTREAM LINK:
//        MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
        MediaSource mediaSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        MediaSource mediaDash = new DashMediaSource(dashUri, buildDataSourceFactory(null),
                new DefaultDashChunkSource.Factory(dataSourceFactory),
                mainHandler, null);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(mediaDash);

        // Prepare the player with the source.
        player.prepare(loopingSource);

        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                BLog.e(LOG_TAG,"Listener-onLoadingChanged...");

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                BLog.e(LOG_TAG,"Listener-onPlayerStateChanged...    =    "+playbackState);
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                BLog.e(LOG_TAG,"Listener-onTimelineChanged...");

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                BLog.e(LOG_TAG,"Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                BLog.e(LOG_TAG,"Listener-onPositionDiscontinuity...");

            }
        });
        player.setPlayWhenReady(true);

    }

    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter){
        return new DefaultDataSourceFactory(this, bandwidthMeter, buildHttpDataSourceFactory(bandwidthMeter));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter){
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "videoplayer"), bandwidthMeter);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVideoPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BLog.e(LOG_TAG,"onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BLog.e(LOG_TAG,"onDestroy()...");
        releasePlayer();
    }


    @Override
    protected void onStart() {
        super.onStart();
        BLog.e(LOG_TAG,"onStart()...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        BLog.e(LOG_TAG,"onStop()...");
        releasePlayer();
    }
}
