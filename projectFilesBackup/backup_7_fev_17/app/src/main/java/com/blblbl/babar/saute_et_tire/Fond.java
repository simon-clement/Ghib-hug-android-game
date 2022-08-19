package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Fond extends Drawable {
    public Fond(float speed_tank) {
        mSpeed=speed_tank/5;
        setDrawableAttribs(6, 2, 0, 1, 3, 0, 0, 0, 0, 1);
    }

    public void live() {
        mPosX -= mSpeed * GameTest.TIME;
        if (mPosX < -2)
            mPosX += 2;
    }

    private float mSpeed;


}
