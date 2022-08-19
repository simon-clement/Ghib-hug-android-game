package com.blblbl.forgotname;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.VideoView;
import android.view.View;


public class StartActivity extends Activity {
    private VideoView myVideoView;
    private static final String TAG = "StartActivity";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startedReal = false;
        setContentView(R.layout.activity_start);
        try {
            myVideoView = (VideoView) findViewById(R.id.videoView);
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videopening));
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
            e.printStackTrace();
        }
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                getToTheGame();
            }
        });
        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                myVideoView.start();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getToTheGame();
        return true;
    }

    public void getToTheGame() {
        if (!startedReal) {
            startedReal = true;
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(NAME_JUST_STARTED, true);
            startActivity(i);
            myVideoView.stopPlayback();
            finish(); // c'est safe! une activity peut start alors que l'autre est déjà finished
        }
    }
    public void getToTheGame(View v) {
        getToTheGame();
    }
    public final static String NAME_JUST_STARTED = "com.blbl.just_started";
    private boolean startedReal;

}
