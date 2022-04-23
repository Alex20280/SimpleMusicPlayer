package com.example.playaudio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer media;
    SeekBar seekBar;
    AudioManager audioManager;
    ImageView BackbackgoundImageview, playBackgroundImageview, forwardBackgroungImageview, playImageview;
    MediaMetadataRetriever metaRetriever;
    TextView songNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackbackgoundImageview = findViewById(R.id.BackbackgoundImageview);
        playBackgroundImageview = findViewById(R.id.playBackgroundImageview);
        forwardBackgroungImageview = findViewById(R.id.forwardBackgroungImageview);
        playImageview = findViewById(R.id.playImageview);
        songNameTv = findViewById(R.id.songNameTv);

        ActionBar actionBar;
        Window window = this.getWindow();
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#455b80"));
        actionBar.setBackgroundDrawable(colorDrawable);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.turquoise));


        seekBar = findViewById(R.id.volumeSeekBar);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //volumeSeekBar.setMax(maxVolume);

        media = MediaPlayer.create(this, R.raw.stuff);

        seekBar.setMax(media.getDuration());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(media.getCurrentPosition());
            }
        }, 0, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    media.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        BackbackgoundImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.reset();
                //String uriPath = "android.resource://"+getPackageName()+"/raw/"+ "video";
                String uriPath = "android.resource://com.example.playaudio" +"/"+ R.raw.stuff;
                try {
                    media.setDataSource(getApplicationContext(), Uri.parse(uriPath));
                    media.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                media.start();

            }
        });

        playBackgroundImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (media.isPlaying()) {
                    media.pause();
                    playImageview.setImageResource(R.drawable.play);
                } else  {
                    media.start();
                    playImageview.setImageResource(R.drawable.pause);
                }

            }
        });

        forwardBackgroungImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.reset();
                String uriPath = "android.resource://com.example.playaudio" +"/"+ R.raw.audio;
                try {
                    media.setDataSource(getApplicationContext(), Uri.parse(uriPath));
                    media.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                media.start();
            }
        });



    }

}