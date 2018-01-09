package com.example.administrator.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_use_system_player)
    Button btnUseSystemPlayer;
    @BindView(R.id.btn_use_video_view)
    Button btnUseVideoView;
    @BindView(R.id.btn_use_MediaPlayer)
    Button btnUseMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_use_system_player, R.id.btn_use_video_view, R.id.btn_use_MediaPlayer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_use_system_player:
//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/splash_video");
//                //调用系统自带的播放器
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(uri, "video/*");
//                startActivity(intent);
                break;
            case R.id.btn_use_video_view:

                break;
            case R.id.btn_use_MediaPlayer:
                startActivity(new Intent(this, MediaPlayerActivity.class));
                break;
        }
    }
}
