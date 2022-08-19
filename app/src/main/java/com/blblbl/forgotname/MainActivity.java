package com.blblbl.forgotname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blblbl.forgotname.moteur.GameView;
import com.blblbl.forgotname.moteur.TheGame;
import com.blblbl.forgotname.squive.squive.GameSquive;

public class MainActivity extends Activity {
    private MediaPlayer musicPlayer;
    private MediaPlayer mSoundWinPlayer, mSoundDeathPlayer, mSoundLaserPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSoundDeathPlayer = MediaPlayer.create(this,  R.raw.sound_dead);
        mSoundWinPlayer = MediaPlayer.create(this,  R.raw.sound_win);
        mSoundLaserPlayer = MediaPlayer.create(this, R.raw.sound_laser);


        setContentView(R.layout.layout_game);

        Intent intent_starter = getIntent();
        boolean just_started = intent_starter.getBooleanExtra(StartActivity.NAME_JUST_STARTED, false);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout_game);
        RelativeLayout relativeLayout = (RelativeLayout) frameLayout.findViewById(R.id.layout_text_game);
        mTextScoreView=(TextView) relativeLayout.findViewById(R.id.scoreTextView);


        settings = getPreferences(MODE_PRIVATE);
        indexGame=0;
        getHighScore();
        try {
            mGame = new GameSquive(this, mHighScore);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mGameView = new GameView(this, mGame);


        frameLayout.addView(mGameView,0);// we're ready to add the game view now
        resetDied();
    }


    public void setScore(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextScoreView.setText(getString(R.string.stringScoreInGame,score));
            }
        });
        if (mHighScore < score)
            setHighScore(score);
    }


    public void playSoundWin() {
        mSoundWinPlayer.seekTo(0);
        mSoundWinPlayer.start();
    }
    public void playSoundDeath() {
        mSoundDeathPlayer.seekTo(0);
        mSoundDeathPlayer.start();
    }
    public void playSoundLaser() {
        mSoundLaserPlayer.seekTo(0);
        mSoundLaserPlayer.start();
    }


    public void resetDied() {
        //this fuction must be called if the game begin or if someone has hit "try again"

        mTextScoreView.setText(getString(R.string.stringScoreInGame,mHighScore));
        mGame.resetGameThreaded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.onPause();
        try {
            musicPlayer.release();
        } catch (Exception e) {
            Log.e("release music", e.toString());
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            mSoundDeathPlayer.release();
            mSoundWinPlayer.release();
            mSoundLaserPlayer.release();
        } catch (Exception e) {
            Log.e("release sounds", e.toString());
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGameView.onResume();
        try {
            musicPlayer = MediaPlayer.create(this, R.raw.music_game);
            musicPlayer.setLooping(true);
            musicPlayer.start(); // no need to call prepare(); create() does that for you
        } catch (Exception e) {
            Log.e("start music", e.toString());
            e.printStackTrace();
        }
    }

    private void getHighScore() {
        mHighScore = settings.getInt("HighScore" + Integer.toString(indexGame),0);
    }

    private void setHighScore(int score) {
        mHighScore = score;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HighScore" + Integer.toString(indexGame), mHighScore);
        editor.apply();
    }

    private int mHighScore;
    private int indexGame;
    private TextView mTextScoreView, mTextDiedView, mTextHighScoreView;
    private Button mRetryButton;
    private Button mChangeGameButton;
    private GameView mGameView;
    private SharedPreferences settings;
    private TheGame mGame;
}
