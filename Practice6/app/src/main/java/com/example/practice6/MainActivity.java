package com.example.practice6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.Clock;

public class MainActivity extends AppCompatActivity {

    public static String sRecordedFileName;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private Button btnRec;
    private Button btnPlay;
    private Button btnStop;

    private boolean isRecording = false;
    private boolean isPlaying = false;
    private boolean isPaused = false;

    SeekBar seekBar;
    private Handler mHandler;
    private Runnable mRunnable;
    TextView textViewStart;
    TextView textViewEnd;

    Button videoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRec = (Button) findViewById(R.id.buttonRec);
        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnStop = (Button) findViewById(R.id.buttonStop);

        // seekbar for duration, time progress
        seekBar = findViewById(R.id.playerSeekBar);
        seekBar.setProgress(0);
        mHandler = new Handler();
        textViewStart = findViewById(R.id.textViewStart);
        textViewEnd = findViewById(R.id.textViewEnd);
        textViewStart.setText("00:00");
        textViewEnd.setText("00:00");

        btnRec.setEnabled(true);
        btnPlay.setEnabled(true);
        btnStop.setEnabled(false);


        // permissions for audio record and external storage
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,}, 0);
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        //


        sRecordedFileName = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/audio_file.3gpp";

        btnRec.setOnClickListener(recordClick);
        btnPlay.setOnClickListener(playClick);
        btnStop.setOnClickListener(stopClick);

        btnRec.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_circle_notrec, 0, 0, 0);
        playButtonStyle();
        btnStop.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0, 0, 0);


        // go to video activity
        videoBtn=findViewById(R.id.buttonVideo);
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
        //
    }


    // ==================================================================================
    // ==================================================================================
    // record recording button - start recording and stop recording
    View.OnClickListener recordClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!isRecording) {
                try {
                    //releaseMediaRecorder();
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(sRecordedFileName);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //mediaRecorder.setAudioEncodingBitRate(16);
                    //mediaRecorder.setAudioSamplingRate(44100);
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                    isRecording = true;
                    btnRec.setEnabled(true);
                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(false);

                    btnRec.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rec, 0, 0, 0);
                    Toast.makeText(MainActivity.this, "Recording started!", Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Sorry! " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

                isRecording = false;
                btnRec.setEnabled(true);
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);

                btnRec.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_circle_notrec, 0, 0, 0);
                Toast.makeText(MainActivity.this, "Recording ended!", Toast.LENGTH_SHORT).show();
            }

        }
    };


    // ==================================================================================
    // ==================================================================================
    // play playing button - start playing and pause playing
    View.OnClickListener playClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!isPlaying) {
                try {
                    //releaseMediaPlayer();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(sRecordedFileName);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            isPlaying = false;
                            isPaused = false;
                            btnRec.setEnabled(true);
                            btnPlay.setEnabled(true);
                            btnStop.setEnabled(false);

                            playButtonStyle();
                        }
                    });

                    isPlaying = true;
                    btnRec.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnStop.setEnabled(true);

                    valuesSeekBar(mediaPlayer.getDuration(),textViewEnd);
                    pauseButtonStyle();
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Sorry! " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (isPaused) {
                    mediaPlayer.start();
                    isPaused = false;
                    pauseButtonStyle();
                } else {
                    mediaPlayer.pause();
                    isPaused = true;
                    playButtonStyle();
                }
            }

            initializeSeekBar();
        }
    };

    // ==================================================================================
    // ==================================================================================
    // stop play playing
    View.OnClickListener stopClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                releaseMediaRecorder();
                releaseMediaPlayer();

                btnRec.setEnabled(true);
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);

                seekBar.setProgress(0);
                textViewStart.setText("00:00");
                textViewEnd.setText("00:00");
                playButtonStyle();
            } catch (IllegalStateException e) {
                Log.e("stopClick", "Error while stopping the recording.", e);
            }

        }
    };

    // ==================================================================================
    // ==================================================================================

    private void releaseMediaRecorder() {
        if (isRecording == true) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        }
    }

    private void releaseMediaPlayer() {
        if (isPlaying == true) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaRecorder();
        releaseMediaPlayer();
    }


    // button buttons style
    private void playButtonStyle() {
        btnPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0);
        btnPlay.setText("Play");
    }

    private void pauseButtonStyle() {
        btnPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0);
        btnPlay.setText("Pause");
    }
    //


    // display calculate values of end - seekbar seek bar
    private void valuesSeekBar(double duration, TextView textView) {
        int min = (int) duration / 60000;
        double sec = ((duration / 60000.0 - min) * 60.0);

        String min_s = min + "";
        String sec_s = (int) sec + "";
        if (min < 10) {
            min_s = "0" + min_s;
        }
        if (sec < 10) {
            sec_s = "0" + sec_s;
        }

        textView.setText(min_s + ":" + sec_s);
    }

    // ==================================================================================
    // ==================================================================================
    // seekbar duration player
    protected void initializeSeekBar() {
        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000; //100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
                    seekBar.setProgress(mCurrentPosition);
                    valuesSeekBar(mediaPlayer.getCurrentPosition(), textViewStart);
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    // ==================================================================================
    // ==================================================================================

}
