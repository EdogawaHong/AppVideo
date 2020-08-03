package com.example.appvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.appvideo.SQLite.SQLHelper;
import com.example.appvideo.adapter.AdapterNav;
import com.example.appvideo.adapter.AdapterVideoHome;
import com.example.appvideo.databinding.ActivityMainBinding;
import com.example.appvideo.model.ItemMenuNav;
import com.example.appvideo.model.Video;
import com.example.appvideo.model.VideoInfo;
import com.example.appvideo.network.APIManager;
import com.example.appvideo.network.Define;
import com.example.appvideo.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<VideoInfo> videoInfos;
    AdapterVideoHome adapterVideoHome;

    List<ItemMenuNav> navList;
    AdapterNav adapterNav;
    SQLHelper sqlHelper=new SQLHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setNavigation();

        navList = new ArrayList<>();
        videoInfos = new ArrayList<>();

        setMenuNavigation();
        getData();
        binding.listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                binding.drawerLayout.closeDrawer(GravityCompat.START, false);
                getSupportActionBar().setTitle(navList.get(i).getName());
                if (i == 0) {
                    getData();
                }else if (i==navList.size()-1){
                    videoInfos.clear();
                    videoInfos=sqlHelper.getAllVideo();
                    Collections.reverse(videoInfos);
                    addRecycleView();
                }else {
                    getDataCategory(i);
                }
            }
        });
    }

    private void setNavigation() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        getSupportActionBar().setTitle("Trang chủ");
    }

    private void setMenuNavigation() {
        navList.add(new ItemMenuNav("Trang chủ", R.drawable.ic_baseline_home_24));
        navList.add(new ItemMenuNav("Thịnh hành", R.drawable.ic_baseline_whatshot_24));
        navList.add(new ItemMenuNav("Phim hay", R.drawable.ic_baseline_widgets_24));
        navList.add(new ItemMenuNav("Âm nhạc", R.drawable.ic_baseline_library_music_24));
        navList.add(new ItemMenuNav("Giải trí", R.drawable.ic_baseline_live_tv_24));
        navList.add(new ItemMenuNav("Lịch sử xem", R.drawable.ic_baseline_history_24));
        adapterNav = new AdapterNav(getBaseContext(), R.layout.item_menu_nav, navList);
        binding.listViewCategory.setAdapter(adapterNav);
    }

    public void getData() {
        videoInfos.removeAll(videoInfos);
        APIManager service = RetrofitClient.getRetrofitClient(Define.base_url).create(APIManager.class);
        Call<Video> call = service.getAPIVideo();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video video = response.body();
                for (int j = 0; j < video.getCategory().size(); j++) {
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
                        videoInfos.add(new VideoInfo(id, id_category, title, avatar, thumb, file_mp4, file_mp4_size, actor, director, description, date_created, youtube_url));
                    }
                    Collections.shuffle(videoInfos);
                }
                addRecycleView();
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("tb", t.getMessage());
            }
        });
    }

    public void getDataCategory(final int position) {
        videoInfos.removeAll(videoInfos);
        APIManager service = RetrofitClient.getRetrofitClient(Define.base_url).create(APIManager.class);
        Call<Video> call = service.getAPIVideo();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video video = response.body();
                for (int i = 0; i < video.getCategory().get(position - 1).getList().size(); i++) {
                    String id = video.getCategory().get(position - 1).getList().get(i).getId();
                    String id_category = video.getCategory().get(position - 1).getList().get(i).getIdCategory();
                    String title = video.getCategory().get(position - 1).getList().get(i).getTitle();
                    String avatar = video.getCategory().get(position - 1).getList().get(i).getAvatar();
                    String thumb = video.getCategory().get(position - 1).getList().get(i).getThumb();
                    String file_mp4 = video.getCategory().get(position - 1).getList().get(i).getFileMp4();
                    String file_mp4_size = video.getCategory().get(position - 1).getList().get(i).getFileMp4Size();
                    String actor = video.getCategory().get(position - 1).getList().get(i).getActor();
                    String director = video.getCategory().get(position - 1).getList().get(i).getDirector();
                    String description = video.getCategory().get(position - 1).getList().get(i).getDescription();
                    String date_created = video.getCategory().get(position - 1).getList().get(i).getDateCreated();
                    String youtube_url = video.getCategory().get(position - 1).getList().get(i).getYoutubeUrl();
                    videoInfos.add(new VideoInfo(id, id_category, title, avatar, thumb, file_mp4, file_mp4_size, actor, director, description, date_created, youtube_url));
                }
                adapterVideoHome.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("tb", t.getMessage());
            }
        });
    }

    public void addRecycleView() {
        adapterVideoHome = new AdapterVideoHome(videoInfos, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        binding.rvHome.setHasFixedSize(false);
        binding.rvHome.setLayoutManager(layoutManager);
        binding.rvHome.setAdapter(adapterVideoHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_right, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterVideoHome.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}