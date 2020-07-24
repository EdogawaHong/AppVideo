package com.example.appvideo.network;

import com.example.appvideo.model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIManager {
    @GET("video.ver2")
    Call<Video> getAPIVideo();
}
