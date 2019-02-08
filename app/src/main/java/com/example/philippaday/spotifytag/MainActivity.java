package com.example.philippaday.spotifytag;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;



public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "d9abadc69fbb46239385d025042d8c02";
    private static final String REDIRECT_URI = "com.yourdomain.yourapp://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    SpotifyDbHelper mDbHelper = new SpotifyDbHelper(this);
    TextView songTitle, tagName;
    EditText editView;
    Button button;
    String tag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);
                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        SongDatabase song = new SongDatabase(track.uri, track.name, tag);
                        long insertingSong = mDbHelper.createSongInDatabase(song);
                        songTitle.setText(track.name);
                        addTag(track);
                    }
                });
    }

    private void addTag(Track track) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedTag = editView.getText().toString();
                if(!addedTag.isEmpty()) {
                    SongDatabase song = new SongDatabase();
                    mDbHelper.addTagToSong(song, addedTag);
                    Log.d("ADDED", "ADDED" + addedTag);
                    tagName.setText(mDbHelper.displaySongTag(track.uri).toString());
                }
            }
        });
    }

    private void setViews() {
        button = (Button)findViewById(R.id.button);
        editView = (EditText)findViewById(R.id.editView);
        songTitle = (TextView)findViewById(R.id.textView);
        tagName = (TextView)findViewById(R.id.tagName);
    }
}