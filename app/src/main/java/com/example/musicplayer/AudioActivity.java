package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.recyclerViewAdapter.RecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AudioActivity extends AppCompatActivity {

    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    ImageView playImageView;
//    ArrayList<AudioModel> audioList;
    RecyclerViewAdapter recyclerViewAdapter;

    TextView titleTextView;
    TextView albumTextView;
    TextView artistTextView;

    Thread updateSeek;
    String name;
    String album;
    String artist;
    int audio_index ;
    int pos;

    public void playAudio(int position) {
        try {
            Log.i("playAudio", "reached function");
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, StartActivity.audioArrayList.get(position).getAudioUri());
            mediaPlayer.prepare();
            mediaPlayer.start();
            name = StartActivity.audioArrayList.get(pos).getAudioTitle();
            album = StartActivity.audioArrayList.get(pos).getAudioAlbum();
            artist = StartActivity.audioArrayList.get(pos).getAudioArtist();
            titleTextView.setText(name);
            albumTextView.setText(album);
            artistTextView.setText(artist);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playpause(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playImageView.setImageResource(R.drawable.pause);

        }
        else {
            mediaPlayer.start();
            playImageView.setImageResource(R.drawable.play);
        }
    }

    public void next(View view) {
        if (pos<audio_index)
        {
            pos++;
        }
        else if (pos == audio_index)
        {
            pos=0;
        }
        playAudio(pos);
    }
    public void previous(View view) {
        if (pos>0)
        {
            pos--;
        }
        else if (pos == 0)
        {
            pos=audio_index;
        }
        playAudio(pos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Intent intent = getIntent();

        pos = intent.getIntExtra("position",0);

//        Bundle bundle = intent.getExtras();
//        audioList = (ArrayList) bundle.getParcelableArrayList("audioList");
//        audioList = (ArrayList<AudioModel>) intent.getSerializableExtra("audioList");

        recyclerViewAdapter = new RecyclerViewAdapter(this,StartActivity.audioArrayList);
        audio_index = recyclerViewAdapter.getItemCount();

        playImageView = findViewById(R.id.playImageView);
        titleTextView = findViewById(R.id.titleTextView);
        albumTextView = findViewById(R.id.albumTextView2);
        artistTextView = findViewById(R.id.artistTextView);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
        playAudio(pos);
        SeekBar volSeekBar = (SeekBar) findViewById(R.id.volSeekBar);
        SeekBar scrubSeekBar = (SeekBar) findViewById(R.id.scrubSeekBar);

        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //get max volume through audioManager store it in a var
        int curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //get current volume through audioManager ,store it in a var
        volSeekBar.setMax(maxVol);
        volSeekBar.setProgress(curVol);

        volSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); //now set the volume of audio to progress(or value of seekbar)
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        scrubSeekBar.setMax(mediaPlayer.getDuration()); //its max slider value will be duration of audio

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mediaPlayer.seekTo(progress); //seek or jump audio to progress(or value of scrubSeekBar)
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        updateSeek = new Thread(){
            @Override
            public void run() {
                int currentPosition = 0;
                try{
                    while(currentPosition<mediaPlayer.getDuration()){
                        currentPosition = mediaPlayer.getCurrentPosition();
                        scrubSeekBar.setProgress(currentPosition);
                        sleep(800);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();
        //now we want our scrubSeekBar to progress or keep moving as the audio progresses with every milliseconds
//       

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateSeek.interrupt();
    }



}
