package com.appsatwork.ezgles;

import android.opengl.GLES20;

import com.appsatwork.ezgles.Util.FileUtil;

/**
 * Created by Casper on 7-2-2015.
 */
public class ShaderTools
{
    private static String vertexShader = "";
    private static String fragmentShader = "";
    private static int vertexShaderHandle = - 1;
    private static int fragmentShaderHandle = -1;
    private static int programHandle = -1;

    public static void SetVertexShaderCode(String shader)
    {
        vertexShader = shader;
    }

    public static void SetFragmentShaderCode(String shader)
    {
        fragmentShader = shader;
    }

    //Loads the vertex shader to OpenGLES and returns a handle to it.
    public static int CompileVertexShader()
    {
        //Get a nice handle to the shader
        vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        //Fill the shader in openGles with our vertexShader code.
        GLES20.glShaderSource(vertexShaderHandle, vertexShader);

        //Compile the shader code in OpenGLES
        GLES20.glCompileShader(vertexShaderHandle);

        //Return the handle so we can access this shader when linking the program
        return vertexShaderHandle;
    }

    //Loads the fragment shader to OpenGLES and returns a handle to it.
    public static int CompileFragmentShader()
    {
        //Get a nice handle to the shader
        fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        //Fill the shader in openGles with our fragmentShader code.
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

        //Compile the shader code in OpenGLES
        GLES20.glCompileShader(fragmentShaderHandle);

        //Return the handle so we can access this shader when linking the program
        return fragmentShaderHandle;
    }

    public static int ApplyCompiledShaders()
    {
        //Create a program and obtain a handle to it.
        programHandle = GLES20.glCreateProgram();

        //Attach the shaders
        GLES20.glAttachShader(programHandle, vertexShaderHandle);
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);

        //Link the program
        GLES20.glLinkProgram(programHandle);

        //Actually use it.
        //When a program is used, all drawing commands will be performed
        //using the shaders attached to this program.
        GLES20.glUseProgram(programHandle);

        return programHandle;
    }

    public static String GetVertexShaderString()
    {
        try
        {
            return FileUtil.ReadStringFromRawPath("res/raw/vertex_shader.glsl");
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static String GetFragmentShaderString()
    {
        try
        {
            return FileUtil.ReadStringFromRawPath("res/raw/fragment_shader.glsl");
        }
        catch(Exception e)
        {
            return null;
        }
    }
}