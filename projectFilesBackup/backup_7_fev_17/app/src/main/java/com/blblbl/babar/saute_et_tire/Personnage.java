package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Personnage extends Drawable{
    public Personnage() {
        mSpeedY = 0;
        hasJumped= false;
        mFrame=0;
    }
    public void live() {
        mPosY += mSpeedY * GameTest.TIME;
        mSpeedY -= 15.81f * GameTest.TIME;
        if (mPosY <= Y_SOL) {
            hasJumped = false;
            mSpeedY = 0;
            mPosY = Y_SOL;
        }
        mFrame = (mFrame+1)%NUMBER_FRAMES;
        mTexBG_y = (float) (mFrame+1)/NUMBER_FRAMES;
        mTexHD_y = (float) mFrame/NUMBER_FRAMES;

    }

    public void jump() {
        if (mPosY == Y_SOL) {
            mSpeedY = 4f;
            return;
        }
        if (!hasJumped) {
            mSpeedY = 4f;
            hasJumped = true;
        }
    }

    public boolean can_shoot() {
        return true;
    }

    private int mFrame;
    private final int NUMBER_FRAMES=8;
    private boolean hasJumped = false;
    private float mSpeedY;
    private final float Y_SOL = -0.6f;
}
