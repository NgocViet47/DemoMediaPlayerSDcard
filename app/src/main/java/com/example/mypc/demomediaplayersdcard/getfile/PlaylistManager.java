package com.example.mypc.demomediaplayersdcard.getfile;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mypc.demomediaplayersdcard.activity.MainActivity;
import com.example.mypc.demomediaplayersdcard.model.Singleton;
import com.example.mypc.demomediaplayersdcard.sevice.MusicService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by MyPC on 3/7/2017.
 */

public class PlaylistManager {

    private ArrayList<File> arrayListSong = null;

    public PlaylistManager() {
        arrayListSong = new ArrayList<File>();
    }

    public PlaylistManager(ArrayList<File> list) {
        arrayListSong = list;
    }

    public ArrayList<File> getArrayListSong() {
        return arrayListSong;
    }
    public void setArrayListSong(ArrayList<File> arrayListSong) {
        this.arrayListSong = arrayListSong;
    }

    public void getListSong(){
        setArrayListSong(Singleton.getInstance().getListFileSong());
    }


    public enum PlayerState {
        Stopped, Preparing, Playing, Paused
    }

    private static PlayerState mState = PlayerState.Playing;

    public static PlayerState getmState() {
        return mState;
    }

    public static void setmState(PlayerState mState) {
        PlaylistManager.mState = mState;
    }

    public Uri getUriSong(Integer position){
        Uri uri = Uri.parse(arrayListSong.get(position).toString());
        return uri;
    }
    public void stopSong(){
        if(getmState()==PlayerState.Paused||
                getmState()==PlayerState.Playing)
            setmState(PlayerState.Stopped);
        else
            Log.e("TAG","==========> Đả Stop rồi");
    }

    public void playSong(){
        if (getmState() == PlayerState.Paused ||
                getmState() == PlayerState.Stopped ||
                getmState() == PlayerState.Preparing) {
            setmState(PlayerState.Playing);
        }
    }

    public void pauseSong(){
        if(getmState() == PlayerState.Playing)
            setmState(PlayerState.Paused);
    }
    public Uri goToNextSong(int position){
        position = (position + 1) % arrayListSong.size();
        Singleton.getInstance().setPositionSong(position);
        Uri uri = getUriSong(Singleton.getInstance().getPositionSong());
        return uri;
    }
    public Uri goToPreviousSong(int position){
        position = (position - 1 < 0) ? arrayListSong.size() - 1 : position - 1;
        Singleton.getInstance().setPositionSong(position);
        Uri uri = getUriSong(Singleton.getInstance().getPositionSong());
        return uri;
    }
}
