package com.blblbl.forgotname.squive.squive;

import android.util.Log;
import android.view.MotionEvent;

import com.blblbl.forgotname.MainActivity;
import com.blblbl.forgotname.R;
import com.blblbl.forgotname.moteur.Drawable;
import com.blblbl.forgotname.moteur.TheGame;
import com.blblbl.forgotname.moteur.Vect;

import java.util.Random;

/**
 * Created by carotte on 22/10/16.
 */
public class GameSquive extends TheGame{

    public GameSquive(MainActivity activity, int score) throws Exception {
        super(activity, score);
    }

    public void resetGame() throws Exception {
        mFond = new Fond();
        mLost = false;
        mIsCoeurs = false;
        mIsDyingIcons = false;
        mImgFullscreen = false;
        mTexFullscreen = TEX_TUTO_FOND;
        mActiveEnnemi = false;
        try {
            mNiveau = GraphCreator.create_graph(GraphCreator.scoreToNiveau(mScore));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            throw new Exception(e);
        }
        mHero = new Hero(mNiveau);
        mEnnemi = new Ennemi(mNiveau, mScore);
        mLaser = new Laser();
        mTexFullscreen = TEX_TUTO_FOND;
        int nbCoeurs = 200;
        mCoeurs = new Coeur[nbCoeurs];
        started_hearts = System.currentTimeMillis();
        mChargeLaser = new ChargeLaser(mEnnemi.getLoadTime());
        mEnnemi.setmChargeLaser(mChargeLaser);
    }

    private final static int TEX_HERO = 0;
    private final static int TEX_ENNEMI = 1;
    private final static int TEX_FOND = 2;// C'est mieux d'utiliser ça
    private final static int TEX_LASER = 3;
    private final static int TEX_COEUR = 4;
    private final static int TEX_DYING_ICON = 5;
    private final static int TEX_WIN_FOND = 6;
    private final static int TEX_WIN_FOND_CHALL_1 = 7;
    private final static int TEX_WIN_FOND_CHALL_2 = 8;
    private final static int TEX_LOST_FOND = 9;
    private final static int TEX_BOX_LASER_CHARGE = 10;
    private final static int TEX_LASER_CHARGE = 11;
    private final static int TEX_TUTO_FOND = 12;
    private final static int TEX_GRAPHE = 13;

    public void loadTextures() {
        setTextures(new int[]{R.drawable.tex_hero, R.drawable.tex_ennemi, R.drawable.tex_fond,
                R.drawable.laser, R.drawable.tex_coeur, R.drawable.tex_dying_icon,
                R.drawable.tex_win, R.drawable.tex_win_chall_1, R.drawable.tex_win_chall_2,
                R.drawable.tex_dead, R.drawable.boite_laser_charge,
                R.drawable.laser_charge, R.drawable.tex_tuto, R.drawable.tex_graphe});
        Log.i(TAG, Integer.toString(R.raw.password)+Integer.toString(R.drawable.password));
    }

