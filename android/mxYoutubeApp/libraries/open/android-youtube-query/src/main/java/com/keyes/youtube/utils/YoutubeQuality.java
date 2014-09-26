package com.keyes.youtube.utils;

import com.keyes.youtube.beans.Format;
import com.keyes.youtube.beans.VideoStream;

import java.util.ArrayList;

public class YoutubeQuality {

    private ArrayList<Format> lFormats;

    private ArrayList<VideoStream> lStreams;

    public ArrayList<Format> getlFormats() {
        return lFormats;
    }

    public void setlFormats(ArrayList<Format> lFormats) {
        this.lFormats = lFormats;
    }

    public ArrayList<VideoStream> getlStreams() {
        return lStreams;
    }

    public void setlStreams(ArrayList<VideoStream> lStreams) {
        this.lStreams = lStreams;
    }

}
