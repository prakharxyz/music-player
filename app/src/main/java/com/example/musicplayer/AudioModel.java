package com.example.musicplayer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

//it is a constructor class that is actually skeleton of our app. we use this class to set title, album, artist etc to variable which our cursor read from device
//then after getting all details about audio from external storage, we have to get necessary info to recyclerView adapter so that it can display it in recyclerView
public class AudioModel  {
    String audioTitle;
    String audioAlbum;
    String audioArtist;
    String audioDuration;
    Bitmap audioImage;
    Uri imageUri;
    Uri audioUri;

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public Bitmap getAudioImage() {
        return audioImage;
    }

    public void setAudioImage(Bitmap audioImage) {
        this.audioImage = audioImage;
    }

    public String getAudioAlbum() {
        return audioAlbum;
    }

    public void setAudioAlbum(String audioAlbum) {
        this.audioAlbum = audioAlbum;
    }

    public String getAudioArtist() {
        return audioArtist;
    }

    public void setAudioArtist(String audioArtist) {
        this.audioArtist = audioArtist;
    }

    public String getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(String audioDuration) {
        this.audioDuration = audioDuration;
    }

    public Uri getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(Uri audioUri) {
        this.audioUri = audioUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
