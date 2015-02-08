package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 7-2-2015.
 */
public class AwesomeSquare
{
    private float R;
    private float G;
    private float B;

    public PointF LeftUpper;
    private PointF RightUpper;
    private PointF RightLower;
    public PointF LeftLower;

    private float[] vertices;
    private short[] drawingOrder = new short[] {0,1,2,0,2,3};
    private float[] color;

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    public AwesomeSquare(float left, float top, float width, float height, float r, float g, float b)
    {
        LeftUpper = new PointF(left, top);
        LeftLower = new PointF(left, top - height);
        RightUpper = new PointF(left + width, top);
        RightLower = new PointF(left + width, top-height);

        R = r;
        G = g;
        B = b;


        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    public void Draw(float[] projectionViewMatrix, int program)
    {
        //Get a handle to the vPosition member of the shader
        //If I understand correctly, this is pretty much the point that is being rasterized.
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

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
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(program, "color"),1,color,0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawingOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    public void SetColor(float r, float g, float b)
    {
        R= r;
        G = g;
        B = b;
        color = new float[] {R,G,B, 1.0f};
    }

    public void UpdateVertexBuffer()
    {
        vertices = new float[]{
                LeftUpper.x, LeftUpper.y, 0.0f,
                RightUpper.x, RightUpper.y, 0.0f,
                RightLower.x, RightLower.y, 0.0f,
                LeftLower.x, LeftLower.y, 0.0f
        };
        color = new float[] {R,G,B, 1.0f};

        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public void UpdateDrawListBuffer()
    {
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }
}
