package com.blblbl.forgotname;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blblbl.forgotname.monte.GameMonte;
import com.blblbl.forgotname.saute_et_tire.GameTest;
import com.blblbl.forgotname.moteur.GameView;
import com.blblbl.forgotname.moteur.TheGame;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout_game);
        RelativeLayout relativeLayout = (RelativeLayout) frameLayout.findViewById(R.id.layout_text_game);
        mTextScoreView=(TextView) relativeLayout.findViewById(R.id.scoreTextView);
        mTextHighScoreView=(TextView) relativeLayout.findViewById(R.id.highScoreTextView);
        mTextDiedView= (TextView) relativeLayout.findViewById(R.id.diedTextView);
        mRetryButton= (Button) relativeLayout.findViewById(R.id.retryButton);
        mChangeGameButton= (Button) relativeLayout.findViewById(R.id.ChangeButton);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//we don't want the device to shutdown the screen

        settings = getPreferences(MODE_PRIVATE);
        indexGame=0;
        getHighScore();
        mGame = new GameMonte(this);

        mGameView = new GameView(this, mGame);

        frameLayout.addView(mGameView,0);//we're ready to add the game view now
        resetDied();
    }

    public void setScore(final int score) { //just prints the new score (doesn't change Physics)
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextScoreView.setText(getString(R.string.stringScoreInGame,score));
            }
        });
        if (mHighScore < score)
            setHighScore(score);
    }


    public void resetDied() {
        //this fuction must be called if the game begin or if someone has hit "try again"
  /*      runOnUiThread(new Runnable() {
            @Override
            public void run() {*/
                mRetryButton.setVisibility(View.GONE);
                mChangeGameButton.setVisibility(View.GONE);
                mTextDiedView.setVisibility(View.GONE);
                mTextHighScoreView.setText(getString(R.string.stringHighScoreInGame, mHighScore));
                mTextScoreView.setText(getString(R.string.stringScoreInGame,0));/*
            }
        });*/
        mGame.resetGameThreaded();
    }
    public void setDied(final int score) {
        //this function must be called when... THE BALL DIED! YOU DON'T SAY?!
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextDiedView.setVisibility(View.VISIBLE);
                mRetryButton.setVisibility(View.VISIBLE);
                mChangeGameButton.setVisibility(View.VISIBLE);
                mRetryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetDied();
                    }
                });
                mChangeGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexGame = 1 - indexGame;
                        if (indexGame == 1)
                            mGame = new GameTest(MainActivity.this);
                        else
                            mGame = new GameMonte(MainActivity.this);

                        getHighScore();
                        resetDied();

                        mGameView = new GameView(MainActivity.this, mGame);

                        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout_game);
                        frameLayout.removeViewAt(0);
                        frameLayout.addView(mGameView,0);
                    }
                });
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        mGameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameView.onResume();
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
