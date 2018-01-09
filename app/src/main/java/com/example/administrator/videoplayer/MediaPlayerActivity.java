package com.example.administrator.videoplayer;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MediaPlayerActivity extends AppCompatActivity {

    private SurfaceView surfaceview;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        surfaceview = (SurfaceView) findViewById(R.id.surfaceView);
        mediaPlayer = new MediaPlayer();
        surfaceview.getHolder().setKeepScreenOn(true);
        surfaceview.getHolder().addCallback(new SurfaceViewLis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                video_play(0);
            }
        }, 50);
    }

    private class SurfaceViewLis implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            holder.getSurface().describeContents();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                ReleasePlayer();
            }

        }

    }

    /**
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void video_play(final int msec) {
        // 获取视频文件地址
        try {
            mediaPlayer = new MediaPlayer();
            //设置音频流类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            AssetFileDescriptor fd = this.getAssets().openFd("splash_video.mp4");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                    fd.getLength());
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(surfaceview.getHolder());//这一步是关键，制定用于显示视频的SurfaceView对象（通过setDisplay（））

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    ReleasePlayer();
                    finish();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放
                    video_play(0);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surfaceview.destroyDrawingCache();
        ReleasePlayer();
    }

    /**
     * 释放播放器资源
     */
    private void ReleasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        System.gc();
    }
}
