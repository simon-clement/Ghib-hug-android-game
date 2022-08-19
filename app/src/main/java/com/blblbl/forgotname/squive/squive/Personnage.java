package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.Vect;

import java.util.Random;

public abstract class Personnage extends Drawable{
    Personnage(Graphe graphe, float taille_screen_x, float taille_screen_y, float hitbox,
               int first_sommet, int animation_par_frame,
               float plus_degres) {
        ajout_degres = plus_degres;
        animation_per_frame = animation_par_frame;//in fact it's more like "frame by animation..."
        to_next_frame = 0;
        mSensAnimation = 1;
        mCircleSize = hitbox;
        is_turning = false;
        has_next_sommet = false;
        animation = false;
        end_animation = false;
        graphe_ref= graphe;
        Random rand = new Random();
        sommet_graphe = first_sommet;
        is_moving = false;
        int bon_fils = rand.nextInt(graphe_ref.getNbFils(sommet_graphe));
        new_sommet_graphe = graphe.getFils(sommet_graphe, bon_fils);
        time_to_move = 500; //in millis
        speed_turning = 10f;

        Vect pos_screen = graphe_ref.getSommet(sommet_graphe).getVect();
        float BG_tex_x = 0, BG_tex_y = 1f/(float) NUMBER_FRAMES;
        float HD_tex_x = 1, HD_tex_y = 0;
        float rotation = ajout_degres*3.1415f / 180 + graphe.getDirection(sommet_graphe, bon_fils).to_angle();
        //la rotation n'a aucun impact...


        setDrawableAttribs(taille_screen_x, taille_screen_y, BG_tex_x, BG_tex_y, HD_tex_x, HD_tex_y,
                pos_screen.getmX(), pos_screen.getmY(), rotation);
        time_last_move = System.currentTimeMillis();
        time_last_turn = time_last_move;
    }

    protected void setAnimationRepos(int animation_repos) {
        mAnimation_repos = animation_repos;
        mFrame = (mAnimation_repos + NUMBER_FRAMES - 1) % NUMBER_FRAMES; // on prend la frame d'avant repos
        setTexCoord_y((float) (mFrame + 1) / (float) NUMBER_FRAMES, (float) mFrame / (float) NUMBER_FRAMES);
    }

    protected void live_when_done_turning() {
        if (is_moving_after_turning) {
            is_moving = true;
            is_moving_after_turning = false;
            time_last_move = System.currentTimeMillis();
        }
        is_turning = false;
    }

    public Vect get_exact_position() {
        return new Vect(getPosX(), getPosY());
    }

    protected void live() {
        /*
        tourne si is_turning, avance si is_moving
         */
        if (is_turning) {
            float vecteur_reach_angle = futur_angle - (float)Math.toRadians(getAngle());
            if (Math.abs(vecteur_reach_angle) > Math.PI) { // si 2pi - |vecteur_reach_angle| < |vecteur|
                vecteur_reach_angle = - Math.signum(vecteur_reach_angle) * 2*(float)Math.PI + vecteur_reach_angle;
            }

            float deplacement = speed_turning * (System.currentTimeMillis() - time_last_turn)/1000f;
            time_last_turn = System.currentTimeMillis();


            if (Math.abs(vecteur_reach_angle) < deplacement) {
                setAngle((float)Math.toDegrees(futur_angle));
                live_when_done_turning();
            } else {
                float turn = Math.signum(vecteur_reach_angle) * deplacement;
                float new_angle = (float)Math.toRadians(getAngle()) + turn;
                if (new_angle < - (float) Math.PI || new_angle > (float) Math.PI) {
                    new_angle -= 2 * Math.signum(new_angle) * Math.PI;
                }
                setAngle((float)Math.toDegrees(new_angle));
            }
        }

        if (is_moving) { //si on est en chemin vers new_sommet_graphe
            animation = true;
            float avancement = (float)(System.currentTimeMillis() - time_last_move) / (float) time_to_move;
            if (avancement >= 1) { // si le temps est dépassé
                end_animation = !has_next_sommet;
                is_moving_after_turning = has_next_sommet;
                is_moving = false;
                has_next_sommet = false;
                sommet_graphe = new_sommet_graphe;
                new_sommet_graphe = next_sommet_graphe;
                if (is_moving_after_turning)
                    tourner(next_sommet_graphe);
            }
            Vect new_pos = new Vect(graphe_ref.getSommet(sommet_graphe).getVect()); // on prend le sommet
            if (avancement < 1){
                Vect deplacement = graphe_ref.getSommet(new_sommet_graphe).getVect().moins(
                        graphe_ref.getSommet(sommet_graphe).getVect()).by(avancement);
                new_pos.add(deplacement);
            }
            setPos(new_pos.getmX(), new_pos.getmY());
        }
        change_frame();
    }

