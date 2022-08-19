package com.blblbl.forgotname.saute_et_tire;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Mur extends Drawable {
        Mur() {
            mValide=false;
        }

        public void validate(float speed_tank) {
            mSpeed = speed_tank;
            mValide = true;
            setDrawableAttribs(0.05f,0.3f,0,0.3f,0.1f,0, 1,-0.6f, 0, 2);
        }

        public void live() {
            mPosX -= mSpeed * GameTest.TIME;
            if (mPosX < -1.2f)
                mValide = false;
        }

        public boolean getValid() {
            return mValide;

        }
        private boolean mValide;
        private float mSpeed;
}
