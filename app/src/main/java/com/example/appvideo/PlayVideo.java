package com.example.appvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appvideo.model.VideoInfo;

public class PlayVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Intent intent=getIntent();
        VideoInfo videoInfo= (VideoInfo) intent.getSerializableExtra("video");
        String tb=videoInfo.getTitle();
        Toast.makeText(PlayVideo.this,tb,Toast.LENGTH_SHORT).show();
    }
}