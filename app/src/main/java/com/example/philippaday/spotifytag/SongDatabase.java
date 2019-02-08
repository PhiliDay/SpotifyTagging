package com.example.philippaday.spotifytag;


import android.database.sqlite.SQLiteDatabase;

import com.spotify.protocol.types.ImageUri;

/* Class to create the user table - contains getters and setters */

public class SongDatabase {

    public static final String TABLE_NAME = "SpotifyTable";

    public static final String Table_Column_ID="songId";
    public static final String Table_Column_1_Name="songName";
    public static final String Table_Column_2_Tag="songTag";
    public static final String Table_Column_3_Album="songAlbum";
    public static final String Table_Column_4_Playlist="songPlaylist";
    public static final String Table_Column_5_ImageUri="imageUri";
    public static final String Table_Column_6_songArtist="songArtist";


    String songId;
    String songName;
    String songTag;
    String songAlbum;
    String songPlaylist;
    ImageUri imageUri;
    String songArtist;


    // Create table SQL query
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
            + Table_Column_ID+" VARCHAR, "
            +Table_Column_1_Name+" VARCHAR, "
            +Table_Column_2_Tag+" VARCHAR, "
            +Table_Column_3_Album+" VARCHAR, "
            +Table_Column_4_Playlist+" VARCHAR, "
            +Table_Column_5_ImageUri+" VARCHAR, "
            +Table_Column_6_songArtist+" VARCHAR)";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SongDatabase() {

    }

    public SongDatabase(String songId, String songName, String songTag, String songAlbum, String songPlaylist, ImageUri imageUri, String songArtist) {
        // this.userId = userId;
        this.songId = songId;
        this.songName = songName;
        this.songTag = songTag;
        this.songAlbum = songAlbum;
        this.songPlaylist = songPlaylist;
        this.imageUri = imageUri;
        this.songArtist = songArtist;
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

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongPlaylist(String songPlaylist) {
        this.songPlaylist = songPlaylist;
    }

    public String getSongPlaylist() {
        return songPlaylist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongUri(ImageUri imageUri) {
        this.imageUri = imageUri;
    }

    public ImageUri getSongUri() {
        return imageUri;
    }

}
