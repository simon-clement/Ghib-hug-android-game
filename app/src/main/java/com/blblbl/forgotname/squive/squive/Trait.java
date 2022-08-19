package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

public class Trait extends Drawable {
    public Trait(Vect point1, Vect point2) {
        Vect vecteur = point2.moins(point1);
        Vect position = point1.plus(point2).by(0.5f);
        float rotation = 90+(float)Math.toDegrees(vecteur.to_angle());
        float taille_screen_x = vecteur.norme();
        float taille_screen_y = 0.03f;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = 1;
        float HD_tex_x = 1, HD_tex_y = 0.5f;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                position.getmX(), position.getmY(), rotation);
    }
}
