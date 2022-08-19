package com.blblbl.forgotname.squive.squive;

import android.view.MotionEvent;

import com.blblbl.forgotname.MainActivity;
import com.blblbl.forgotname.R;
import com.blblbl.forgotname.moteur.TheGame;

import java.util.Random;

/**
 * Created by carotte on 22/10/16.
 */
public class GameSquive extends TheGame{

    public GameSquive(MainActivity activity) {
        super(activity);
    }

    public void resetGame() {
        mTrampos = new Trampo[NUMBER_TRAMPO];
        mEnnemis = new Ennemi[NUMBER_ENNEMIS];
        for (int i=0; i< NUMBER_ENNEMIS; ++i)
            mEnnemis[i] = new Ennemi();
        offsetTrampo = 0;
        mMoving = false;
        mFond = new Fond();
        mPersonnage = new Personnage();
        mLost = false;
        mF_Score=0;
    }

    public void loadTextures() {
        setTextures(new int[]{R.raw.texsurfaces, R.raw.wall, R.raw.tex_ball, R.raw.earth});
    }

    public void callback() {
        setDefaultTex(2);
        draw(mFond);
        if (!mLost) {
            float diff = mPersonnage.live(mTrampos);
            if (diff > 0) {
                mF_Score += diff * 8;
                mScore = (int) mF_Score;
                setScore();

                mFond.move(diff);
                for (Trampo trampo : mTrampos) {
                    if (trampo != null)
                        trampo.move(diff);
                }

                if (mTrampoTemp != null)
                    mTrampoTemp.moveFixedPoint(diff);
            }
            for (Ennemi ennemi:mEnnemis) {
                if (ennemi != null) {
                    if (ennemi.getValid())
                        ennemi.live(diff);
                }
            }
        }

        setDefaultTex(1);
        for (Trampo trampo : mTrampos)
            if (trampo != null)
                draw(trampo);
        if (mMoving && mTrampoTemp != null)
            draw(mTrampoTemp);
        setDefaultTex(0);
        draw(mPersonnage);

        setDefaultTex(3);
        for (Ennemi ennemi:mEnnemis) {
            if (ennemi != null) {
                if (ennemi.getValid()) {
                    draw(ennemi);
                    if (!mLost && Math.abs(ennemi.getPosX() - mPersonnage.getPosX()) < mPersonnage.getSizeX() / 2
                            && Math.abs(ennemi.getPosY() - mPersonnage.getPosY()) < mPersonnage.getSizeY() / 2 + ennemi.getSizeY() / 2) {
                        mLost = true;
                        setDied();
                        break;
                    }
                }
            }
        }

        Random rand = new Random();
        if (rand.nextInt() % 300 == 0) {
            if (mEnnemis[offsetEnnemi] != null) {
                if (!mEnnemis[offsetEnnemi].getValid()) {
                    mEnnemis[offsetEnnemi].validate(1.8f*rand.nextFloat()-0.9f, 0.18f + rand.nextFloat()/1.2f);
                    offsetEnnemi = (offsetEnnemi + 1)%NUMBER_ENNEMIS;
                }
            }
        }

        if (mPersonnage.getPosY() < -1.5f && !mLost) {
            mLost = true;
            setDied();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoving = true;
                mTrampoTemp = new Trampo(2* event.getX()/mLargeur - 1,
                        -2* event.getY() / mHauteur + 1);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mMoving)
                    mTrampoTemp.setMovingPoint(2* event.getX()/mLargeur - 1, -2* event.getY() / mHauteur + 1);
                break;

            case MotionEvent.ACTION_UP:
                if (mMoving) {
                    mMoving = false;
                    if (mTrampoTemp.size() > 0.05f) {
                        mTrampos[offsetTrampo] = mTrampoTemp;
                        mTrampos[offsetTrampo].fix();
                        offsetTrampo = (offsetTrampo + 1) % NUMBER_TRAMPO;
                    } else if (mTrampoTemp.size() > 0.0002f) {
                        mTrampoTemp.setNorm(0.22f);
                        mTrampos[offsetTrampo] = mTrampoTemp;
                        mTrampos[offsetTrampo].fix();
                        offsetTrampo = (offsetTrampo + 1) % NUMBER_TRAMPO;
                    }
                }
                break;
        }
        return true;
    }

    private float mF_Score;
    private int offsetTrampo;
    private Trampo[] mTrampos;
    private Ennemi[] mEnnemis;
    private int offsetEnnemi;
    private Trampo mTrampoTemp;
    private Personnage mPersonnage;
    private boolean mMoving;
    private final int NUMBER_TRAMPO = 4;
    private final int NUMBER_ENNEMIS = 4;
    private Fond mFond;
    public static final float TIME = 0.02f;


}
