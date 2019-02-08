package com.example.philippaday.spotifytag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SpotifyDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SpotifyDatabase.db";

    public SpotifyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SongDatabase.CREATE_TABLE);
        Log.d("ONCREATE", "CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SongDatabase.DELETE_TABLE);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SongDatabase displayAllSongDetails(String songId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + SongDatabase.TABLE_NAME + " WHERE songId = '" + songId +"' ");

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

    public String displaySongTag(String songId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT songTag FROM " + SongDatabase.TABLE_NAME + " WHERE songId = '" + songId +"' ");

        Log.i("query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        //Debugging purposes
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
        //This only displays first tag, need to update this when gets more complex
        String tag = c.getString(0);
        Log.i("displaySongTag", "tagging" + tag);

        return tag;
    }

    public long createSongInDatabase(SongDatabase song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long emptyRow=0;

        contentValues.put(SongDatabase.Table_Column_ID, song.getSongId());
        contentValues.put(SongDatabase.Table_Column_1_Name, song.getSongName());
        contentValues.put(SongDatabase.Table_Column_2_Tag, song.getSongTag());
        contentValues.put(SongDatabase.Table_Column_3_Album, song.getSongAlbum().toString());
        contentValues.put(SongDatabase.Table_Column_4_ImageUri, song.getSongUri().toString());
        contentValues.put(SongDatabase.Table_Column_5_songArtist, song.getSongArtist().toString());
         if(isUnique(song.getSongId())) {
             long songRow = db.insert(SongDatabase.TABLE_NAME, null, contentValues);
             return songRow;
         } else {
            long emptyRow = db.query()
         }
       // return emptyRow;
    }

    public boolean isUnique(String songId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = ("SELECT * FROM " + SongDatabase.TABLE_NAME + " WHERE songId = '" + songId +"' ");
        Cursor cursor = db.rawQuery(selectQuery, new String[] { songId });

        if (cursor != null && cursor.getCount()>0) {
            return true;
            }
        return false;
    }

    public long addTagToSong(SongDatabase song, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String insertQuery = ("songId = ?");
        contentValues.put(SongDatabase.Table_Column_2_Tag, tag);
        song.setSongTag(tag);

        long songRow = db.update(SongDatabase.TABLE_NAME, contentValues, insertQuery, new String[] { tag });

        return songRow;
    }
}