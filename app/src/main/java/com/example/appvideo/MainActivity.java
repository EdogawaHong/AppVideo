package com.example.appvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appvideo.adapter.AdapterVideoHome;
import com.example.appvideo.databinding.ActivityMainBinding;
import com.example.appvideo.model.Video;
import com.example.appvideo.model.VideoInfo;
import com.example.appvideo.network.APIManager;
import com.example.appvideo.network.Define;
import com.example.appvideo.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<VideoInfo> videoInfos;
    AdapterVideoHome adapterVideoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        videoInfos=new ArrayList<>();

        getData();
        
    }
    public void getData(){
        APIManager service= RetrofitClient.getRetrofitClient(Define.base_url).create(APIManager.class);
        Call<Video> call=service.getAPIVideo();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video video=response.body();
                for (int j=0;j<video.getCategory().size();j++) {
                    for (int i = 0; i < video.getCategory().get(j).getList().size(); i++) {
                        String id = video.getCategory().get(j).getList().get(i).getId();
                        String id_category = video.getCategory().get(j).getList().get(i).getIdCategory();
                        String title = video.getCategory().get(j).getList().get(i).getTitle();
                        String avatar = video.getCategory().get(j).getList().get(i).getAvatar();
                        String thumb = video.getCategory().get(j).getList().get(i).getThumb();
                        String file_mp4 = video.getCategory().get(j).getList().get(i).getFileMp4();
                        String file_mp4_size = video.getCategory().get(j).getList().get(i).getFileMp4Size();
                        String actor = video.getCategory().get(j).getList().get(i).getActor();
                        String director = video.getCategory().get(j).getList().get(i).getDirector();
                        String description = video.getCategory().get(j).getList().get(i).getDescription();
                        String date_created = video.getCategory().get(j).getList().get(i).getDateCreated();
                        String youtube_url = video.getCategory().get(j).getList().get(i).getYoutubeUrl();
                        videoInfos.add(new VideoInfo(id,id_category, title, avatar, thumb, file_mp4, file_mp4_size, actor, director, description, date_created, youtube_url));
                    }
                }
                Log.e("tb",video.getCategory().get(0).getList().get(0).getTitle());
                Log.e("tb",videoInfos.get(0).getTitle());

                adapterVideoHome=new AdapterVideoHome(videoInfos,MainActivity.this);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL,false);
                binding.rvHome.setHasFixedSize(false);
                binding.rvHome.setLayoutManager(layoutManager);
                binding.rvHome.setAdapter(adapterVideoHome);
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("tb",t.getMessage());
            }
        });
    }

}