package com.appsatwork.ezgles.Drawing;

import android.opengl.GLES20;

/**
 * Created by Casper on 2-4-2015.
 */
public class ShaderProgram
{
    private String vertexShader;
    private String fragmentShader;

    private int vertexShaderHandle;
    private int fragmentShaderHandle;

    public int ProgramHandle;

    public ShaderProgram(String vertexShader, String fragmentShader)
    {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;

        Compile();
        Link();
    }

    private void Compile()
    {
        //Get a nice handle to the shaders
        vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        //Fill the shaders in openGles with our shader code.
        GLES20.glShaderSource(vertexShaderHandle, vertexShader);
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

        //Compile the shader code in OpenGLES
        GLES20.glCompileShader(vertexShaderHandle);
        GLES20.glCompileShader(fragmentShaderHandle);
    }

    private void Link()
    {
        ProgramHandle = GLES20.glCreateProgram();

        //Attach the shaders
        GLES20.glAttachShader(ProgramHandle, vertexShaderHandle);
        GLES20.glAttachShader(ProgramHandle, fragmentShaderHandle);

        //Link the program
        GLES20.glLinkProgram(ProgramHandle);
    }

    public void SetActive()
    {
        GLES20.glUseProgram(ProgramHandle);
    }
}
