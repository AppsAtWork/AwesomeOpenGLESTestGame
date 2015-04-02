package Engine.Drawing;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Util.BufferBuilder;
import Engine.Util.Color;

/**
 * Created by Casper on 1-4-2015.
 */
public class FlatDrawer
{
    private int programHandle;

    public FlatDrawer(int programHandle)
    {
        this.programHandle = programHandle;
    }

    public void Draw(float[] projectionViewMatrix, float[] vertices, short[] drawingList, Color color)
    {
        this.Draw(projectionViewMatrix, BufferBuilder.BuildFloatBuffer(vertices), drawingList.length, BufferBuilder.BuildShortBuffer(drawingList), color);
    }

    public void Draw(float[] projectionViewMatrix, float[] vertices, ShortBuffer drawingListBuffer, Color color)
    {
        this.Draw(projectionViewMatrix, BufferBuilder.BuildFloatBuffer(vertices), drawingListBuffer.capacity(), drawingListBuffer, color);
    }

    public void Draw(float[] projectionViewMatrix, FloatBuffer vertexBuffer, short[] drawingList, Color color)
    {
        this.Draw(projectionViewMatrix, vertexBuffer, drawingList.length, BufferBuilder.BuildShortBuffer(drawingList), color);
    }

    public void Draw(float[] projectionViewMatrix, FloatBuffer vertexBuffer, ShortBuffer drawingListBuffer, Color color)
    {
        this.Draw(projectionViewMatrix, vertexBuffer, drawingListBuffer.capacity(), drawingListBuffer, color);
    }

    private void Draw(float[] projectionViewMatrix, FloatBuffer vertexBuffer, int drawingListLength, ShortBuffer drawingListBuffer, Color color)
    {
        //Get a handle to the vPosition member of the shader
        //If I understand correctly, this is pretty much the point that is being rasterized.
        int positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

        //Enable the vertex array that will contain the positions that will be drawn
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                vertexBuffer);

        //We need to send our projectionview matrix to OpenGLES too.
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the drawing type to the shader
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "vColor"),1, color.GetFloatArray(), 0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawingListLength, GLES20.GL_UNSIGNED_SHORT, drawingListBuffer);

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
