package com.blblbl.forgotname.moteur;

/**
 * Created by carotte on 22/10/16.
 */
public abstract class Drawable {

    public void setDrawableAttribs(float taille_x, float taille_y,
                                  float bas_gauche_tex_x, float bas_gauche_tex_y,
                                  float haut_droit_tex_x, float haut_droit_tex_y,
                                  float pos_x, float pos_y,
                                  float angle) {
        mSquareSizeX = taille_x;
        mSquareSizeY = taille_y;
        mTexBG_x = bas_gauche_tex_x;
        mTexBG_y = bas_gauche_tex_y;
        mTexHD_x = haut_droit_tex_x;
        mTexHD_y = haut_droit_tex_y;
        mPosX = pos_x;
        mPosY = pos_y;
        mAngle = angle;
    }

    protected void setPos(float x, float y) {
        mPosX = x;
        mPosY = y;
    }

    protected void setTexCoord_y(float bg_y, float hd_y) {
        mTexBG_y = bg_y;
        mTexHD_y = hd_y;
    }

    protected void setAngle(float angle) { mAngle = angle; }

    protected void setSize(float sizeX, float sizeY) {
        mSquareSizeX = sizeX;
        mSquareSizeY = sizeY;
    }

    public float getSizeX() {
        return mSquareSizeX;
    }
    public float getSizeY() {
        return mSquareSizeY;
    }
    public float getTexBG_x() {
        return mTexBG_x;
    }
    public float getTexBG_y() {
        return mTexBG_y;
    }
    public float getTexHD_x() {
        return mTexHD_x;
    }
    public float getTexHD_y() {
        return mTexHD_y;
    }
    public float getPosX() {
        return mPosX;
    }
    public float getPosY() {
        return mPosY;
    }
    public float getAngle() {
        return mAngle;
    }
    private float mSquareSizeX, mSquareSizeY, mTexBG_x, mTexBG_y, mTexHD_x, mTexHD_y,
            mAngle, mPosX, mPosY;
    protected static final String TAG="Drawable";
}
