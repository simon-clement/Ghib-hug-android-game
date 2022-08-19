package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Vect;

import java.util.Random;

/**
 * Created by carotte on 07/02/17.
 */

public class Hero extends Personnage {
    public Hero(Graphe graphe) {
        super(graphe, 0.48f, 0.156f, 0.30f,
                graphe.getNbSommets()-1, 5, 90);
        NUMBER_FRAMES = 4;
        setAnimationRepos(0);
    }
}
