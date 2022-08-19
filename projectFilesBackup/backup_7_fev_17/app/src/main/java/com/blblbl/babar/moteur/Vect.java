package com.blblbl.forgotname.moteur;

/**
 * Created by carotte on 28/01/17.
 */

public class Vect {
    private float mX, mY;
    public Vect(float x, float y) {
        mX = x;
        mY = y;
    }
    public float getmX() { return mX; }
    public float getmY() { return mY; }
    public void setmX(float x) { mX = x;}
    public void setmY(float y) { mY = y; }
    public Vect moins(Vect b) {return new Vect(mX - b.mX, mY - b.mY);}
}
