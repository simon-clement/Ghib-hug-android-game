package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Sol extends Drawable {
    Sol(float speed_tank) {
        setDrawableAttribs(10,1,-5,1,5,0, 0,-1.25f, 0, 2);
        mSpeed = speed_tank;
    }

    public void live() {
        mPosX -= mSpeed * GameTest.TIME;
        if (mPosX < -4)
            mPosX +=4;

    }

    private float mSpeed;
}
