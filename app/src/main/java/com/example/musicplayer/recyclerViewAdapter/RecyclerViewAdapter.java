package com.example.musicplayer.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.AudioActivity;
import com.example.musicplayer.AudioModel;
import com.example.musicplayer.R;

import java.io.Serializable;
import java.util.ArrayList;

//this is adapter class of recyclerView which basically have all info to be inserted in recyclerView
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //here arrayList is of audioModel type, ie, our device will have many audio which will be stored in arrayList. each audio's info will be defined by audioModel class
    private Context context;
    private ArrayList<AudioModel> audioArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<AudioModel> audioModels) //constructor
    {
        this.context = context;
        this.audioArrayList = audioModels;
    }
    @NonNull
    @Override //this is basically view for our single item which we have designed as row layout
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.songTitleTextView.setText(audioArrayList.get(position).getAudioTitle());
        holder.albumTextView.setText(audioArrayList.get(position).getAudioAlbum());

    }

    @Override
    public int getItemCount() {
        return audioArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView songImageView;
        public TextView songTitleTextView;
        public TextView albumTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            songImageView = itemView.findViewById(R.id.songImageView);
            songTitleTextView = itemView.findViewById(R.id.songTitleTextView);
            albumTextView = itemView.findViewById(R.id.albumTextView);
        }

        @Override
        public void onClick(View v) {
            Log.i("RecyclerView","item clicked");
            int position = this.getAdapterPosition();
//            AudioModel audioModel = audioArrayList.get(position);
//            String title = audioModel.getAudioTitle();
//            String album = audioModel.getAudioAlbum();
//            String artist = audioModel.getAudioArtist();
            Intent intent = new Intent(context, AudioActivity.class);
//            intent.putExtra("SongName",title);
//            intent.putExtra("SongAlbum",album);
//            intent.putExtra("SongArtist",artist);
            intent.putExtra("position",position);
//            intent.putExtra("audioList",(Serializable) audioArrayList);

            context.startActivity(intent);
        }
    }


}
