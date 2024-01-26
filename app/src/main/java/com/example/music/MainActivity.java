package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
RecyclerView rv_DisplaySong;
String [] item;
    ArrayList<File> songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find ids
        rv_DisplaySong = findViewById(R.id.rv_DisplaySongs);

        //set Layout Manager
        rv_DisplaySong.setLayoutManager(new LinearLayoutManager(this));
        // Get Permisions
        Dexter.withContext(MainActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        songs = fetchSong(Environment.getExternalStorageDirectory());
        item = new String[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            item[i] = songs.get(i).getName();
        }
        //Set Adapter
        rvSong_Adapter rvSong_adapter = new rvSong_Adapter(songs, MainActivity.this);
        rv_DisplaySong.setAdapter(rvSong_adapter);
                        Collections.sort(songs);


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


     public ArrayList<File> fetchSong(File file){
        ArrayList arrayList = new ArrayList();
        File[] arrSongs = file.listFiles();
        if (arrSongs != null){
            for (File myfile:  arrSongs){
                if (!myfile.isHidden() && myfile.isDirectory()){
                    arrayList.addAll(fetchSong(myfile));
                }else {
                    if (myfile.getName().endsWith(".mp3")){
                        arrayList.add(myfile);
                    }
                }

            }
        }
        return arrayList;
     }

}