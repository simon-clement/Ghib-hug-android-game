package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

public class Point extends Drawable {
    public Point(Vect position) {
        float taille_screen_x = 0.1f;
        float taille_screen_y = 0.1f;
        float BG_tex_x = 0, BG_tex_y = 0.45f;
        float HD_tex_x = 1, HD_tex_y = 0;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                position.getmX(), position.getmY(), 0);
    }
}
