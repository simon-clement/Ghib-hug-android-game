package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Vect;

/**
 * Created by carotte on 28/01/17.
 */

class Sommet {
    private final Vect mPos;//pour l'instant c'est qu'un vect mais ça pourrait changer...

    public Sommet(float x, float y) {
        mPos = new Vect(x, y);
    }

    public Vect getVect() {
        return mPos;
    }
}
