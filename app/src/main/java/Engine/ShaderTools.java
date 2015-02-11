package Engine;

import android.opengl.GLES20;

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


    public static class ShaderPresets
    {
        public static String SimpleVertexShader =
                        "uniform    mat4        uMVPMatrix;"        +
                        "attribute  vec4        vPosition;"         +
                        "void main() "                              +
                        "{"                                         +
                        "  gl_Position = uMVPMatrix * vPosition;"   +
                        "}";

        public static String SimpleFragmentShader =
                        "precision mediump float;"          +
                        "uniform vec4 vColor;"               +
                        "void main() "                      +
                        "{"                                 +
                        "  gl_FragColor = vColor;"            +
                        "}";

        public static String TextureVertexShader =
                "uniform mat4 uMVPMatrix;"                  +
                "attribute vec4 vPosition;"                 +
                "attribute vec2 a_texCoord;"                +
                "varying vec2 v_texCoord;"                  +
                "void main() {"                             +
                "  gl_Position = uMVPMatrix * vPosition;"   +
                "  v_texCoord = a_texCoord;"                +
                "}";

        public static String TextureFragmentShader =
                "precision mediump float;"                              +
                "varying vec2 v_texCoord;"                              +
                "uniform sampler2D s_texture;"                          +
                "uniform vec4 vColor;"                                  +
                "uniform int drawingType;"                              +
                "void main() "                                          +
                "{"                                                     +
                    "if(drawingType == 1)" +
                        "{ gl_FragColor = texture2D( s_texture, v_texCoord ); }" +
                    "else if(drawingType == 0)" +
                        "{ gl_FragColor = vColor; }" +
                "}";
    }
}
// texture2D( s_texture, v_texCoord );