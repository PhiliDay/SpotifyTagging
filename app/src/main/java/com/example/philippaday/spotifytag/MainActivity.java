package com.example.philippaday.spotifytag;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.ContentApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.types.Image;
import com.spotify.protocol.types.ListItem;
import com.spotify.protocol.types.ListItems;
import com.spotify.protocol.types.Track;

import org.w3c.dom.Text;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "d9abadc69fbb46239385d025042d8c02";
    private static final String REDIRECT_URI = "com.yourdomain.yourapp://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final String TAG = MainActivity.class.getSimpleName();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ErrorCallback mErrorCallback = throwable -> logError(throwable, "Boom!");
    SpotifyDbHelper mDbHelper = new SpotifyDbHelper(this);
    TextView songTitle, tagName;
    EditText editView;
    Button button;
    String tag;
    ImageView mCoverArtImageView;
    TextView mImageLabel;


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
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;

                    if (track != null) {

                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        SongDatabase song = new SongDatabase(track.uri, track.name, tag, track.album, track.imageUri, track.artist);
                        songTitle.setText(song.getSongName());
                        mDbHelper.createSongInDatabase(song);
                        Log.d("MainActivity2", song.getSongName());
                        addTag();
                        mSpotifyAppRemote.getImagesApi()
                                .getImage(playerState.track.imageUri, Image.Dimension.LARGE)
                                .setResultCallback(bitmap -> {
                                    mCoverArtImageView.setImageBitmap(bitmap);
                                    mImageLabel.setText(String.format(Locale.ENGLISH, "%d x %d", bitmap.getWidth(), bitmap.getHeight()));
                                });
                    }
                });
    }

    private void addTag() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedTag = editView.getText().toString();
                if(!addedTag.isEmpty()) {
                    SongDatabase song = new SongDatabase();
                    mDbHelper.addTagToSong(song, addedTag);
                    Log.d("ADDED", "ADDED" + addedTag);
                    tagName.setText(song.getSongTag());
                }
            }
        });
    }

    private void setViews() {
        button = (Button)findViewById(R.id.button);
        editView = (EditText)findViewById(R.id.editView);
        songTitle = (TextView)findViewById(R.id.textView);
        tagName = (TextView)findViewById(R.id.tagName);
        mCoverArtImageView = findViewById(R.id.mCoverArtImageView);
        mImageLabel = findViewById(R.id.mImageLabel);
    }

    private void playUri(String uri) {
        mSpotifyAppRemote.getPlayerApi()
                .play(uri)
                .setResultCallback(empty -> logMessage("Play successful"))
                .setErrorCallback(mErrorCallback);
    }

    private void logMessage(String msg) {
        logMessage(msg);
    }

    private void logError(Throwable throwable, String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg, throwable);
    }
}