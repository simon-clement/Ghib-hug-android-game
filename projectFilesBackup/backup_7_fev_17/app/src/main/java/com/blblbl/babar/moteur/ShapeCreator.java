package com.blblbl.forgotname.moteur;

import java.nio.FloatBuffer;

public class ShapeCreator {
    public static FloatBuffer makeRectangle(float bas_gauche_x, float bas_gauche_y,
                                            float haut_droit_x, float haut_droit_y) {
        return makeRectangle(bas_gauche_x, bas_gauche_y, haut_droit_x, haut_droit_y, 0, 1, 1, 0);
    }
    public static FloatBuffer makeRectangle(float bas_gauche_x, float bas_gauche_y,
                                            float haut_droit_x, float haut_droit_y,
                                            float bas_gauche_tex_x, float bas_gauche_tex_y,
                                            float haut_droit_tex_x, float haut_droit_tex_y) {
        float points[] = new float[]
                {bas_gauche_x, bas_gauche_y, 1, bas_gauche_tex_x, bas_gauche_tex_y,
                        haut_droit_x, bas_gauche_y, 1, haut_droit_tex_x, bas_gauche_tex_y,
                        bas_gauche_x, haut_droit_y, 1, bas_gauche_tex_x, haut_droit_tex_y,

                        haut_droit_x, haut_droit_y, 1, haut_droit_tex_x, haut_droit_tex_y,
                        bas_gauche_x, haut_droit_y, 1, bas_gauche_tex_x, haut_droit_tex_y,
                        haut_droit_x, bas_gauche_y, 1, haut_droit_tex_x, bas_gauche_tex_y
                };
        return Utils.toFloatBuffer(points);
    }
}
