package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Ennemi extends Drawable{
    Ennemi() {
        mValide = false;
        mFrame=0;
    }
    public void validate(float speed_tank) {
        mSpeed = speed_tank/1.5f;
        mValide = true;
        setDrawableAttribs(0.2f,0.2f,0,0.3f,1f,0, 1.3f,-0.1f, 0, 3);
    }

    public void die(){
        mValide = false;
    }

    public void live() {
        mPosX -= mSpeed * GameTest.TIME;
        if (mPosX < -1.2f)
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