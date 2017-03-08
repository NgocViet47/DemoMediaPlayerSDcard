package com.example.mypc.demomediaplayersdcard.sevice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mypc.demomediaplayersdcard.getfile.PlaylistManager;
import com.example.mypc.demomediaplayersdcard.model.Singleton;

/**
 * Created by MyPC on 3/6/2017.
 */

public class MusicService extends Service {

    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREVIOUS = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PAUSE = "PAUSE";
    public static final String ACTION_STOP = "STOP";
    public static final String ACTION_SEEKGO = "SEEKGO";
    public static final String ACTION_SEEKBACK = "SEEKBACK";
    public static final String SERVICE_START = "STARTSERVICE";

    private MediaPlayer mMediaPlayer = null;

    private static PlaylistManager playlistManager = new PlaylistManager();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playlistManager.getListSong();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), playlistManager.getUriSong(Singleton.getInstance().getPositionSong()));
        mMediaPlayer.setLooping(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {

        String action = i.getAction();
        Log.i("LOG ========>", "intent:" + action);

        if (action.equals(SERVICE_START)) {
            mMediaPlayer.start();
            processStartDetailRequest();
        } else if (action.equals(ACTION_PLAY))
            processPlayRequest();
        else if (action.equals(ACTION_PAUSE))
            processPauseRequest();
        else if (action.equals(ACTION_STOP))
            processStopRequest();
        else if (action.equals(ACTION_NEXT))
            processNextRequest();
        else if (action.equals(ACTION_PREVIOUS))
            processPreviousRequest();
        else if (action.equals(ACTION_SEEKGO))
            processSeekGoRequest();
        else if (action.equals(ACTION_SEEKBACK))
            processSeeKBackRequest();
        else if (action == null) {
            mMediaPlayer.start();
            return START_NOT_STICKY;
        }
        return START_STICKY;
    }

    private void processStartDetailRequest() {

    }

    private void processStopRequest() {
        playlistManager.stopSong();
        if (PlaylistManager.getmState() == PlaylistManager.PlayerState.Stopped) {
            mMediaPlayer.stop();
        }
    }
    private void processSeeKBackRequest() {

    }

    private void processSeekGoRequest() {

    }

    private void processPreviousRequest() {
        setPlaySongFromUri(playlistManager.goToPreviousSong(Singleton.getInstance().getPositionSong()));
    }

    private void processNextRequest() {
        setPlaySongFromUri(playlistManager.goToNextSong(Singleton.getInstance().getPositionSong()));
    }

    private void setPlaySongFromUri(Uri uri) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
    }

    private void processPauseRequest() {
        playlistManager.pauseSong();
        if (PlaylistManager.getmState() == PlaylistManager.PlayerState.Paused) {
            mMediaPlayer.pause();
        }
    }
    private void processPlayRequest() {
        if (PlaylistManager.getmState() == PlaylistManager.PlayerState.Stopped) {
            setPlaySongFromUri(playlistManager.getUriSong(Singleton.getInstance().getPositionSong()));
            playlistManager.playSong();
        } else {
            playlistManager.playSong();
            if (PlaylistManager.getmState() == PlaylistManager.PlayerState.Playing) {
                mMediaPlayer.start();
            }
        }
    }
}
