package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by carotte on 28/01/17.
 */

public class Graphe {
    private final static String TAG = "Graphe in squive";
    public final static int SCORE_TO_SECOND_LEVEL = 6;
    private Sommet[] mSommets;
    private int[][] mArcs;
    private float[][] mAngles; //tableau pré-calculé d'angles!
    //overrive graphe constructor and function "sommets_alignes" to create a new level
    private Drawable[] mDrawables;
    private boolean[][] mMatriceTir;
    private int sommet_gap;

    private int index_drawable;
    private int index_sommet;

    public void addAlignementTirable(int... sommets) {
        for (int i = 0; i< sommets.length; ++i) {
            for (int u=i+1; u<sommets.length; ++u) {
                add_arrete_tirable(sommets[i], sommets[u]);
            }
        }
    }

    public void addChaineTirable(int... sommets) {
        for (int i=1; i< sommets.length; ++i)
            add_arrete_tirable(sommets[i-1], sommets[i]);
    }

    public void addChaineArretes(int... sommets) throws Exception {
        for (int i=1; i< sommets.length; ++i)
            addArrete(sommets[i-1], sommets[i]);
    }


    public void addSommet(int degre, Vect position) {
        mDrawables[index_drawable++] = new Point(position);
        //if (degre != 0) {
            mSommets[index_sommet] = new Sommet(position.getmX(), position.getmY());
            mArcs[index_sommet] = new int[degre];
            for (int i = 0; i < degre; ++i) {
                mArcs[index_sommet][i] = -1;
            }
        //}
        ++index_sommet;
    }

    public void addSommet(int degre, float positionX, float positionY) {
        addSommet(degre, new Vect(positionX, positionY));
    }

    public void add_arrete_tirable(int sommet1, int sommet2) {
        mMatriceTir[sommet1][sommet2] = true;
        mMatriceTir[sommet2][sommet1] = true;
    }

    public void addArrete(int sommet1, int sommet2) throws Exception{
        mMatriceTir[sommet1][sommet2] = true;
        mMatriceTir[sommet2][sommet1] = true;
        mDrawables[index_drawable++] = new Trait(mSommets[sommet1].getVect(), mSommets[sommet2].getVect());
        boolean arrete1_ajoutee = false;
        boolean arrete2_ajoutee = false;
        for (int i=0; i<mArcs[sommet1].length; ++i)
            if (mArcs[sommet1][i] == -1) {
                mArcs[sommet1][i] = sommet2;
                arrete1_ajoutee = true;
                break;
            }
        for (int i=0; i< mArcs[sommet2].length; ++i)
            if (mArcs[sommet2][i] == -1) {
                mArcs[sommet2][i] = sommet1;
                arrete2_ajoutee = true;
                break;
            }
        if (!arrete1_ajoutee || !arrete2_ajoutee)
            throw new Exception("ARRETE NE S'AJOUTE PAS" + Integer.toString(sommet1) + "->" + Integer.toString(sommet2));
    }

    public void setSommet_gap(int sommet) {
        sommet_gap = sommet;
    }

    public int getSommet_gap() {
        return sommet_gap;
    }

    public void load_angles() {
        mAngles = new float[mArcs.length][];
        for (int i=0; i<mAngles.length; ++i) {
            mAngles[i] = new float[mArcs[i].length];
            for (int u=0; u<mAngles[i].length; ++u) {
                    try {
                        mAngles[i][u] = mSommets[mArcs[i][u]].getVect().moins(mSommets[i].getVect()).to_angle();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                        Log.e(TAG, Integer.toString(i)+", -> " + Integer.toString(mArcs[i][u]));
                    }
            }
        }
    }


    public Drawable[] getmDrawables() {
        return mDrawables;
    }


    public Graphe(int nombre_Sommets, int nombre_arretes){//fonction de débuggage (faudrait qu'elle marche aussi ^^')
        index_drawable = 0;
        index_sommet = 0;

        mMatriceTir = new boolean[nombre_Sommets][nombre_Sommets];
        for (boolean[] tab : mMatriceTir)
            Arrays.fill(tab, false);

        mDrawables = new Drawable[nombre_arretes + nombre_Sommets];
        mSommets = new Sommet[nombre_Sommets];
        mArcs = new int[nombre_Sommets][];
    }

    public boolean fully_loaded(int number_drawable) {
        return number_drawable == index_drawable;
    }

    public int getNbDrawable() {
        return index_drawable;
    }

    public boolean sommets_alignes(int sommet1, int sommet2) {
        return mMatriceTir[sommet1][sommet2];
    }



    public float getAngle(int sommet1, int sommet2) {
        return mSommets[sommet2].getVect().moins(mSommets[sommet1].getVect()).to_angle();
    }

