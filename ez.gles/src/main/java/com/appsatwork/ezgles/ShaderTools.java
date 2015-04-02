package com.appsatwork.ezgles;

import android.opengl.GLES20;

import com.appsatwork.ezgles.Util.FileUtil;

/**
 * Created by Casper on 7-2-2015.
 */
public class ShaderTools
{
    public static String GetVertexShaderString(String shaderType)
    {
        try
        {
            return FileUtil.ReadStringFromRawPath("res/raw/" + shaderType + "_vertex_shader.glsl");
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static String GetFragmentShaderString(String shaderType)
    {
        try
        {
            return FileUtil.ReadStringFromRawPath("res/raw/" + shaderType + "_fragment_shader.glsl");
        }
        catch(Exception e)
        {
            return null;
        }
    }
}