package com.blblbl.forgotname.saute_et_tire;

import android.view.MotionEvent;

import com.blblbl.forgotname.R;
import com.blblbl.forgotname.MainActivity;
import com.blblbl.forgotname.moteur.TheGame;

import java.util.Random;

/**
 * Created by carotte on 22/10/16.
 */
public class GameTest extends TheGame {

    public GameTest(MainActivity act) {
        super(act);
    }

    public void resetGame() {
        TIME = 0.015f;
        mPersonnage = new Personnage();
        mPersonnage.setDrawableAttribs(0.2f,0.3f,0,1,1,0,-0.5f,0.5f,0,0);
        mSpeedTank=1.5f;
        mFond = new Fond(mSpeedTank);
        mSol = new Sol(mSpeedTank);
        mBalles = new Balle[14];
        for (int i=0; i< 14; ++i) {
            mBalles[i] = new Balle();
        }
        mMurs = new Mur[4];
        mEnnemis = new Ennemi[4];
        for (int i=0; i<4; ++i) {
            mMurs[i] = new Mur();
            mEnnemis[i] = new Ennemi();
        }
        offsetBalles = 0;
        offsetMurs= 0;
        offsetEnnemi=0;
        mLost = false;
        mScore = 0;
    }

    public void loadTextures() {
        setTextures(new int[]{R.raw.texsurfaces, R.raw.tex_ball, R.raw.ground, R.raw.earth, R.raw.wall});
    }

    public void callback() {

        setDefaultTex(mFond.getTexture());
        draw(mFond);
        setDefaultTex(mSol.getTexture());
        draw(mSol);

        setDefaultTex(4);

        for (int i=0; i< 4; ++i) {
            if (mMurs[i].getValid()) {
                draw(mMurs[i]);
                if (!mLost && Math.abs(mMurs[i].getPosX() - mPersonnage.getPosX()) < mPersonnage.getSizeX() / 2
                        && Math.abs(mMurs[i].getPosY() - mPersonnage.getPosY()) < mPersonnage.getSizeY() / 2 + mMurs[i].getSizeY() / 2) {
                    mLost = true;
                    setDied();
                }
            }
        }
        setDefaultTex(mEnnemis[0].getTexture());
        for (int i=0; i<4; ++i) {
            if (mEnnemis[i].getValid()) {
                draw(mEnnemis[i]);
                for (int j=0; j<14; ++j) {
                    if (mBalles[j].getValid())
                        if (Math.abs(mEnnemis[i].getPosX() - mBalles[j].getPosX()) < mBalles[j].getSizeX() / 2 + mEnnemis[i].getSizeX() / 2
                                && Math.abs(mEnnemis[i].getPosY() - mBalles[j].getPosY()) < mBalles[j].getSizeY() / 2 + mEnnemis[i].getSizeY() / 2) {
                            mEnnemis[i].die();
                            mBalles[j].invalidate();
                            break;
                        }

                    if (!mLost && Math.abs(mEnnemis[i].getPosX() - mPersonnage.getPosX()) < mPersonnage.getSizeX() / 2
                            && Math.abs(mEnnemis[i].getPosY() - mPersonnage.getPosY()) < mPersonnage.getSizeY() / 2 + mEnnemis[i].getSizeY() / 2) {
                        mLost = true;
                        setDied();
                    }
                }
            }
        }

        setDefaultTex(mPersonnage.getTexture());
        draw(mPersonnage);
        for (int i=0; i< 14; ++i) {
            if (mBalles[i].getValid()) {
                draw(mBalles[i]);
            }
        }


        if (!mLost) {


            Random random = new Random();
            if (random.nextInt() % 100 == 0) {
                if (!mMurs[offsetMurs].getValid()) {
                    mMurs[offsetMurs].validate(mSpeedTank);
                    offsetMurs = (offsetMurs +1)%4;
                }
            }
            if (random.nextInt() % 333 == 0) {
                if (!mEnnemis[offsetEnnemi].getValid()) {
                    mEnnemis[offsetEnnemi].validate(mSpeedTank);
                    offsetEnnemi = (offsetEnnemi + 1)%4;
                }
            }


            mPersonnage.live();
            mFond.live();
            mSol.live();
            for (int i = 0; i < 4; ++i) {
                if (mMurs[i].getValid())
                    mMurs[i].live();
                if (mEnnemis[i].getValid())
                    mEnnemis[i].live();
            }
            for (int i = 0; i < 14; ++i)
                if (mBalles[i].getValid())
                    mBalles[i].live();

        } else {
            //TODO print "you lost!"

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float posX=0;
        boolean active = false;
        int action = event.getAction();
        if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
            final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            if (pointerIndex != -1) {
                posX = event.getX(pointerIndex);
                active = true;
            }
        } else if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            posX = event.getX();
            active = true;
        }

        if (active && !mLost) {
            if (posX < mLargeur/2) {
                mPersonnage.jump();
                mScore++;
                TIME = 0.015f + 0.001f*(float) Math.sqrt(mScore);
                setScore();
            }
            else
                if (mPersonnage.can_shoot() && !mLost) {
                    if (mBalles[offsetBalles].can_be_replaced()) {
                        mBalles[offsetBalles].setDrawableAttribs(0.1f, 0.1f, 0.3f,0.5f, 0.5f, 0.3f,
                                mPersonnage.getPosX() + mPersonnage.getSizeX()/2,
                                mPersonnage.getPosY() + mPersonnage.getSizeY()/4,
                                0,0);
                        mBalles[offsetBalles].startWithSpeed(5f);
                        offsetBalles = (offsetBalles + 1)%14;
                    }
                }
        }
        return true;
    }

    public static float TIME;
    private Personnage mPersonnage;
    private Fond mFond;
    private Mur mMurs[];
    private Sol mSol;
    private Balle[] mBalles;
    private Ennemi[] mEnnemis;
    private int offsetEnnemi;
    private int offsetBalles;
    private int offsetMurs;
    private float mSpeedTank;
    private boolean mLost;

}
