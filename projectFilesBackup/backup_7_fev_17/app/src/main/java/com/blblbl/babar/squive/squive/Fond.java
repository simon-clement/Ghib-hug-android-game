package com.blblbl.forgotname.squive.squive;


import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Fond extends Drawable {
    public Fond() {
        setDrawableAttribs(2, 6, 0, 3, 1, 0, 0, 0, 0, 2);
    }

    public void move(float diff) {
        mPosY -= diff/2;
        if (mPosY < -2) {
            mPosY += 4;
        }
    }
}
