package com.example.appvideo.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appvideo.model.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    Context context;
    private static String TABLE_DATABASE_NAME = "Video.dp";
    private static String TABLE_NAME = "video";//ten bang
    private static int VERSION_TABLE = 3;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    //id,id_category,title,avatar,thumb,file_mp4,
    //file_mp4_size,actor,director,description,date_created,youtube_url
    private String KEY_ID = "id";
    private String KEY_ID_CATEGORY = "id_category";
    private String KEY_TITLE = "title";
    private String KEY_AVATAR = "avatar";
    private String KEY_THUMB = "thumb";
    private String KEY_FILE_MP4 = "file_mp4";
    private String KEY_FILE_MP4_SIZE = "file_mp4_size";
    private String KEY_ACTOR = "actor";
    private String KEY_DIRETOR = "director";
    private String KEY_DESCRIPTION = "description";
    private String KEY_DATE_CREATED = "date_created";
    private String KEY_YOUTUBE_URL = "youtube_url";

    public SQLHelper(@Nullable Context context) {
        super(context, TABLE_DATABASE_NAME, null, VERSION_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateTable = "CREATE TABLE video (" +
                "id Text NOT NULL PRIMARY KEY," +
                "id_category Text , " +
                "title Text ," +
                "avatar Text ," +
                "thumb Text ," +
                "file_mp4 Text ," +
                "file_mp4_size Text ," +
                "actor Text ," +
                "director Text ," +
                "description Text ," +
                "date_created Text ," +
                "youtube_url Text )";
        sqLiteDatabase.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i!=i1){
            sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public void onInsertToTB(VideoInfo v){
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();

        contentValues.put(KEY_ID,v.getId());
        contentValues.put(KEY_ID_CATEGORY,v.getId_category());
        contentValues.put(KEY_TITLE,v.getTitle());
        contentValues.put(KEY_AVATAR,v.getAvatar());
        contentValues.put(KEY_THUMB,v.getThumb());
        contentValues.put(KEY_FILE_MP4,v.getFile_mp4());
        contentValues.put(KEY_FILE_MP4_SIZE,v.getFile_mp4_size());
        contentValues.put(KEY_ACTOR,v.getActor());
        contentValues.put(KEY_DIRETOR,v.getDirector());
        contentValues.put(KEY_DESCRIPTION,v.getDescription());
        contentValues.put(KEY_DATE_CREATED,v.getDate_created());
        contentValues.put(KEY_YOUTUBE_URL,v.getYoutube_url());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }
    public int delete(String id){
        sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{id});
    }

    public List<VideoInfo> getAllVideo(){
        List<VideoInfo> videoInfoList=new ArrayList<>();
        String query="SELECT * FROM "+TABLE_NAME;

        sqLiteDatabase=this.getReadableDatabase();
        cursor=sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false){
            VideoInfo videoInfo=new VideoInfo(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11)
            );
            videoInfoList.add(videoInfo);
            cursor.moveToNext();
        }
        return videoInfoList;
    }
}
