package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

import java.util.Random;

/**
 * Created by carotte on 22/10/16.
 */
public class Personnage extends Drawable{
    Personnage(Graphe graphe) {
        mSpeedY = 0;
        mSpeedX = 0.1f;
        mCircleSize = 0.25f;
        graphe_ref= graphe;
        sommet_graphe = (new Random()).nextInt() % graphe_ref.getNbSommets();

        Vect pos_screen = graphe_ref.getSommet(sommet_graphe).getVect();
        float taille_screen_x = 0.2f;
        float taille_screen_y = 0.3f;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = 1f/8f;
        float HD_tex_x = 1, HD_tex_y = 0;
        int index_texture = 0;
        float rotation = 0;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                pos_screen.getmX(), pos_screen.getmY(), rotation, index_texture);
    }

    protected Graphe graphe_ref;
    protected int sommet_graphe;
    protected boolean is_moving;
    protected int new_sommet_graphe; // is is_moving=true!
    protected float time_since_move;
    protected float time_to_move; // start with value like 500ms and decreases

    private int mFrame=0;
    private float mSpeedX;
    private float mSpeedY;
    private float mCircleSize;
}
