package com.blblbl.forgotname.moteur;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class UltimateRenderer implements GLSurfaceView.Renderer {
    public UltimateRenderer(Context Activity, TheGame game) {
        mAct = Activity;
        mAfficheur = new Afficheur(mAct);
        mGame = game;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        mAfficheur.prepareForDraw();
        //mAfficheur.clear();
        mGame.hyperCallback();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mGame.setAfficheur(mAfficheur);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width  / height;
        mGame.setResolution(width, height);
        mAfficheur.setRatio(ratio); // besoin de ça, si on veut pas que le jeu soit tout étiré (attention à la hauteur du coup)
//fixme it's hard to use ratio when we put some rotations :/
    }
    private TheGame mGame;
    private Afficheur mAfficheur;
    private Context mAct;//note: since we are in a different thread, don't use mAct directly
}
