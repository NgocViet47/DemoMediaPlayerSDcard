package com.example.mypc.demomediaplayersdcard.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.demomediaplayersdcard.R;
import com.example.mypc.demomediaplayersdcard.getfile.PlaylistManager;
import com.example.mypc.demomediaplayersdcard.model.Singleton;
import com.example.mypc.demomediaplayersdcard.sevice.MusicService;
import com.example.mypc.demomediaplayersdcard.utils.BundleExtra;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnList, btnNext, btnStop, btnPlayAndPause, btnPrevious;
    private SeekBar seekBarPlay;
    private ArrayList<File> mySongs;
    private MediaPlayer mediaPlayer;
    private int position;
    private Thread updateSeekBar;
    private TextView editTextViewSong;
    private ImageView imageViewCD;
    private Animation animFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();
    }

    private void setAnimation() {
        animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageViewCD.startAnimation(animFade);
    }

    private void addEvent() {
        btnList.setOnClickListener(this);
        btnPlayAndPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        getIntentDetail();
        //updateSeekBar();
        /*if(mediaPlayer.getCurrentPosition()== mediaPlayer.getDuration()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position = (position + 1) % mySongs.size();
            Uri uri = Uri.parse(mySongs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }*/
        //updateSeekBar.start();
        setEditText();
        setAnimation();
    }
    public void setEditText(){
        int id = Singleton.getInstance().getPositionSong();
        String name = Singleton.getInstance().getListFileSong().get(id).getName().toString();
        editTextViewSong.setText(name);
    }

    /*private void updateSeekBar(){
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        //seekBarPlay.setProgress(currentPosition);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBarPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               // btnPlayAndPause.setBackgroundResource(R.drawable.pause);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mediaPlayer.seekTo(seekBarPlay.getProgress());
                //btnPlayAndPause.setBackgroundResource(R.drawable.play);
            }
        });
    }*/

    private void addControl() {
        btnList = (ImageButton) findViewById(R.id.btnListMP3);
        btnPrevious = (ImageButton) findViewById(R.id.imgButtonPrevious);
        btnNext = (ImageButton) findViewById(R.id.imgButtonNext);
        btnPlayAndPause = (ImageButton) findViewById(R.id.imgButtonPlayAndPause);
        btnStop = (ImageButton) findViewById(R.id.imgButtonStop);
        seekBarPlay = (SeekBar) findViewById(R.id.seekBarPlay);
        editTextViewSong = (TextView) findViewById(R.id.txtViewSong);
        imageViewCD = (ImageView) findViewById(R.id.imgCD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListMP3:
                imgButtonListScreen();
                break;
            case R.id.imgButtonPlayAndPause:
                btnPlayAndPauseClick();
                break;
            case R.id.imgButtonStop:
                btnStopClick();
                break;
            case R.id.imgButtonNext:
                btnNextClick();
                break;
            case R.id.imgButtonPrevious:
                btnPreviousClick();
                break;
        }
    }

    private void btnStopClick() {
        if(PlaylistManager.getmState()== PlaylistManager.PlayerState.Playing ||
                PlaylistManager.getmState() == PlaylistManager.PlayerState.Paused)
        {
            Intent i = new Intent(MainActivity.this, MusicService.class);
            i.setAction(MusicService.ACTION_STOP);
            stopService(i);
            btnPlayAndPause.setBackgroundResource(R.drawable.pause);
        }
        else
            Toast.makeText(getApplicationContext(), "Nhạc đả dừng rồi không cần ", Toast.LENGTH_SHORT).show();
    }

    private void btnPlayAndPauseClick() {
        if (PlaylistManager.getmState() == PlaylistManager.PlayerState.Playing) {

            Intent i = new Intent(MainActivity.this, MusicService.class);
            i.setAction(MusicService.ACTION_PAUSE);
            startService(i);


            btnPlayAndPause.setBackgroundResource(R.drawable.pause);
        } else {
            Intent i = new Intent(MainActivity.this, MusicService.class);
            i.setAction(MusicService.ACTION_PLAY);
            startService(i);

            setPlayBtnPlay();
        }
    }

    private void btnPreviousClick() {

        Intent i = new Intent(MainActivity.this, MusicService.class);
        i.setAction(MusicService.ACTION_PREVIOUS);
        startService(i);

        setPlayBtnPlay();
        setEditText();

        //seekBarPlay.setMax(mediaPlayer.getDuration());
    }

    private void btnNextClick() {

        Intent i = new Intent(MainActivity.this, MusicService.class);
        i.setAction(MusicService.ACTION_NEXT);
        startService(i);

        setPlayBtnPlay();
        setEditText();

        //seekBarPlay.setMax(mediaPlayer.getDuration());
    }

    private void getIntentDetail() {
        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList(BundleExtra.DATA_SONG);
        position = bundle.getInt(BundleExtra.POSITION_SONG);

        Uri uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        seekBarPlay.setMax(mediaPlayer.getDuration());*/
    }

    private void imgButtonListScreen() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }
    private void setPlayBtnPlay()
    {
        btnPlayAndPause.setBackgroundResource(R.drawable.play);
    }

}
