package com.example.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class rvSong_Adapter extends RecyclerView.Adapter<rvSong_Adapter.ViewHolder> {
    ArrayList<File> songs;
    MainActivity mainActivity = new MainActivity();
    Context context;
    public rvSong_Adapter(ArrayList<File> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.songdisplay , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.tvSongName.setText(songs.get(position).getName());

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent inext = new Intent(view.getContext() , Player_Activity.class);

        String sName = songs.get(position).getName();
        inext.putExtra("sName" , sName);
        inext.putExtra("position" , position);
        inext.putExtra("songs" , songs);


        view.getContext().startActivity(inext);

//       intent.putExtra("sNAme" , songs.get(position).getName());
//        intent.putExtra("songsList" , songs);
//        intent.putExtra("position" , position);





//        Uri uri = Uri.parse(songs.get(position).toString());
//        Uri uri = Uri.parse(songs.get(position).toString());
//        mediaPlayer = MediaPlayer.create(context ,uri );

//        mediaPlayer.start();
//        String curentSong = songs.get(position).getName().toString();

    }
});
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
TextView tvSongName;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvSongName = itemView.findViewById(R.id.tv_rvSongName);
    }
}
}
