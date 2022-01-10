package com.hyt.moseaclass.ui.course;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.hyt.moseaclass.R;
import com.hyt.moseaclass.databinding.ActivityVideoPlayerBinding;

import java.util.Objects;

public class VideoPlayerActivity extends AppCompatActivity {


    private ExoPlayer exoPlayer;

    private ActivityVideoPlayerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(LayoutInflater.from(this), null, false);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.videoToolbar);
        // 设置状态栏覆盖到应用之上，不占固定位置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        显示返回按钮
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //        将应用栏与背景色融为一体，去除高程
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(binding.videoAppbar, "elevation", 0.1f));
        binding.videoAppbar.setStateListAnimator(stateListAnimator);
        init();
    }

    private void init() {
        setCustomNavigationIcon(R.color.black);
        initPlayer();
    }

    //    初始化播放器
    private void initPlayer() {
        PlayerView playerView = binding.playView;
        ExoPlayer.Builder builder = new ExoPlayer.Builder(this);
//        均为默认设置
        builder.setRenderersFactory(new DefaultRenderersFactory(this));
        builder.setTrackSelector(new DefaultTrackSelector(this));
        builder.setLoadControl(new DefaultLoadControl());
        exoPlayer = builder.build();
        playerView.setPlayer(exoPlayer);
//        设置视频媒体资源
        exoPlayer.setMediaSource(buildMediaSource());
        exoPlayer.prepare();
    }

    //    构建视频媒体资源
    private MediaSource buildMediaSource() {
        Uri uri = Uri.parse("http://r46uv4d69.hn-bkt.clouddn.com/2004727%E2%80%94%E8%81%94%E7%B3%BB%E6%88%91%E4%BB%AC%E9%98%9F%E2%80%94%E3%80%90A14%E3%80%91%E7%A7%BB%E5%8A%A8%E4%BA%92%E8%81%94%E6%97%B6%E4%BB%A3%E7%9A%84%E8%AE%BE%E5%A4%87%E7%AE%A1%E7%90%86%E3%80%90%E8%99%B9%E8%BD%AF%E3%80%91%E2%80%94%E9%A1%B9%E7%9B%AE%E6%BC%94%E7%A4%BA%E8%A7%86%E9%A2%91.mp4");
        DefaultDataSource.Factory factory = new DefaultDataSource.Factory(this);
        return new DefaultMediaSourceFactory(factory).createMediaSource(MediaItem.fromUri(uri));
    }

    /*
     * 根据状态栏的展开折叠修改返回按钮的颜色
     * */
    private void setCustomNavigationIcon(int color) {
        Drawable upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }

    /*
     * 当活动不可见时，暂停播放
     * */
    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }


    /*
     * 当活动恢复时，视频继续播放
     * */
    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.play();
    }


    /*
     * 当活动销毁时，视频停止播放
     * */
    @Override
    protected void onDestroy() {
        exoPlayer.stop();
        if (exoPlayer != null) {
            exoPlayer = null;
        }
        super.onDestroy();
    }
}
