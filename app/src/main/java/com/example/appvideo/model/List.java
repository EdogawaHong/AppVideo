package com.example.appvideo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_category")
    @Expose
    private String idCategory;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("file_mp4")
    @Expose
    private String fileMp4;
    @SerializedName("file_mp4_size")
    @Expose
    private String fileMp4Size;
    @SerializedName("actor")
    @Expose
    private String actor;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("youtube_url")
    @Expose
    private String youtubeUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
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

    public String getFileMp4() {
        return fileMp4;
    }

    public void setFileMp4(String fileMp4) {
        this.fileMp4 = fileMp4;
    }

    public String getFileMp4Size() {
        return fileMp4Size;
    }

    public void setFileMp4Size(String fileMp4Size) {
        this.fileMp4Size = fileMp4Size;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

}