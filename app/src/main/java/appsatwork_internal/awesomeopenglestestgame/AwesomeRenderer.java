package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Casper on 7-2-2015.
 */
public class AwesomeRenderer implements GLSurfaceView.Renderer
{
    public AwesomeSquare square;
    private float[] ProjectionMatrix = new float[16];
    private float[] ViewMatrix = new float[16];
    private float[] ProjectionViewMatrix = new float[16];
    private int ShaderProgram = -1;

    public AwesomeRenderer()
    {
        square = new AwesomeSquare(
                -0.5f, //left
                0.5f, //top
                1.0f,  //width
                1.0f,  //height
                1.0f,  //r
                1.0f,  //g
                1.0f   //b
        );
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        //Black background
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

        //Set which shader to use.
        AwesomeShaders.SetVertexShaderCode(AwesomeShaders.ShaderPresets.SimpleVertexShader);
        AwesomeShaders.SetFragmentShaderCode(AwesomeShaders.ShaderPresets.SimpleFragmentShader);

        //Compile the shaders
        AwesomeShaders.CompileVertexShader();
        AwesomeShaders.CompileFragmentShader();

        //Make OpenGL use the compiled shaders.
        ShaderProgram = AwesomeShaders.ApplyCompiledShaders();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        //We wish to work in some sort of world-space, not directly in screen space.
        //Working in screen space presents issues when the screen resolution changes (ex: rotation)

        //No need for perspective, its 2D.
        Matrix.orthoM(ProjectionMatrix, 0, (float)width/height, -(float)width/height, -1, 1, 0, 50);

        //Define the camera transformation
        Matrix.setLookAtM(ViewMatrix, 0,0,0,1,0,0,0,0,1,0);

        //Pre-calculate their multiplication
        Matrix.multiplyMM(ProjectionViewMatrix, 0, ProjectionMatrix, 0, ViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        //Clear the screen. I'd like to comment this out sometime, for the yolo.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        square.Draw(ProjectionViewMatrix, ShaderProgram);
    }
}
