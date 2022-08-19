package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Drawable;

/**
 * Created by carotte on 23/02/17.
 */

public class ChargeLaser extends Drawable {
    ChargeLaser(long time_loading) {
        time_to_load = time_loading;
        is_active = false;
    }

    public void activate(Personnage perso) {
        started = System.currentTimeMillis();
        is_active = true;
        charge = 0;
        posX_gauche = perso.get_exact_position().getmX() - 0.12f;
        posY = perso.get_exact_position().getmY() - 0.3f * Math.signum(perso.get_exact_position().getmY());
        live();
    }

    public void desactivate() {
        is_active = false;
    }

    public boolean full_loaded() {
        return is_active && charge == 1;
    }

    public boolean get_is_active() {
        return is_active;
    }

    public void live() {
        if (is_active) {
            charge = Math.min(1, (float)(System.currentTimeMillis() - started)/time_to_load);
            float taille_screen_x = 0.25f * charge;
            float taille_screen_y = 0.08f;//on déclare tout pour pouvoir tout changer facilement
            float BG_tex_x = 0, BG_tex_y = 1f;
            float HD_tex_x = 1, HD_tex_y = 0;

            setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                    posX_gauche + taille_screen_x/2f, posY, -90);
        }
    }

    public void box_mode() { // call it between 2 call to draw()
        float taille_screen_x = 0.25f;
        float taille_screen_y = 0.08f;//on déclare tout pour pouvoir tout changer facilement
        float BG_tex_x = 0, BG_tex_y = 1f;
        float HD_tex_x = 1, HD_tex_y = 0;

        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                posX_gauche + taille_screen_x/2f, posY, -90);
    }

    private final float MAX_SIZE = 0.25f;
    private float posX_gauche, posY;
    private float charge;
    private long started;
    private float time_to_load;
    private boolean is_active;
}
