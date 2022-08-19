package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Balle extends Drawable{
    Balle () {
        mValid = false;
    }

    public void startWithSpeed(float speed) {
        mSpeedX = speed;
        mValid = true;
    }

    public void invalidate() {
        mValid = false;
    }

    public boolean getValid() {
        return mValid;
    }

    public void live() {
        mPosX += mSpeedX * GameTest.TIME;
        if (mPosX > 1.2f)
            mValid = false;
    }

    public boolean can_be_replaced() {
        return !mValid;
    }

    private float mSpeedX;
    private boolean mValid;

}
