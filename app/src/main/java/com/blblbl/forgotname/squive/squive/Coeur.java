package com.blblbl.forgotname.squive.squive;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

import java.util.Random;


public class Coeur extends Drawable {
    Coeur(Random random) {
        float texture = random.nextInt(3);
        float taille_screen_x = cote_carre;
        float taille_screen_y = cote_carre;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = (texture+1)/3f;// 1/8f;?
        float HD_tex_x = 1, HD_tex_y = texture/3f;
        ;
        int index_texture = 0;
        mDirection = new Vect((random.nextFloat()-0.5f)/2f, (random.nextFloat()-0.5f)/2f);
        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                0, 0, 70 + 40 * random.nextFloat());
    }

    public void live(Random random, boolean rester, float ratio_taille) {
        setPos(getPosX() + ratio_taille * mDirection.getmX(), getPosY() + ratio_taille * mDirection.getmY());
        float taille = Math.min(cote_carre, cote_carre * ratio_taille);
        setSize(taille, taille);
        if ((Math.abs(getPosX()) > 2 || Math.abs(getPosY()) > 2) && rester)
            setPos((random.nextFloat()-0.5f)/5f, (random.nextFloat()-0.5f)/5f);
    }

    private Vect mDirection;
    private final static float cote_carre = 0.9f;
}
