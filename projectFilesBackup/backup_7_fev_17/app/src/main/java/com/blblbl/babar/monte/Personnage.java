package com.blblbl.forgotname.monte;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 22/10/16.
 */
public class Personnage extends Drawable{
    Personnage() {
        mSpeedY = 0;
        mSpeedX = 0.1f;
        mCircleSize = 0.25f;
        setDrawableAttribs(0.2f,0.3f,0,1f/8f,1,0,-0.5f,0.3f,0,0);

    }

    public float live(Trampo[] trampos) {//renvoie le déplacement de l'écran vers le haut!
        mTexBG_y = (float)(mFrame+1)/8f;
        mTexHD_y = (float)(mFrame)/8f;
        mFrame = (mFrame+1)%8;


        float speed = (float) Math.sqrt(mSpeedX * mSpeedX + mSpeedY*mSpeedY);
        float cosDeplacement= (mSpeedX / speed) * mCircleSize/2;
        float sinDeplacement = (mSpeedY / speed) * mCircleSize/2;
        float pos1X= mPosX;
        float pos1Y= mPosY;
        float pos2X = mPosX + mSpeedX * GameMonte.TIME + cosDeplacement;
        float pos2Y = mPosY + mSpeedY * GameMonte.TIME + sinDeplacement;


        for (Trampo trampo:trampos) {
            if (trampo == null)
                continue;
            float vec_tramp_x = (float) Math.cos(trampo.getAngle() * 3.1415/180) * (trampo.getSizeX()/2 + mSquareSizeX/2);
            float vec_tramp_y = (float) Math.sin(trampo.getAngle() * 3.1415/180) * (trampo.getSizeX()/2 + mSquareSizeX/2);
            float pt_A_x = trampo.getPosX() + vec_tramp_x;
            float pt_A_y = trampo.getPosY() + vec_tramp_y;
            float pt_B_x = trampo.getPosX() - vec_tramp_x;
            float pt_B_y = trampo.getPosY() - vec_tramp_y;
            if (inter_segment(pos1X, pos1Y, pos2X, pos2Y, pt_A_x, pt_A_y, pt_B_x, pt_B_y)) {
                rebondir(trampo.getAngle(), 0.5f +0.4f/(float)Math.sqrt(trampo.size()));
                break;
            }
        }

        float future_posX = mPosX + mSpeedX * GameMonte.TIME;
        float future_posY = mPosY + mSpeedY * GameMonte.TIME;

        mPosX = future_posX;
        mPosY = future_posY;
        if (mPosX < -1)
            mPosX +=2;
        else if (mPosX > 1)
            mPosX -= 2;

        mSpeedY -= 0.981f * GameMonte.TIME;
        mSpeedX /= 1.01f;
        mSpeedY /= 1.001f;

        float diff = mPosY - 0.4f;
        if (diff > 0) {
            mPosY = 0.4f;
            return diff;
        }
        return 0;
    }

    private void rebondir(float angle, float force) {
        float angleSpeed, speed = (float) Math.sqrt(mSpeedY*mSpeedY + mSpeedX*mSpeedX);

        if (mSpeedY == 0 && mSpeedX <= 0) {
            angleSpeed = 3.1415f;
        } else
            angleSpeed = 2* (float) Math.atan(mSpeedY/(mSpeedX + speed));
        float newAngle = 2*angle*3.1415f/180 - angleSpeed;
        mSpeedX = (float)Math.cos(newAngle)* force* speed;
        mSpeedY = (float)Math.sin(newAngle)* force* speed;
    }

    private float dot(float vec1X, float vec1Y, float vec2X,float vec2Y) {
        return vec1X*vec2X + vec1Y*vec2Y;
    }

    private boolean inter_segment(float pt1X, float pt1Y, float pt2X, float pt2Y,
                              float pt3X, float pt3Y, float pt4X, float pt4Y) {
        float abX=pt2X - pt1X;
        float abY = pt2Y - pt1Y;
        float cdX=pt4X-pt3X;
        float cdY=pt4Y-pt3Y;/*
        Log.e("A", Float.toString(pt1X) + ",  " + Float.toString(pt1Y));
        Log.e("3->1", Float.toString(pt1X-pt3X) + ",  " + Float.toString(pt1Y-pt3Y));

        Log.e("taggy", Float.toString(dot(0, -1, pt1X-pt3X, pt1Y-pt3Y)) + "  :  " +Float.toString(dot(-cdY, cdX, pt2X-pt3X, pt2Y-pt3Y)));*/
        return (dot(-abY, abX, pt3X-pt1X, pt3Y-pt1Y) * dot(-abY, abX, pt4X-pt1X, pt4Y-pt1Y) <= 0  &&
                 dot(-cdY, cdX, pt1X-pt3X, pt1Y-pt3Y) * dot(-cdY, cdX, pt2X-pt3X, pt2Y-pt3Y) <=0);
    }

    private int mFrame=0;
    private float mSpeedX;
    private float mSpeedY;
    private float mCircleSize;
}