    private void change_frame() {
        if (animation) {
            if (end_animation && mFrame == mAnimation_repos && mSensAnimation == 1) {
                animation = false;
                end_animation = false;
            } else {
                setTexCoord_y((float) (mFrame + 1) / (float) NUMBER_FRAMES, (float) mFrame / (float) NUMBER_FRAMES);
                to_next_frame = (to_next_frame + 1) % animation_per_frame;
                if (to_next_frame == 0) {
                    mFrame = (mFrame + mSensAnimation) % NUMBER_FRAMES;
                }
                if (mFrame == 0 || mFrame == NUMBER_FRAMES - 1) {
                    mSensAnimation *= -1;
                }
            }
        }
    }

    public boolean touche(Personnage deuxieme) {
        return get_exact_position().moins(deuxieme.get_exact_position()).norme_carree()
                < mCircleSize * deuxieme.mCircleSize;
    }

    public void move(int index_sommet) {// à utiliser par IA
        //TODO changer index_arc en index_sommet, plus simple d'utilisation
        if (!is_moving) {
            tourner(index_sommet);
            is_moving_after_turning = true;
            is_turning = true;
            new_sommet_graphe = index_sommet;
            time_last_turn = System.currentTimeMillis();
        }
    }

    public void move(float direction_X, float direction_Y) { //à utiliser par héros
        if (!is_moving) {
            int retour[] = graphe_ref.parse_dir(sommet_graphe, new Vect(direction_X, direction_Y));
            if (retour[1] != -1) {
                tourner(retour[0]);
                is_moving_after_turning = true;
                is_turning = true;
                new_sommet_graphe = retour[0];
                time_last_turn = System.currentTimeMillis();
            }
        } else { // on change l'intention suivante dans tous les cas, pour avoir le droit à l'erreur
            int retour[] = graphe_ref.parse_dir(new_sommet_graphe, new Vect(direction_X, direction_Y));
            if (retour[1] != -1) {
                has_next_sommet = true;
                next_sommet_graphe = retour[0];
            }
        }
    }

    protected void tourner(int sommet_futur) {
        if (!is_moving) {
            futur_angle = ajout_degres * 3.1415f / 180 + graphe_ref.getAngle(sommet_graphe, sommet_futur);
            is_turning = true;
            time_last_turn =System.currentTimeMillis();
        }
    }

    public void tourner_prepare(int sommet_futur) {
        if (!is_turning || !is_moving_after_turning)
            tourner(sommet_futur);
    }

    public boolean get_is_moving() {
        return is_moving;
    }

    protected int get_prochain_sommet() {
        if (!is_moving) {
            return sommet_graphe;
        } else {
            return new_sommet_graphe;
        }
    }

    protected int get_last_sommet() {
        return sommet_graphe;
    }

    public float getmCircleSize() {
        return mCircleSize;
    }
    public int getSommet_graphe() {
        return sommet_graphe;
    }

    protected final static String TAG = "Personnage! ";
    protected Graphe graphe_ref;
    protected int sommet_graphe;
    protected boolean is_moving, has_next_sommet, is_turning, is_moving_after_turning;
    protected float futur_angle;
    protected int new_sommet_graphe; // is is_moving=true!
    protected int next_sommet_graphe; //if has_next_sommet is true!
    protected long time_last_move, time_last_turn;
    protected int time_to_move;  // start with value like 500ms and decreases (IN MILLIS)
    protected float speed_turning;
    private boolean end_animation, animation;
    private int mFrame=0;
    private int animation_per_frame;
    private int to_next_frame;
    protected int NUMBER_FRAMES;
    private int mSensAnimation;
    private int mAnimation_repos;
    private float mCircleSize; //hitbox
    private final float ajout_degres;
}
