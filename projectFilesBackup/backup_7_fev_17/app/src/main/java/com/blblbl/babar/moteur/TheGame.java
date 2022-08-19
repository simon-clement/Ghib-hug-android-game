package com.blblbl.forgotname.moteur;

import android.view.MotionEvent;

import com.blblbl.forgotname.MainActivity;

/**
 * Created by carotte on 22/10/16.
 */
public abstract class TheGame {
    public TheGame(MainActivity act) {
        mLost = false;
        mAct =act;
        mToResetBeforeCallback=false;
    }

    /**
     * Here are the section with the functions that need to be implemented.
     * when you extends TheGame, you have only 4 functions to declare:
     * resetGame(), the initializer,
     * onTouchEvent, to communicate with user
     * loadTexture, to... Load textures?
     * callback(), that is called every frame.
     */


    /**
     * resetGame() must initialize a TheGame and put all variables to a start state.
     * This function will be used when the game starts or restart
     */
    public abstract void resetGame();

    /**
     * onTouchEvent, where you detect touchs (ACTION_DOWN, ACTION_POINTER_DOWN...)
     * @param event, provided by a View
     * @return true
     */
    public abstract boolean onTouchEvent(MotionEvent event);
        /*example: if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //do something with event.getX()// event.getY()
        }
        return true
        */

    /**
     * loadTexture is called at the end of the initialization,
     * must use setTexture(Int[] textures) to load all the textures.
     */
    protected abstract void loadTextures();
    //use setTextures here
    //ex: setTextures(new int[]{R.raw.texsurfaces});

    /**
     * callback() is called every frame. this is where you call "draw"
     */
    protected abstract void callback(); //called each frame


    /**
     * Here are the function that are not to implement but which are used by activities and view
     */

    /**
     * call resetGame() in the rendering thread
     */
    public void resetGameThreaded() {
        mToResetBeforeCallback = true;
    }


    /**
     * called by the renderer to init the rendering profile.
     * MUST NOT BE CALLED BEFORE onSurfaceCreated()!!
     * @param afficheur
     */
    public void setAfficheur(Afficheur afficheur) {
        mAfficheur = afficheur;
        afficheur.handlePrograms();
        loadTextures();
        mAfficheur.endInit();
    }

    /**
     * called by the renderer to give resolution
     * @param w number of pixels in a row
     * @param h number of pixels in a column
     */
    public void setResolution(float w, float h) {
        mLargeur = w;
        mHauteur = h;
    }

    /**
     * called by the renderer in onDrawFrame():
     * permit to do things in the rendering thread, without touch to callback.
     */
    public void hyperCallback() {
        if (mToResetBeforeCallback) {
            resetGame();
            mToResetBeforeCallback=false;
        }
        callback();
    }

    /**
     * prepare the textures in a tab to be used.
     * @param textures the textures (like R.drawable.my_texture)
     */
    protected void setTextures(int[] textures) {
        //please don't override
        mAfficheur.LoadTextures(textures.length, textures);
    }

    /**
     * draw the same drawable object, but with a different position (not used in practice)
     * @param d
     */
    protected void drawSameObj(Drawable d) { //should be more used :'(
        mAfficheur.setDrawableAttribs(d.getPosX(), d.getPosY(), d.getSizeX(), d.getSizeY(), d.getAngle());
        mAfficheur.DrawRectangle();
    }

    /**
     * set the texture idTex to be used with draw().
     * The id is the index in the array provided in setTextures(int[] textures)
     * @param idTex the index in the setTexture() array
     */
    protected void setDefaultTex(int idTex) {
        mAfficheur.setTexture(idTex);
    }

    /**
     * draw the drawable d, but WARNING! it is with the default texture: this makes the drawable object texture useless^^
     * @param d
     */
    protected void draw(Drawable d) {
        mAfficheur.setDrawableAttribs(d.getPosX(), d.getPosY(), d.getSizeX(), d.getSizeY(), d.getAngle());
        mAfficheur.setTexCoords(d.getTexBG_x(), d.getTexBG_y(), d.getTexHD_x(), d.getTexHD_y());
        mAfficheur.DrawRectangle();
    }

    /**
     * use this when the user win a point, and it will be printed (important for highscore too)
     */
    protected void setScore() {
        mAct.setScore(mScore);
    }

    /**
     * call this when the user loose :)
     */
    protected void setDied() {
        mAct.setDied(mScore);
    }


    private boolean mToResetBeforeCallback;
    protected int mScore;
    protected Afficheur mAfficheur;
    protected float mLargeur, mHauteur;
    private MainActivity mAct;
    protected boolean mLost;
}
