package com.keyes.youtube.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.libraries.mediaframework.R;

public class Sample extends Activity {

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
//
//        setContentView(R.layout.sample);
//
//        final TextView videoIdTextView = (TextView) findViewById(R.id.youtubeIdText);
//        final Button viewVideoButton = (Button) findViewById(R.id.viewVideoButton);
//
//        viewVideoButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View pV) {
//
//                String videoId = videoIdTextView.getText().toString();
//
//                if (videoId == null || videoId.trim().equals("")) {
//                    return;
//                }
//
//                Uri parse = Uri.parse("ytv://" + videoId);
//                Intent lVideoIntent = new Intent(null, parse, Sample.this, OpenYouTubePlayerActivity.class);
//                startActivity(lVideoIntent);
//
//            }
//        });

    }


}
