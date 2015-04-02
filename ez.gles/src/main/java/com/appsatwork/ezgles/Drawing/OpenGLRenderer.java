package com.appsatwork.ezgles.Drawing;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.appsatwork.ezgles.Gaming.Game;
import com.appsatwork.ezgles.Objects.Drawables.IDrawable;
import com.appsatwork.ezgles.Objects.TextureObjects.TextureManager;
import com.appsatwork.ezgles.ShaderTools;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Casper on 7-2-2015.
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer
{
    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] projectionViewMatrix = new float[16];
    private DrawableList drawableList;
    private Game game;
    private TextureManager textureManager;

    public OpenGLRenderer(Game game, DrawableList drawableList, TextureManager manager)
    {
        this.game = game;
        this.drawableList = drawableList;
        this.textureManager = manager;
        previousTime = System.currentTimeMillis();
    }

    public PointF ToWorldCoords(PointF clipped)
    {
        float[] inverted = new float[16];
        Matrix.invertM(inverted, 0, projectionViewMatrix,0);
        float[] vector = new float[] {clipped.x, clipped.y, 0, 0};
        float[] result = new float[4];

        Matrix.multiplyMV(result, 0, inverted, 0, vector, 0);

        return new PointF(result[0], result[1]);
    }

    public void SetClearColor(float r, float g, float b)
    {
        //Black background
        GLES20.glClearColor(r,g,b,1.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        //Black background
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

        //Enable alpha blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        //We wish to work in some sort of world-space, not directly in screen space.
        //Working in screen space presents issues when the screen resolution changes (ex: rotation)

        //No need for perspective, its 2D.
        Matrix.orthoM(projectionMatrix, 0, -(float)width/height, (float)width/height, -1, 1, 0, 50);

        //Define the camera transformation
        Matrix.setLookAtM(viewMatrix, 0,0,0,1,0,0,0,0,1,0);

        //Pre-calculate their multiplication
        Matrix.multiplyMM(projectionViewMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    long previousTime = -1;
    @Override
    public synchronized void onDrawFrame(GL10 gl)
    {
        //Update textures if necessary.
        textureManager.UpdateTextures();

        long currentMillis = System.currentTimeMillis();
        long timeSinceLast = currentMillis - previousTime;
        while(timeSinceLast >= 0)
        {
            game.Update();
            timeSinceLast -=game.TimeStepMillis;
        }
        previousTime = currentMillis;

        //Clear the screen. I'd like to comment this out sometime, for the yolo.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        for(int i = 0; i < drawableList.Size(); i++)
        {
            IDrawable obj = drawableList.Get(i);
            obj.Draw(projectionViewMatrix);
        }
    }
}
