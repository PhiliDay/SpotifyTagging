package com.example.philippaday.spotifytag;


import android.database.sqlite.SQLiteDatabase;

/* Class to create the user table - contains getters and setters */

public class SongDatabase {

    public static final String TABLE_NAME = "SpotifyTable";

    public static final String Table_Column_ID="songId";
    public static final String Table_Column_1_Name="songName";
    public static final String Table_Column_2_Tag="songTag";

    String songId;
    String songName;
    String songTag;


    // Create table SQL query
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
            //  +Table_Column_ID+" VARCHAR, "
            + Table_Column_ID+" VARCHAR, NOT NULL, PRIMARY KEY,"
            +Table_Column_1_Name+" VARCHAR, "
            +Table_Column_2_Tag+"VARCHAR)";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SongDatabase() {

    }

    public SongDatabase(String songId, String songName, String songTag) {
        // this.userId = userId;
        this.songId = songId;
        this.songName = songName;
        this.songTag = songTag;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId){
        this.songId = songId;
    }

    public String getSongName(){ return songName;}

    public void setSongName(String songName){this.songName = songName;}


    public void setSongTag(String songTag) {
        this.songTag = songTag;
    }

    public String getSongTag() {
        return songTag;
    }
}
