package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;


public class MainActivity1 extends AppCompatActivity {
    TextView nm,desc,us;
SimpleExoPlayerView videoView;
SimpleExoPlayer exoPlayer;
String videourl="http://192.168.43.37/YouTubePlayer/Videos/video4.3gp";
String vn,vdes,vuse,downloadUrl;
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        videoView=findViewById(R.id.videoview);
        nm=findViewById(R.id.videonamesingle);
        desc=findViewById(R.id.videodescrisingle);
        us=findViewById(R.id.videousersingle);
        videourl=getIntent().getStringExtra("videourl");
        vn=getIntent().getStringExtra("videoname");
        vdes=getIntent().getStringExtra("videodesc");
        vuse=getIntent().getStringExtra("videouser");
        us.setText(" - "+vuse);
        desc.setText(vdes);
        nm.setText(vn);

        try{
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            new YouTubeExtractor(this) {
                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    if (ytFiles != null) {
                        int itag = 17;
                        downloadUrl = ytFiles.get(itag).getUrl();
                        Toast.makeText(MainActivity1.this, "the url is:"+downloadUrl, Toast.LENGTH_SHORT).show();
                    }
                }
            }.extract(videourl, true, true);
            Uri videouri=Uri.parse(downloadUrl);
            DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(videouri,dataSourceFactory,extractorsFactory,null,null);
            videoView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
//        SimpleMediaSource mediaSource=new SimpleMediaSource("http://192.168.43.38/YouTubePlayer/Videos/Agrimate.mp4");
//        mediaSource.setDisplayName("VideoPlaying");
//        videoView.play(mediaSource,false);
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(Build.VERSION.SDK_INT>23){
//            videoView.resume();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(Build.VERSION.SDK_INT>23){
//            videoView.resume();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(Build.VERSION.SDK_INT>23){
//            videoView.pause();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(Build.VERSION.SDK_INT>23){
//            videoView.pause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        exoPlayer.pau;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            return videoView.onKeyDown(keyCode,event);
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}