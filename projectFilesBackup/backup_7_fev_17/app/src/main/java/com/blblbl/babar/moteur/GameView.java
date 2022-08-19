package com.blblbl.forgotname.moteur;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by carotte on 21/05/16.
 * Puisque on est dans une vue, c'est ici qu'on va gérer les évènements (onTouchEvent)!
 */
public class GameView extends GLSurfaceView{
    public GameView(Context context, TheGame game) {
        super(context);
        setEGLContextClientVersion(2);
        mGame = game;
        mRenderer = new UltimateRenderer(context, game);
        setRenderer(mRenderer);
    }
//TODO permettre de bouger l'écran?
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return mGame.onTouchEvent(event);
    }

    private TheGame mGame;
    private UltimateRenderer mRenderer;//note: ne JAMAIS accéder directement au renderer, utiliser queueEvent
}
