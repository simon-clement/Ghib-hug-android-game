package com.blblbl.forgotname.moteur;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.blblbl.forgotname.R;

import java.nio.FloatBuffer;

/**
 * Created by carotte on 22/05/16.
 * module used to draw everything you may need. This is where we're gonna change a lot of things ;)
 */
public class Afficheur {
    public Afficheur(Context context) {
        mContext = context;
        Resources res = mContext.getResources();
        mVertexShaderString = res.getString(R.string.MainVertexShader);
        mFragmentShaderString = res.getString(R.string.MainFragmentShader);
        mFinalMatrix = new float[16];
        mTransfMatrix = new float[16];
        mViewMatrix = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        mSquare = ShapeCreator.makeRectangle(-0.5f,-0.5f,0.5f,0.5f);
        mIndexTexture=0;
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    public void setDrawableAttribs(float centerX, float centerY, float sizeX, float sizeY, float angle) {
        mAngle = angle;
        mPosX = centerX;
        mPosY = centerY;
        mScaleX = sizeX;
        mScaleY = sizeY;
//can't use Matrix class if we are in 2x2, can we? -> no, but 3x3 is ok for translations
        //too much code for too few progress in perfs
        Matrix.setIdentityM(mViewMatrix,0);//for now we put identity
        Matrix.setIdentityM(mTransfMatrix,0);//for now we put identity

        float left=-1, right=1, bottom=-1, top=1;
        if (mRatio > 1) {
            left = -mRatio;
            right = mRatio;
        } else {
            bottom = -1/mRatio;
            top = 1/mRatio;
        }


        Matrix.orthoM(mViewMatrix, 0, left, right, bottom, top, -1, 1);


        Matrix.translateM(mTransfMatrix, 0, mPosX, mPosY, 0);
        Matrix.rotateM(mTransfMatrix, 0, mAngle-90, 0, 0, 1);
        Matrix.scaleM(mTransfMatrix, 0, mScaleX, mScaleY, 0);
        Matrix.multiplyMM(mFinalMatrix, 0, mViewMatrix, 0, mTransfMatrix, 0);
        GLES20.glUniformMatrix4fv(mFinalMatrixHandle, 1, false, mFinalMatrix, 0);
    }
    
    public void setTexCoords(float BG_x, float BG_y, float HD_x, float HD_y) {
        mScaleTexX = HD_x - BG_x;
        mScaleTexY = BG_y - HD_y;

        mTransTexX = BG_x;
        mTransTexY = HD_y;
        GLES20.glUniform4f(mTexVectorHandle, mScaleTexX,mScaleTexY, mTransTexX, mTransTexY);
    }

    public void setTexture(int textId) {
        mIndexTexture = textId;
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID[mIndexTexture]);
    }

    public void prepareForDraw() {

        GLES20.glUniform1f(mRatioHandle, mRatio);
        //GLES20.glCullFace(GLES20.GL_BACK);
        //GLES20.glEnable(GLES20.GL_CULL_FACE);
    }
    public void clear() {//if we have a background, useless: use it for developping purpose!
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1,1,1,1);
    }

    public void DrawRectangle() {
            /**
             * draw the rectangle thas has been set with setTexture and setDrawableAttribs
             */

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
            Utils.checkGlError("glDrawArrays");

    }


    public void Draw(FloatBuffer points, int nombrePoints, int numero_texture) {
        /**
         * points must be formated (like x,y,z,xTex,yTex,x,y,...)
         * The main function of this class (and almost of this app), we'll add some args later
         * We need as much as possible to pre-load the vertex to draw!
         * This should be almost the only function in the rendering thread!
         * note: it's not using VBOS (not important?), and it may be using a LOT of textures (IMPORTANT WARNING)
         */
        GLES20.glUniform1f(mRatioHandle, mRatio);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID[numero_texture]);
        Utils.checkGlError("Draw, Beginning");
        Utils.sendToDraw(points, maPositionHandle, maTextureHandle);


        GLES20.glUniform4f(mTexVectorHandle, mScaleTexX,mScaleTexY, mTransTexX, mTransTexY);

//can't use Matrix class if we are in 2x2, can we? -> no, but 3x3 is ok for translations
        Matrix.setIdentityM(mViewMatrix,0);//for now we put identity
        Matrix.setIdentityM(mTransfMatrix,0);//for now we put identity
        //Matrix.translateM(mViewMatrix, 0, posX, posY, posZ);
        Matrix.rotateM(mTransfMatrix, 0, mAngle, 0, 0, 1);
        Matrix.translateM(mViewMatrix, 0, mPosX, mPosY, 0);
        Matrix.scaleM(mViewMatrix, 0, mScaleX, mScaleY, 0);
        Matrix.multiplyMM(mFinalMatrix, 0, mViewMatrix, 0, mTransfMatrix, 0);
        GLES20.glUniformMatrix4fv(mFinalMatrixHandle, 1, false, mFinalMatrix, 0);

        Utils.checkGlError("glUniformMatrix4fv");
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, nombrePoints);
        Utils.checkGlError("glDrawArrays");

    }



    public void PerspectiveM(float ratio) {
        //Matrix.perspectiveM(mProjMatrix, 0, angleVue, ratio, 1f, 100); (only in API 14)
        //float top = (float) Math.tan(angleVue * Math.PI/360.0) * 1f;

//frustumM(matrix,offset,left,right,bottom,top,zNear,zFar)
        // left = bottom*ratio, right = ratio*top, bottom = -top, zNear and zFar arbitrary
        //Matrix.frustumM(mProjMatrix,0, -ratio*top,ratio*top, -top, top, 1f, 10000f);
    }
    public void endInit() {
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);


        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        GLES20.glUseProgram(mProgram);
        Utils.sendToDraw(mSquare, maPositionHandle, maTextureHandle);//now or when initialisating the game?
    }



    public void handlePrograms() {
        /**
         * This fonction should be called each time the surface is created
         * (throws a runtimeException if the program can't be created)
         */
        mProgram = Utils.createProgram(mVertexShaderString, mFragmentShaderString);
        Utils.checkGlError("glDrawArrays");
        LoadAttributes();
    }
    public void LoadTextures(int nombre_textures, int[] textureNames) {
        //MUST BE CALLED AFTER HANDLEPROGRAMS
        mTextureID = new int[nombre_textures];
        GLES20.glGenTextures(nombre_textures, mTextureID, 0);
        for (int i=0; i < nombre_textures; ++i)
            Utils.BindTexture(mContext, mTextureID[i], textureNames[i]);
    }
    private void LoadAttributes() {//load id of attributes/uniforms
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        mFinalMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uFinalMatrix");
        mRatioHandle = GLES20.glGetUniformLocation(mProgram, "uRatio");
        mTexVectorHandle = GLES20.glGetUniformLocation(mProgram, "uTexVec");
    }
    private Context mContext;
    private final String mVertexShaderString,mFragmentShaderString;
    private int mProgram,mFinalMatrixHandle,maPositionHandle,maTextureHandle, mRatioHandle,
    mTexVectorHandle;
    private int[] mTextureID;
    private int mIndexTexture;
    private float mAngle;
    private float mRatio;
    private float mPosX, mPosY, mScaleX, mScaleY, mScaleTexX, mScaleTexY, mTransTexX, mTransTexY;
    private float[] mFinalMatrix,mViewMatrix,mTransfMatrix;
    private FloatBuffer mSquare;
    private static final String TAG="afficheur";
}