    public void callback() {
        if (!mImgFullscreen) {
            if (mHero.get_is_moving())
                mActiveEnnemi = true;

            if (!mActiveEnnemi && mScore < 5)
                setDefaultTex(TEX_TUTO_FOND);
            else
                setDefaultTex(TEX_FOND);

            draw(mFond);

            setDefaultTex(TEX_GRAPHE);
            for (Drawable drawable : mNiveau.getmDrawables())
                draw(drawable);


            setDefaultTex(TEX_HERO);
            draw(mHero);

            if (!mIsCoeurs && mLaser.get_is_active()) {
                setDefaultTex(TEX_LASER);
                draw(mLaser);
            }

            setDefaultTex(TEX_ENNEMI);
            draw(mEnnemi);

            if (!mIsCoeurs && mChargeLaser.get_is_active()) {
                setDefaultTex(TEX_LASER_CHARGE);
                draw(mChargeLaser);
                mChargeLaser.box_mode();
                setDefaultTex(TEX_BOX_LASER_CHARGE);
                draw(mChargeLaser);
            }

            if (mHero.touche(mEnnemi) && !mIsCoeurs && !mIsDyingIcons) {

                mScore += 1;
                setScore();
                soundWin();
                started_hearts = System.currentTimeMillis();
                mIsCoeurs = true;
                if (mScore ==42)
                    mTexFullscreen = TEX_WIN_FOND_CHALL_2;
                else if (mScore < 100)
                    mTexFullscreen = TEX_WIN_FOND;
                else
                    mTexFullscreen = TEX_WIN_FOND_CHALL_1; // le fameux chall infinissable
                Random random = new Random();
                for (int i=0; i<mCoeurs.length; ++i)
                    mCoeurs[i] = new Coeur(random);
            }

            if (mLaser.touche(mHero, mNiveau) && !mIsCoeurs && !mIsDyingIcons) {
                started_hearts = System.currentTimeMillis();
                mTexFullscreen = TEX_LOST_FOND;
                mIsDyingIcons = true;
                soundDeath();
                Random random = new Random();
                for (int i=0; i<mCoeurs.length; ++i)
                    mCoeurs[i] = new Coeur(random);
            }

            if (!mIsCoeurs && !mIsDyingIcons) {
                mHero.live();
                boolean activated = mLaser.get_is_active();
                if (mActiveEnnemi)
                    mEnnemi.live(mHero, mLaser);
                if (mLaser.get_is_active() != activated) {
                    if (!activated) {// si le laser vient de s'activer
                        soundLaserStart();
                    }
                }

            }

        } else {
            setDefaultTex(mTexFullscreen);
            draw(mFond);
        }


        if (mIsCoeurs || mIsDyingIcons) {
            Random random = new Random();
            setDefaultTex(mIsCoeurs ? TEX_COEUR : TEX_DYING_ICON);
            boolean stay_hearts = System.currentTimeMillis() < time_hearts + started_hearts;
            for (Coeur coeur : mCoeurs) {
                try {
                    coeur.live(random, stay_hearts, (float)(System.currentTimeMillis() - started_hearts) /(float) time_hearts );
                    draw(coeur);
                } catch (Exception e) {
                    for (int i=0; i<mCoeurs.length; ++i)
                        mCoeurs[i] = new Coeur(random);
                }
                }
            if (started_hearts + time_hearts < System.currentTimeMillis()) {
                //on a déjà rempli mTexFullscreen
                mImgFullscreen = true;
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        if (mImgFullscreen)
            resetGameThreaded();
        /* need to be here to say that we want to interpret the down event as a fling */
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        if (mImgFullscreen)
            return false;//on "sauvegarde" l'évènement si on est en train d'afficher une image
        Vect velocity = new Vect(velocityX, velocityY);
        if (velocity.norme_carree() > 1000) {
            mHero.move(event2.getX() - event1.getX(), -(event2.getY() - event1.getY()));
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mImgFullscreen)
            return false;
        Vect distance = new Vect(-distanceX, distanceY);
        if (distance.norme_carree() > 50) { //minimum pour pouvoir se retourner
            int retourne[] = mNiveau.parse_dir(mHero.getSommet_graphe(), distance);
            if (retourne[1] != -1) {
                mHero.tourner_prepare(retourne[0]);
                if (distance.norme_carree() > 1800) // minimum pour se déplacer
                    mHero.move(- distanceX, distanceY);
            }
        }

        return true;
    }

    private final static String TAG = "GameSquive";
    Graphe mNiveau;
    private Ennemi mEnnemi;
    private Hero mHero;
    private Laser mLaser;
    private Fond mFond;
    private boolean mImgFullscreen;
    private boolean mIsCoeurs, mIsDyingIcons, mActiveEnnemi;
    private int mTexFullscreen;
    private Coeur[] mCoeurs;
    private ChargeLaser mChargeLaser;
    private long started_hearts;
    private static final long time_hearts = 600;
}
