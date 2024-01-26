package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player_Activity extends AppCompatActivity {

    ImageView  imgPause , imgNext , imgPrevious;
    TextView tv_playerSName;

    String tt;
    TextView tv_ss , tv_tds;
    ArrayList<File> songs;
    int position;
    String csName;
   static MediaPlayer mediaPlayer;
    Thread updateSeekbar;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        seekBar = findViewById(R.id.seekbar);
        tv_ss = findViewById(R.id.tv_ss);
        tv_tds = findViewById(R.id.tv_tds);
        tv_playerSName = findViewById(R.id.tv_playerSongName);
        imgNext = findViewById(R.id.next);
        imgPause = findViewById(R.id.pause);
        imgPrevious = findViewById(R.id.previous);

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.start();
        }





        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs =(ArrayList) intent.getParcelableArrayListExtra("songs");
     position =  intent.getIntExtra("position" , 0);
     csName =intent.getStringExtra("sName");
     tv_playerSName.setText(csName);
     Uri uri = Uri.parse(songs.get(position).toString());
     mediaPlayer = MediaPlayer.create(getApplicationContext() , uri);
     mediaPlayer.start();


     new Timer().scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
             seekBar.setProgress(mediaPlayer.getCurrentPosition());
         }
     },0 , 500);



     seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {
mediaPlayer.seekTo(seekBar.getProgress());
         }
     });





     updateSeekbar = new Thread(){
         @Override
         public void run() {
           int curentPosition = 0;
           int totalDuration = mediaPlayer.getDuration();


                 while (curentPosition > totalDuration) {
                     try {
                     sleep(500);
                     curentPosition = mediaPlayer.getCurrentPosition();
                         seekBar.setProgress(curentPosition);
                 }
                     catch (InterruptedException | IllegalStateException e) {
                         e.printStackTrace();
                     }
             }

         }
     };
     seekBar.setMax(mediaPlayer.getDuration());
     updateSeekbar.start();




//     mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//         @Override
//         public void onCompletion(MediaPlayer mediaPlayer) {
//             imgNext.performClick();
//         }
//     });

     tt = createTime(mediaPlayer.getDuration());
     tv_tds.setText(tt);

     final Handler handler = new Handler();
     final int delay = 1000;
             handler.postDelayed(new Runnable() {
         @Override
         public void run() {
             String ct = createTime(mediaPlayer.getCurrentPosition());
             tv_ss.setText(ct);
             handler.postDelayed(this , delay);
         }
     }, delay);

     imgPrevious.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             mediaPlayer.stop();
             if(position != 0){
                 position = position-1;
             }else{
                 position = songs.size()-1;
             }
             Uri uri1 = Uri.parse(songs.get(position).toString());
             mediaPlayer = MediaPlayer.create( getApplicationContext(), uri1);
             mediaPlayer.start();
             csName = songs.get(position).getName().toString();
             tv_playerSName.setText(csName);

         }
     });

     imgPause.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if (mediaPlayer.isPlaying()){
                 mediaPlayer.pause();
                 imgPause.setImageResource(R.drawable.play);
             }else {
                 mediaPlayer.start();
                 imgPause.setImageResource(R.drawable.pause);

             }
         }
     });


     imgNext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             mediaPlayer.stop();
             position = position+1;
             if (position == songs.size()-1){
                 position = 0;
             }
             Uri uri1 = Uri.parse(songs.get(position).toString());
             mediaPlayer = MediaPlayer.create(getApplicationContext() , uri1);
             csName = songs.get(position).getName().toString();

             tv_playerSName.setText(csName);
             mediaPlayer.start();

         }
     });

    }
    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time+= min+":";
        if (sec<10){
            time+="0";
        }
        time+=sec;
        return  time;
    }
}