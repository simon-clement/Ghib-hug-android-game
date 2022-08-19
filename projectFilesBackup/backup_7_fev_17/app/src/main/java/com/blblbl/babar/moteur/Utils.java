package com.blblbl.forgotname.moteur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by carotte on 22/05/16.
 */
public class Utils {

    public static int createProgram(String vertexSource, String fragmentSource) {
        /***
         * Create a program with both vertex and fragment Shaders.
         * since it calls checkGlError, can throw a RuntimeException!
         */
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader, vertexShader");//shouldn't happen

            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader, pixelShader");//shouldn't happen

            GLES20.glLinkProgram(program);
            checkGlError("glLinkProgram");

            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {//can happen depending on the device
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    public static void texImage2D(Context context, int idImage) {
        /**
         * throw an exception if Resource(idImage) does'nt exist!
         */
        InputStream is = context.getResources().openRawResource(idImage);//won't throw an exception if it's in R
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }
        try {
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            checkGlError("texImage2D");
        } catch (Exception e) {
            Log.e(TAG, "texImage2D failed.");
        }
        bitmap.recycle();
    }

    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public static void BindTexture(Context context, int idTexture,int idImage) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, idTexture);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        try {
            checkGlError("BindTexture, idImage : " + Integer.toString(idImage));
        } catch (RuntimeException e) {
            return;
        }
        Utils.texImage2D(context,idImage);
    }

    public static FloatBuffer toFloatBuffer(float[] tab) {
        /**
         * Can throw an exception with allocateDirect and put
         */
        FloatBuffer ret;
        ret =   ByteBuffer.allocateDirect(tab.length*FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        ret.put(tab).position(0);//warning: the allocation is slow, so avoid it as much as possible
        return ret;
    }

    public static void sendToDraw(FloatBuffer points, int maPositionHandle, int maTextureHandle) {
        /**
         * Draw your points, assuming you've put your data in "points" with DATA_POS_OFFSET and DATA_UV_OFFSET
         * (xVertex,yVertex,zVertex,xTex,yTex,xVertex,yVertex...)
         * in the end we won't use it, we'll prefer VBO... won't we?
         */
        points.position(Utils.DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false,
                Utils.TRIANGLE_VERTICES_DATA_STRIDE_BYTES, points);
        checkGlError("glVertexAttribPointer maPosition");
        points.position(Utils.DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        checkGlError("glEnableVertexAttribArray maPositionHandle");
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
                Utils.TRIANGLE_VERTICES_DATA_STRIDE_BYTES, points);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        checkGlError("glEnableVertexAttribArray maTextureHandle");
    }

    public static void sendToDraw(FloatBuffer Vertices, FloatBuffer Textures, int maPositionHandle, int maTextureHandle) {
        /**
         * Draw your points, if you have your vertex and your tex coords in two differents floatBuffers
         * we won't use it i think... maybe if we're dumb enough :p
         */
        Vertices.position(0);
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, true, 12, Vertices);
        Textures.position(0);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 8, Textures);
        GLES20.glEnableVertexAttribArray(maTextureHandle);
    }

    public static final int FLOAT_SIZE_BYTES = 4;
    public static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
    public static final int DATA_POS_OFFSET = 0;
    public static final int DATA_UV_OFFSET = 3;
    private static final String TAG = "blblbl.Utils";
}
