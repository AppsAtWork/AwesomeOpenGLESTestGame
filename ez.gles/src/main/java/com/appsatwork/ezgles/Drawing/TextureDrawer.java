package com.appsatwork.ezgles.Drawing;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.appsatwork.ezgles.Util.BufferBuilder;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 1-4-2015.
 */
public class TextureDrawer
{
    private int programHandle;
    private ShortBuffer drawingListBuffer = BufferBuilder.BuildShortBuffer(new short[] {0,1,2,0,2,3});

    public TextureDrawer(int programHandle)
    {
        this.programHandle = programHandle;
    }

    public void Draw(float[] projectionViewMatrix, TextureProvider textureProvider, FloatBuffer vertexBuffer, FloatBuffer uvBuffer)
    {
        Draw(projectionViewMatrix, textureProvider.TextureSlot, vertexBuffer, uvBuffer);
    }

    public void Draw(float[] projectionViewMatrix, TextureProvider textureProvider, float[] vertices, FloatBuffer uvBuffer)
    {
        Draw(projectionViewMatrix, textureProvider.TextureSlot, BufferBuilder.BuildFloatBuffer(vertices), uvBuffer);
    }

    public void Draw(float[] projectionViewMatrix, TextureProvider textureProvider, float[] vertices, float[] uvs)
    {
        Draw(projectionViewMatrix, textureProvider.TextureSlot, BufferBuilder.BuildFloatBuffer(vertices), BufferBuilder.BuildFloatBuffer(uvs));
    }

    private void Draw(float[] projectionViewMatrix, int textureSlot, FloatBuffer vertexBuffer, FloatBuffer uvBuffer)
    {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureSlot);
        int positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

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
        int texCoordHandle = GLES20.glGetAttribLocation(programHandle, "a_texCoord");

        //Enable a 'vertex' array to contain the UV vertices
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        //Send the uv buffer over to OpenGLES
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        //Set the projection matrix in the shader.
        int projectionMatrixHandle = GLES20.glGetUniformLocation(programHandle, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(projectionMatrixHandle, 1, false, projectionViewMatrix, 0);

        //Send over the drawing type to the shader (1 = texture).
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 1);

        //Get the texture sampler and set it to 0. 0 is the position where the texture is stored in SendTextureToOpenGLES().
        int samplerHandle = GLES20.glGetUniformLocation(programHandle, "s_texture");
        GLES20.glUniform1i(samplerHandle, textureSlot);

        //Draw the sprite with the texture
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawingListBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, drawingListBuffer);

        //Disable the arrays again
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }
}
