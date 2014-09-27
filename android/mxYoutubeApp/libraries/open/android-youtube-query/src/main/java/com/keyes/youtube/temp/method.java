package com.keyes.youtube.temp;


import com.keyes.youtube.beans.Format;
import com.keyes.youtube.beans.VideoStream;
import com.keyes.youtube.utils.YoutubeQuality;

import java.util.ArrayList;

public class method {

    public static String getUrlByQuality(YoutubeQuality youtubeQuality, boolean pFallback, String pYouTubeFmtQuality) {
        ArrayList<Format> lFormats = youtubeQuality.getlFormats();
        ArrayList<VideoStream> lStreams = youtubeQuality.getlStreams();
        String lUriStr = null;
        // Search for the given format in the list of video formats
        // if it is there, select the corresponding stream
        // otherwise if fallback is requested, check for next lower format
        int lFormatId = Integer.parseInt(pYouTubeFmtQuality);

        Format lSearchFormat = new Format(lFormatId);
        while (!lFormats.contains(lSearchFormat) && pFallback) {
            int lOldId = lSearchFormat.getId();
            int lNewId = getSupportedFallbackId(lOldId);

            if (lOldId == lNewId) {
                break;
            }
            lSearchFormat = new Format(lNewId);
        }

        int lIndex = lFormats.indexOf(lSearchFormat);
        if (lIndex >= 0) {
            VideoStream lSearchStream = lStreams.get(lIndex);
            lUriStr = lSearchStream.getUrl();
        }
        return lUriStr;
    }

    public static int getSupportedFallbackId(int pOldId) {
        final int lSupportedFormatIds[] = { 13, // 3GPP (MPEG-4 encoded) Low quality
                17, // 3GPP (MPEG-4 encoded) Medium quality
                18, // MP4 (H.264 encoded) Normal quality
                22, // MP4 (H.264 encoded) High quality
                37 // MP4 (H.264 encoded) High quality
        };
        int lFallbackId = pOldId;
        for (int i = lSupportedFormatIds.length - 1; i >= 0; i--) {
            if (pOldId == lSupportedFormatIds[i] && i > 0) {
                lFallbackId = lSupportedFormatIds[i - 1];
            }
        }
        return lFallbackId;
    }
}
