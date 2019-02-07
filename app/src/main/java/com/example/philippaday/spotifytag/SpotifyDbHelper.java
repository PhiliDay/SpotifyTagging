package com.example.philippaday.spotifytag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpotifyDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SpotifyDatabase.db";
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + SpotifyDatabase.FeedEntry.TABLE_NAME + " (" +
//                    SpotifyDatabase.FeedEntry._ID + " INTEGER PRIMARY KEY," +
//                    SpotifyDatabase.FeedEntry.COLUMN_NAME_SONG_ID + " TEXT," +
//                    SpotifyDatabase.FeedEntry.COLUMN_NAME_SONG_NAME + " TEXT," +
//                    SpotifyDatabase.FeedEntry.COLUMN_NAME_SONG_TAG + " TEXT)";
//
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + SpotifyDatabase.FeedEntry.TABLE_NAME;

    public SpotifyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SongDatabase.CREATE_TABLE);
        Log.d("ONCREATE", "CREATED");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SongDatabase.DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SongDatabase displaySong(String songId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + SpotifyDatabase.FeedEntry.TABLE_NAME + " WHERE songId = '" + songId +"' ");

        Log.i("query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);


        Log.v("MYDB", "Table1 TABLE_NAME has " +
                Integer.toString(c.getCount()) +
                " rows");

        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.v("MYDB", "Table1 TABLE_NAME has a column named " +
                    c.getColumnName(i)
            );
        }
        Log.d("Count",String.valueOf(c.getCount()));
        if(c.getCount() > 0){
                // get values from cursor here
        }

        if(c.getCount() == 0){
            Log.i("user", "user does not exist");
        }

        if (c != null)
            c.moveToFirst();
        Log.i("hello", "hello");
        SongDatabase newSong = new SongDatabase();
        newSong.setSongId(c.getString(c.getColumnIndex(SongDatabase.Table_Column_ID)));
        newSong.setSongName(c.getString(c.getColumnIndex(SongDatabase.Table_Column_1_Name)));
        newSong.setSongTag(c.getString(c.getColumnIndex(SongDatabase.Table_Column_2_Tag)));

        return newSong;
    }

    public long createSongInDatabase(SongDatabase song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SongDatabase.Table_Column_ID, song.getSongId());
        contentValues.put(SongDatabase.Table_Column_1_Name, song.getSongName());
        contentValues.put(SongDatabase.Table_Column_2_Tag, song.getSongTag());
        long songRow = db.insert(SongDatabase.TABLE_NAME, null, contentValues);

        if (songRow > 0) {
            Log.i("userRow", "datainserted");
        }
        return songRow;
    }

    public long addTagToSong(SongDatabase song, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String songId = song.getSongId();


        String insertQuery = ("INSERT " + tag + "INTO " + SongDatabase.TABLE_NAME + " WHERE songId = ?");
        contentValues.put(SongDatabase.Table_Column_2_Tag, tag);
        long songRow = db.update(SongDatabase.TABLE_NAME, null, insertQuery, new String[] { songId });

        if (songRow > 0) {
            Log.i("userRow", "datainserted");
        }
        return songRow;
    }





}