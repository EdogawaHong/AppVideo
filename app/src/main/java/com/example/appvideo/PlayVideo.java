package com.example.appvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appvideo.SQLite.SQLHelper;
import com.example.appvideo.adapter.AdapterPlayVideo;
import com.example.appvideo.databinding.ActivityPlayVideoBinding;
import com.example.appvideo.model.Video;
import com.example.appvideo.model.VideoInfo;
import com.example.appvideo.network.APIManager;
import com.example.appvideo.network.Define;
import com.example.appvideo.network.RetrofitClient;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideo extends AppCompatActivity {

    ActivityPlayVideoBinding binding;
    List<VideoInfo> videoInfos = new ArrayList<>();
    AdapterPlayVideo adapterPlayVideo;
    VideoInfo videoInfo;

    ImageView imgFullscreen;
    boolean flag = false;
    SimpleExoPlayer player;

    SQLHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);

        imgFullscreen = binding.videoView.findViewById(R.id.bt_fullscreen);
        imgFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));

        Intent intent = getIntent();
        videoInfo = (VideoInfo) intent.getSerializableExtra("video");

        showInfo(videoInfo);
        onClickFullScreen();

        sqlHelper=new SQLHelper(this);
        CheckHistory(videoInfo);
        sqlHelper.onInsertToTB(videoInfo);

        binding.imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.llDetail.isShown()) {
                    binding.llDetail.setVisibility(View.GONE);
                    binding.imgDetail.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                } else {
                    binding.llDetail.setVisibility(View.VISIBLE);
                    binding.imgDetail.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                }
            }
        });
        final int id_c = Integer.parseInt(videoInfo.getId_category()) - 1;
        getDataPlay(id_c, videoInfo);

        ListenerPlayer(id_c);
    }
    private void CheckHistory(VideoInfo v){
        List<VideoInfo> list=sqlHelper.getAllVideo();
        for(VideoInfo vd:list){
            if (v.getId().equals(vd.getId())){
                sqlHelper.delete(v.getId());
                break;
            }
        }
    }

    //play next video auto
    private void ListenerPlayer(final int position) {
        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    videoInfo = videoInfos.get(0);
                    CheckHistory(videoInfo);
                    sqlHelper.onInsertToTB(videoInfo);
                    showInfo(videoInfo);
                    getDataPlay(position, videoInfo);
                    ListenerPlayer(position);
                }
            }
        });
    }

    private void showInfo(VideoInfo v) {
        binding.llDetail.setVisibility(View.GONE);
        binding.tvTitle.setText(v.getTitle());
        binding.tvActor.setText(v.getActor());
        binding.tvDate.setText(v.getDate_created());
        binding.tvDirector.setText(v.getDirector());
        binding.tvDesc.setText("Nội dung: " + v.getDescription());

        Uri uri = Uri.parse(v.getFile_mp4());
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());
        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(this, "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );
        player.prepare(audioSource);
        binding.videoView.setPlayer(player);
        player.setPlayWhenReady(true);
    }

    private void onClickFullScreen() {
        imgFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                    imgFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    binding.llFullscreenExit.setVisibility(View.VISIBLE);
                    setSize(binding.videoView, LinearLayout.LayoutParams.MATCH_PARENT, 600);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                    binding.llFullscreenExit.setVisibility(View.GONE);
                    setSize(binding.videoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    imgFullscreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                }
            }
        });
    }

    //change size playerview
    private void setSize(View view, int width, int height) {
        view.setMinimumWidth(width);
        view.setMinimumHeight(height);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params = (params == null) ? new ViewGroup.LayoutParams(0, 0) : params;
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public void getDataPlay(final int id_c, final VideoInfo v) {
        APIManager service = RetrofitClient.getRetrofitClient(Define.base_url).create(APIManager.class);
        Call<Video> call = service.getAPIVideo();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video video = response.body();
                videoInfos.clear();
                for (int i = 0; i < video.getCategory().get(id_c).getList().size(); i++) {
                    String id = video.getCategory().get(id_c).getList().get(i).getId();
                    String id_category = video.getCategory().get(id_c).getList().get(i).getIdCategory();
                    String title = video.getCategory().get(id_c).getList().get(i).getTitle();
                    String avatar = video.getCategory().get(id_c).getList().get(i).getAvatar();
                    String thumb = video.getCategory().get(id_c).getList().get(i).getThumb();
                    String file_mp4 = video.getCategory().get(id_c).getList().get(i).getFileMp4();
                    String file_mp4_size = video.getCategory().get(id_c).getList().get(i).getFileMp4Size();
                    String actor = video.getCategory().get(id_c).getList().get(i).getActor();
                    String director = video.getCategory().get(id_c).getList().get(i).getDirector();
                    String description = video.getCategory().get(id_c).getList().get(i).getDescription();
                    String date_created = video.getCategory().get(id_c).getList().get(i).getDateCreated();
                    String youtube_url = video.getCategory().get(id_c).getList().get(i).getYoutubeUrl();
                    videoInfos.add(new VideoInfo(id, id_category, title, avatar, thumb, file_mp4, file_mp4_size, actor, director, description, date_created, youtube_url));
                }
                sortListVideo(videoInfos, v);

                adapterPlayVideo = new AdapterPlayVideo(videoInfos, PlayVideo.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
                binding.rvPlayVideo.setHasFixedSize(false);
                binding.rvPlayVideo.setLayoutManager(layoutManager);
                binding.rvPlayVideo.setAdapter(adapterPlayVideo);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("tb", t.getMessage());
            }
        });
    }

    public void sortListVideo(List<VideoInfo> videoInfos, VideoInfo v) {
        int d = 0;
        for (int i = 0; i < videoInfos.size(); i++) {
            if (videoInfos.get(i).getId().equals(v.getId())) {
                d = i;
                break;
            }
        }
        //Log.e("tb", d + "");
        List<VideoInfo> temps = new ArrayList<>();
        temps.addAll(videoInfos);
        videoInfos.removeAll(temps);
        for (int j = d + 1; j < temps.size(); j++)
            videoInfos.add(temps.get(j));
        for (int j = 0; j <= d; j++)
            videoInfos.add(temps.get(j));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
}