package com.blblbl.forgotname.monte;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Trampo extends Drawable {
    Trampo(float xBase, float yBase) {
        mFixedY = yBase;
        mFixed = false;
        mValid = false;
        mFixedX = xBase;
        setMovingPoint(xBase, yBase);
    }

    public void move(float diff) {
        mPosY -= diff;
        if (mPosY - mSquareSizeX < -1)
            mValid = false;
    }

    public void moveFixedPoint(float diff) {
        if (!mFixed) {
            mFixedY -= diff;
            setMovingPoint(mMovingX, mMovingY);
        }
    }


    public void setMovingPoint(float xPoint, float yPoint){
        mMovingY = yPoint;
        mMovingX = xPoint;
        float taille= (float)Math.sqrt(size());
        float angle;
        if (mMovingY == mFixedY && mMovingX <= mFixedX)
            angle = 3.141527f;
        else
            angle = 2 * (float) Math.atan((mMovingY - mFixedY) / ((mMovingX - mFixedX) + taille));
        setDrawableAttribs(taille, 0.05f, 0, 1, 1, 0,
                (mMovingX + mFixedX) / 2, (mMovingY + mFixedY) / 2,angle*180/3.141527f, 1);
    }

    public float size() {
        return (mFixedX - mMovingX) * (mFixedX - mMovingX) +
                (mFixedY - mMovingY) * (mFixedY - mMovingY);
    }

    public void setNorm(float newSize) {
        float norme = (float) Math.sqrt(size());
        float vectX = (mMovingX - mFixedX)/norme;
        float vectY = (mMovingY - mFixedY)/norme;
        setMovingPoint(vectX*newSize + mFixedX, vectY*newSize + mFixedY);
    }


    public boolean getValid() {
        return mValid;
    }

    public void fix() {
        mFixed = true;
    }

    private float mFixedX, mFixedY, mMovingX, mMovingY;
    private boolean mFixed, mValid;
}
