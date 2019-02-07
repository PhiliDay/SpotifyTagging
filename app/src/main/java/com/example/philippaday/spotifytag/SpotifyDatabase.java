package com.example.philippaday.spotifytag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SpotifyDatabase {

    private SpotifyDatabase() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_SONG_ID = "songId";
        public static final String COLUMN_NAME_SONG_NAME = "songName";
        public static final String COLUMN_NAME_SONG_TAG = "songTag";
    }


}
