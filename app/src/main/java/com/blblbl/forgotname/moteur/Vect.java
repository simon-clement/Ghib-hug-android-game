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
    public Vect(Vect b) {
        mX = b.mX;
        mY = b.mY;
    }
    public float getmX() { return mX; }
    public float getmY() { return mY; }
    public void setmX(float x) { mX = x;}
    public void setmY(float y) { mY = y; }
    public Vect moins(Vect b) {return new Vect(mX - b.mX, mY - b.mY);}
    public float norme() {
        return (float) Math.sqrt(norme_carree());
    }
    public float norme_carree() {
        return dot(this);
    }
    public float to_angle() {
        /*pour avoir l'angle à partir de notre vecteur:
        le plus simple est d'utiliser la fonction x->2atan(2Y/(norme+x))
        RETOURNE EN RADIANS
         */
        if (mY == 0) {
            return (mX < 0)? (float)Math.PI : 0;
        }
        return 2 * (float) Math.atan(mY / (mX + norme()));
    }

    public float dot(Vect b) {return mX*b.mX + mY*b.mY;}


    public Vect by(float a) { return new Vect(a*mX, a*mY); }
    public void add(Vect b) {
        mX += b.mX;
        mY += b.mY;
    }
    public Vect plus(Vect b) { return new Vect(mX + b.mX, mY + b.mY);}

    public static String toString(Vect a) {
        return "(" + a.mX + ", " + a.mY + ")";
    }

}