    public int[] parse_dir(int sommet, Vect direction) {
        /* renvoie le sommet vers lequel pointe direction */
        float angle = direction.to_angle();
        //Log.i(TAG, "angle: " + Float.toString(angle) +", vect: " + Float.toString(direction.getmX()) + ", "+ Float.toString(direction.getmY()));
        float min = 2* (float)Math.PI; // c'est un peu l'infini en soi :)
        int index_min = -1;
        for (int i=0; i<mAngles[sommet].length; ++i) {
            float dist_abs = Math.abs(angle - mAngles[sommet][i]);
            float distance = Math.min(dist_abs, 2*(float)Math.PI - dist_abs);
            if (distance < min) {
                min = distance;
                index_min = i;
            }
        }
        int test[] = {mArcs[sommet][index_min],0};
        if (min > 1f) {
            test[1] = -1;
        }
        //Log.d(TAG, "informations sur la direction choisie");
        //Log.i(TAG, "delta_angle="+Float.toString(min)+", angle:"+mAngles[sommet][index_min] + ", sommets:"+Integer.toString(sommet)+"->"+
          //                                      Integer.toString(mArcs[sommet][index_min]));
        return test;
    }

    public int getNbSommets() {
        return mSommets.length;
    }

    public Sommet getSommet(int position) {
        return mSommets[position];
    }

    public int getNbFils(int sommet) {
        return mArcs[sommet].length;
    }

    public int getFils(int sommet, int num_fils) {
        return mArcs[sommet][num_fils];
    }

    public Vect getDirection(int index_sommet, int index_arc) {
        try {
            return mSommets[mArcs[index_sommet][index_arc]].getVect().moins(mSommets[index_sommet].getVect());
        } catch (Exception e) {
            Log.e(TAG, "sommet: " + Integer.toString(index_sommet));
            e.printStackTrace();
            return new Vect(0,0);
        }
    }

    public Vect[] getDirections(int index_sommet) throws Exception {
        if (index_sommet >= mArcs.length)
            throw new Exception("index sommet out of range");
        int[] arcs = mArcs[index_sommet];
        Vect[] ret = new Vect[arcs.length];
        Vect pos = mSommets[index_sommet].getVect();
        for (int i=0; i<arcs.length; ++i) {
            ret[i] = mSommets[arcs[i]].getVect().moins(pos);
        }
        return ret;
    }

    public int[] chemin_random(int sommet_depart, int goal) {
        /* renvoie un des plus courts chemins entre sommet_depart et goal
        on prend le tableau des voisins de chaque sommet en cours de visionnage,
        pour chaque sommet, on stocke donc les parents directs
         */
        if (sommets_alignes(sommet_depart, goal)) {
            int ret[] = new int[1];
            ret[0] = sommet_depart;
            return ret;
        }


        int visited[] = new int[mSommets.length];
        Arrays.fill(visited, -1);

        visited[sommet_depart] = 0;
        int sommet_atteint = -1;
        int last_voisins[] = new int[mSommets.length];
        int new_voisins[] = new int[mSommets.length];
        new_voisins[0] = sommet_depart;
        new_voisins[1] = -1; // He! Oublie pas de mettre au moins 2 sommets !
        float max_dist_sommet = -1;
        int rang = 1;
        while (sommet_atteint == -1) {
            int tmp_voisins[] = last_voisins;
            last_voisins = new_voisins;
            new_voisins = tmp_voisins;
            int index_new_voisins = 0;
            for (int i=0; i< mSommets.length && last_voisins[i] != -1; ++i) {
                for (int j=0; j<mArcs[last_voisins[i]].length; ++j) {
                    if (visited[mArcs[last_voisins[i]][j]] == -1) {
                        visited[mArcs[last_voisins[i]][j]] = rang;
                        new_voisins[index_new_voisins] = mArcs[last_voisins[i]][j];
                        if (sommets_alignes(mArcs[last_voisins[i]][j], goal)) {
                            if (distance_euclidienne(mArcs[last_voisins[i]][j], goal) > max_dist_sommet) {
                                sommet_atteint = index_new_voisins;
                                max_dist_sommet = distance_euclidienne(mArcs[last_voisins[i]][j], goal);
                            }
                        }
                        ++index_new_voisins;
                    }
                }
            }
            new_voisins[index_new_voisins] = -1;
            ++rang;
        }
        Random rand = new Random();
        int ret[] = new int[rang];
        ret[rang-1] = new_voisins[sommet_atteint];
        ret[0] = sommet_depart;
        int tab[] = new int[mSommets.length];
        for (int i=rang - 2; i>0; --i) {
            int index_tab = 0;
            for (int voisin:mArcs[ret[i+1]]) {
                if (visited[voisin] == i) { // si le voisin est du bon rang
                    tab[index_tab++] = voisin;
                }
            }
            ret[i] = tab[rand.nextInt(index_tab)]; // et on remplit ret avec la solution trouvée
        }

        return ret;
    }

    private float distance_euclidienne(int sommet1, int sommet2) {
        return mSommets[sommet1].getVect().moins(mSommets[sommet2].getVect()).norme_carree();
    }


    public int what_son(int sommet1, int sommet_fils) {
        for (int i=0; i<mArcs[sommet1].length; ++i) {
            if (mArcs[sommet1][i] == sommet_fils) {
                return i;
            }
        }
        return -1;
    }


}
