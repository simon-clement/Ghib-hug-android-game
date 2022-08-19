package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Vect;

/**
 * Created by carotte on 28/01/17.
 */

public class Graphe {
    private Sommet[] mSommets;
    private int[][] mArcs;

    public Graphe(Sommet[] sommets, int[][] arcs) {
        mSommets = sommets;
        mArcs = arcs;
    }

    public Graphe() {//fonction de débuggage (faudrait qu'elle marche aussi ^^')
        mSommets = new Sommet[4];
        mSommets[0] = new Sommet(-0.5f, -0.5f);
        mSommets[1] = new Sommet(-0.5f, -0.5f);
        mSommets[2] = new Sommet(-0.5f, -0.5f);
        mSommets[3] = new Sommet(-0.5f, -0.5f);
        mArcs = new int[3][4];
        for (int i=0; i<4; ++i) {
            int v=0;
            for (int u=0; u<4; ++u) {
                if (u != i) {
                    mArcs[i][v] = u;
                }
                v+=1;
            }
        }
        //Ca forme un carré ou tous les points sont accessibles de partout!
    }

    public int getNbSommets() {
        return mSommets.length;
    }

    public Sommet getSommet(int position) {
        return mSommets[position];
    }

    public Vect[] getDirections(int index_sommet) throws Exception {
        if (index_sommet >= mArcs.length)
            throw new Exception("index sommet out of range");
        int[] arcs = mArcs[index_sommet];
        Vect[] ret = new Vect[arcs.length];
        Vect pos = mSommets[index_sommet].getVect();
        for (int i=0; i<arcs.length; ++i) {
            ret[i] = pos.moins(mSommets[arcs[i]].getVect());
        }
        return ret;
    }


}
