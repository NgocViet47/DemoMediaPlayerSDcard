package com.example.mypc.demomediaplayersdcard.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mypc.demomediaplayersdcard.R;
import com.example.mypc.demomediaplayersdcard.model.Singleton;
import com.example.mypc.demomediaplayersdcard.sevice.MusicService;
import com.example.mypc.demomediaplayersdcard.utils.BundleExtra;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity  {
    private ListView lv;
    private List<String> listSongs;
    private  ArrayList<File> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addControl();
        addEvent();

    }

    private void addEvent() {
        clickListviewItems();
        addListView();
    }
    public void clickListviewItems(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSong = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intentSong);

                Intent intent = new Intent(DetailActivity.this, MusicService.class);
                intent.putExtra(BundleExtra.POSITION_SONG,position);
                intent.setAction(MusicService.SERVICE_START);
                stopService(intent);
                startService(intent);

                Singleton.getInstance().setPositionSong(position);
                Singleton.getInstance().setListFileSong(mySongs);
            }
        });
    }
    private void addListView(){
        mySongs = findSongs(Environment.getExternalStorageDirectory());
        Collections.sort(mySongs);
        listSongs = new ArrayList<>();
        for (int i = 0;i<mySongs.size();i++){
            listSongs.add(mySongs.get(i).getName().toString().replace(".mp3","").replace(".MP3",""));
        }
        ArrayAdapter<String> adapterSong = new ArrayAdapter<String>(getApplicationContext(),R.layout.songs_layout,R.id.txtSongs,listSongs);
        lv.setAdapter(adapterSong);
    }

    private void addControl() {
        lv = (ListView) findViewById(R.id.listFileMp3);
    }

    private ArrayList<File> findSongs(File root) {
        ArrayList<File> arrayList = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory()&& !singleFile.isHidden()){
                arrayList.addAll(findSongs(singleFile));
            }
            else {
                if(singleFile.getName().endsWith(".mp3")||singleFile.getName().endsWith(".MP3"))
                {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }
}
