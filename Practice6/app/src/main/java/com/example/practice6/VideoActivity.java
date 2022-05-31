package com.example.practice6;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    Button buttonFilm;
    Button buttonWatch;

    private static final int VIDEO_CAPTURE = 101;
    Uri videoUri;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        buttonFilm = findViewById(R.id.buttonFilm);
        buttonWatch = findViewById(R.id.buttonView);


        // capture film a video using hardware camera
        buttonFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });


        // watch captured filmed video
        buttonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playbackRecordedVideo();
            }
        });


        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/myvideo.mp4";
    }


    // ==================================================================================
    // ==================================================================================
    // record video
    private void dispatchTakeVideoIntent() {
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                File mediaFile = new File(filePath); //Environment.getExternalStorageDirectory().getAbsolutePath()
                videoUri = Uri.fromFile(mediaFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(intent, VIDEO_CAPTURE);
            } else {
                Toast.makeText(this, "No camera on device", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // ==================================================================================
    // ==================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video has been saved to:\n" + intent.getData(), Toast.LENGTH_LONG).show();
                playbackRecordedVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video", Toast.LENGTH_LONG).show();
            }
        }
    }


    // ==================================================================================
    // ==================================================================================
    // play watch video from file
    public void playbackRecordedVideo() {
        MediaController m = new MediaController(this);
        videoView.setMediaController(m);

        Uri u = Uri.parse(filePath);

        videoView.setVideoURI(u);
        videoView.start();
    }

    // ==================================================================================
    // ==================================================================================

}
