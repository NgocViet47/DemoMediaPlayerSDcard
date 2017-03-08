package com.example.mypc.demomediaplayersdcard.model;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 3/7/2017.
 */

public class Singleton {
    private static Singleton instance;

    private int positionSong;

    private ArrayList<File> listFileSong;

    public ArrayList<File> getListFileSong() {
        return listFileSong;
    }

    public void setListFileSong(ArrayList<File> listFileSong) {
        this.listFileSong = listFileSong;
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    public int getPositionSong() {
        return positionSong;
    }

    public void setPositionSong(int positionSong) {
        this.positionSong = positionSong;
    }

}
