package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;


public class Laser extends Drawable{
    Laser() {
        float taille_screen_x = 0.2f;
        float taille_screen_y = 0.3f;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = 1f;// 1/8f;?
        float HD_tex_x = 1, HD_tex_y = 0;
        is_active = false;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                0, 0, 0);
    }

    public boolean touche(Personnage perso, Graphe graphe) {
        if (!is_active)
            return false;
        float taille_cercle = perso.getmCircleSize()/2f;
        Vect pos_perso = perso.get_exact_position();

        Vect vecteur_laser = mArrivee.moins(mDepart);
        Vect vecteur_dep_to_perso = pos_perso.moins(mDepart);
        Vect projection = mDepart.plus(vecteur_laser.by(vecteur_dep_to_perso.dot(vecteur_laser)/vecteur_laser.norme_carree()));
        if (mArrivee.moins(projection).dot(mDepart.moins(projection)) > 0.1) { //Ça devrai être 0 mais on laisse un marge d'erreur
            return false;
        }
        return projection.moins(pos_perso).norme_carree() < taille_cercle * taille_cercle;
    }

    public void setLaser(Graphe graphe, int sommet_depart, int sommet_arrivee) {
        is_active = true;

        mDepart = graphe.getSommet(sommet_depart).getVect();
        mArrivee =graphe.getSommet(sommet_arrivee).getVect();//attention, ce sont des références...
        Vect vecteur = mArrivee.moins(mDepart);
        float norme_vect=vecteur.norme();
        Vect position = mDepart.plus(vecteur.by(0.5f + 0.15f/norme_vect));
        float rotation = 90+(float)Math.toDegrees(vecteur.to_angle());
        float taille_screen_x = vecteur.norme()+0.24f;
        float taille_screen_y = 0.3f;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = 1;
        float HD_tex_x = 1, HD_tex_y = 0;
        int index_texture = 0;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                position.getmX(), position.getmY(), rotation);
    }

    public void resetLaser() {
        is_active = false;
    }

    public boolean get_is_active() {
        return is_active;
    }

    private boolean is_active;
    private Vect mDepart, mArrivee;
    private final static String TAG="Laser";
}
