package com.blblbl.forgotname.squive.squive;

import android.util.Log;

import java.util.Random;

/**
 * Created by carotte on 23/10/16.
 */
public class Ennemi extends Personnage {
    Ennemi(Graphe graphe_init, int score) {
        super(graphe_init, 0.48f, 0.156f, 0.2f,
                (new Random()).nextInt(graphe_init.getSommet_gap()), 3,0);
        tire = false;
        float f_score = (float) Math.pow((double) (1+score), 0.2);
        time_to_move = 180 +  (int) (600f/f_score); //in millis
        speed_turning = 16f - 4/f_score;
        time_to_shoot = 130 + (int)(550 / f_score); // in ms
        time_charge = 220 + (int)(1000 / f_score);
        NUMBER_FRAMES = 7;
        setAnimationRepos(4);
    }

    public void setmChargeLaser(ChargeLaser chargeLaser) {
        mChargeLaser = chargeLaser;
    }

    public void live(Personnage adversaire, Laser laser) {
        super.live();
        next_action(adversaire);
        mChargeLaser.live();
        if (is_shooting() && !laser.get_is_active()) { // si on est en train de shooter
            laser.setLaser(graphe_ref, sommet_graphe, sommet_cible_tir);
            time_start_tir = System.currentTimeMillis();
        } else if (!tire) {
            laser.resetLaser();
            if (mChargeLaser.get_is_active())
                mChargeLaser.desactivate();
        }
    }

    private boolean peut_arreter_de_tirer() {
        return !mChargeLaser.get_is_active() ||
                mChargeLaser.full_loaded() && System.currentTimeMillis() > time_start_tir + time_to_shoot;
    }

    public boolean is_shooting() {
        return tire && mChargeLaser.full_loaded();
    }

    private void next_action(Personnage adversaire) {
        if (!is_moving && peut_arreter_de_tirer() && !is_turning) {
            int sommet_adversaire_actuel = adversaire.get_last_sommet();
            int sommet_adversaire_prochain = adversaire.get_prochain_sommet();
            if (tire && sommet_adversaire_prochain == sommet_cible_tir)
                return;
            tire = false;
            //là on tire pas, ou on tire au mauvais endroit
            if (sommet_adversaire_prochain == sommet_graphe && !is_turning && !is_moving_after_turning){
                //OH PUTAIN IL ARRIVE POUR UN CÂLIN (et on a pas commencé à se barrer)
                int sommet = graphe_ref.what_son(sommet_graphe, sommet_adversaire_actuel); //élémentaire, mon cher...
                if (sommet == -1) // si sommet_prochain == sommet_actuel
                    return; //normalement le jeu s'est déjà arrêté, faudrait limite throw une exception
                int nombre_voisins = graphe_ref.getNbFils(sommet_graphe);
                if (nombre_voisins == 1) {
                    //on est coincé, on peut pas fuir
                    sommet_cible_tir = sommet_adversaire_actuel;
                    tire_apres_tourne = true;
                    tourner(sommet_adversaire_actuel);
                    return; //ATTENTION ÇA PEUT ÊTRE DANGEREUX
                }
                Random rand = new Random();
                int next = sommet;
                while (next == sommet)
                    next = rand.nextInt(nombre_voisins); //on choisit un nouveau sommet
                move(graphe_ref.getFils(sommet_graphe,next));//il est temps d'arrêter de tirer et de se sortir les doigts du c**
                sommet_cible_tir = sommet_graphe;
            } else if (sommet_adversaire_prochain != sommet_graphe){ //si on est pas en train de fuir le câlin
                int chemin[] = graphe_ref.chemin_random(sommet_graphe, sommet_adversaire_prochain);
                if (chemin.length == 1) { // le sommet est ciblable
                    sommet_cible_tir = sommet_adversaire_prochain;
                    tire_apres_tourne = true;
                    tourner(sommet_adversaire_prochain);
                } else { // si le sommet est plus loin
                    //move to chemin[1]
                    move(chemin[1]);
                    sommet_cible_tir = sommet_adversaire_prochain;
                }
            }
        } else if (is_moving || is_turning){
            tire = false;
        }
    }

    @Override
    protected void live_when_done_turning() {
        super.live_when_done_turning();
        if (tire_apres_tourne) {
             tire = true;
             tire_apres_tourne = false;
             mChargeLaser.activate(this);
        }
    }

    public long getLoadTime() {
        return time_charge;
    }

    private static final String TAG="Ennemi";
    private long time_start_tir, time_to_shoot, time_charge;
    private boolean tire, tire_apres_tourne;
    private int sommet_cible_tir;
    private ChargeLaser mChargeLaser;
}
