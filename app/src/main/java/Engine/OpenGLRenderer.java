package Engine;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Engine.OpenGLObjects.OpenGLObject;

/**
 * Created by Casper on 7-2-2015.
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer
{
    private float[] ProjectionMatrix = new float[16];
    private float[] ViewMatrix = new float[16];
    private float[] ProjectionViewMatrix = new float[16];
    private int ShaderProgram = -1;
    private Context context;
    private Game game;

    public OpenGLRenderer(Context c, Game g)
    {
        context = c;
        game = g;
        previousTime = System.currentTimeMillis();
    }

    public PointF ToWorldCoords(PointF clipped)
    {
        float[] inverted = new float[16];
        Matrix.invertM(inverted, 0, ProjectionViewMatrix,0);
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

        //Set which shader to use.
        ShaderTools.SetVertexShaderCode(ShaderTools.ShaderPresets.TextureVertexShader);
        ShaderTools.SetFragmentShaderCode(ShaderTools.ShaderPresets.TextureFragmentShader);

        //Compile the shaders
        ShaderTools.CompileVertexShader();
        ShaderTools.CompileFragmentShader();

        //Make OpenGL use the compiled shaders.
        ShaderProgram = ShaderTools.ApplyCompiledShaders();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        //We wish to work in some sort of world-space, not directly in screen space.
        //Working in screen space presents issues when the screen resolution changes (ex: rotation)

        //No need for perspective, its 2D.
        Matrix.orthoM(ProjectionMatrix, 0, -(float)width/height, (float)width/height, -1, 1, 0, 50);

        //Define the camera transformation
        Matrix.setLookAtM(ViewMatrix, 0,0,0,1,0,0,0,0,1,0);

        //Pre-calculate their multiplication
        Matrix.multiplyMM(ProjectionViewMatrix, 0, ProjectionMatrix, 0, ViewMatrix, 0);
    }

    long previousTime = -1;
    @Override
    public synchronized void onDrawFrame(GL10 gl)
    {
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

        for(int i = 0; i < OpenGLObjectManager.Drawables.size(); i++) {
            OpenGLObject obj = OpenGLObjectManager.Drawables.get(i);
            obj.Draw(ProjectionViewMatrix, ShaderProgram);
        }
    }
}
