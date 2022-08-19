package com.blblbl.forgotname.moteur;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
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

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(context, mGame);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // must use this to pass event on to Gesture object from scrollvoew n xml

        super.dispatchTouchEvent(ev);

        return this.mDetector.onTouchEvent(ev);
    }

    GestureDetectorCompat mDetector;
    private TheGame mGame;
    private UltimateRenderer mRenderer;//note: ne JAMAIS accéder directement au renderer, utiliser queueEvent
}
