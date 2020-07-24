package com.example.appvideo.model;

import java.io.Serializable;

public class VideoInfo implements Serializable {
    private String id,id_category,title,avatar,thumb,file_mp4,file_mp4_size,actor,director,description,date_created,youtube_url;

    public VideoInfo() {
    }

    public VideoInfo(String id,String id_category, String title, String avatar, String thumb, String file_mp4, String file_mp4_size, String actor, String director, String description, String date_created, String youtube_url) {
        this.id = id;
        this.id_category=id_category;
        this.title = title;
        this.avatar = avatar;
        this.thumb = thumb;
        this.file_mp4 = file_mp4;
        this.file_mp4_size = file_mp4_size;
        this.actor = actor;
        this.director = director;
        this.description = description;
        this.date_created = date_created;
        this.youtube_url = youtube_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFile_mp4() {
        return file_mp4;
    }

    public void setFile_mp4(String file_mp4) {
        this.file_mp4 = file_mp4;
    }

    public String getFile_mp4_size() {
        return file_mp4_size;
    }

    public void setFile_mp4_size(String file_mp4_size) {
        this.file_mp4_size = file_mp4_size;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }
}
