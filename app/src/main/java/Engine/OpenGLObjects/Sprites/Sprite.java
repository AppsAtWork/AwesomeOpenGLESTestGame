package Engine.OpenGLObjects.Sprites;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import Engine.OpenGLObjects.Geometry.Rectangle;

/**
 * Created by Casper on 8-2-2015.
 */
public class Sprite extends Rectangle
{
    private static float uvs[] = new float[8];
    private FloatBuffer uvBuffer;
    private Bitmap Texture;
    private boolean sent = false;

    public Sprite(float left, float top, float width, float height, Bitmap bmp) {
        super(left, top, width, height, 1.0f, 1.0f, 1.0f, 1.0f);
        Texture = bmp;
        LoadUVBuffer();
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int program)
    {
        if(!sent)
            SendTextureToOpenGLES();

        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                vertexBuffer);

        //Need to send the uv coords to OpenGLES as well
        int texCoordHandle = GLES20.glGetAttribLocation(program, "a_texCoord");

        //Enable a 'vertex' array to contain the UV vertices
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        //Send the uv buffer over to OpenGLES
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        //Set the projection matrix in the shader.
        int projectionMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(projectionMatrixHandle, 1, false, projectionViewMatrix, 0);

        //Send over the drawing type to the shader (1 = texture).
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "drawingType"), 1);

        //Get the texture sampler and set it to 0. 0 is the position where the texture is stored in SendTextureToOpenGLES().
        int samplerHandle = GLES20.glGetUniformLocation(program, "s_texture");
        GLES20.glUniform1i(samplerHandle, 0);

        //Draw the sprite with the texture
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawingOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        //Disable the arrays again
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }

    private void LoadUVBuffer()
    {
        uvs = new float[] {
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f
        };
        ByteBuffer buffer = ByteBuffer.allocateDirect(uvs.length*4);
        buffer.order(ByteOrder.nativeOrder());
        uvBuffer = buffer.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);
    }

    private void SendTextureToOpenGLES()
    {
        //Get handles for the texture(s)
        int[] textureHandles = new int[1];
        GLES20.glGenTextures(1, textureHandles, 0);

        //Set the texture as active in OpenGLES
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[0]);

        //Set filtering on the textures
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //Set wrapping on the textures
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        //Load the bitmap into the texture that we just set params for
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, Texture, 0);

        sent = true;
    }
}
