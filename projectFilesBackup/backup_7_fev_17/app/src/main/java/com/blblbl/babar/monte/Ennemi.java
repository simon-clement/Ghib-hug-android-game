package com.blblbl.forgotname.monte;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 23/10/16.
 */
public class Ennemi extends Drawable {
    Ennemi() {
        mValide = false;
        mFrame=0;
    }
    public void validate(float random, float speed) {
        mSpeed = speed;
        mValide = true;
        setDrawableAttribs(0.2f,0.2f,0,0.3f,1f,0, random,-1.1f, 0, 3);
    }

    public void die(){
        mValide = false;
    }

    public void live(float diff) {
        mPosY += mSpeed * GameMonte.TIME - diff;
        if (mPosY > 1.2f || mPosY < -2.5f)
            mValide = false;
        mFrame = (mFrame + 1) % NUMBER_FRAMES;
        mTexHD_y = (float)mFrame / NUMBER_FRAMES;
        mTexBG_y = (float)(mFrame+1) / NUMBER_FRAMES;

    }

    public boolean getValid() {
        return mValide;

    }
    private int mFrame;
    private final int NUMBER_FRAMES=3;
    private boolean mValide;
    private float mSpeed;
}
