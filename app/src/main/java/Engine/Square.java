package Engine;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 7-2-2015.
 */
public class Square
{
    protected float R;
    protected float G;
    protected float B;

    protected float[] BaseLeftUpper;
    protected float[] BaseRightUpper;
    protected float[] BaseRightLower;
    protected float[] BaseLeftLower;

    protected float[] LeftUpper;
    protected float[] RightUpper;
    protected float[] RightLower;
    protected float[] LeftLower;

    protected float[] vertices;
    protected short[] drawingOrder = new short[] {0,1,2,0,2,3};
    protected float[] color;

    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;

    protected float scale = 1;
    protected float degrees = 0;
    protected float[] translation;


    public Square(float left, float top, float width, float height, float r, float g, float b)
    {
        BaseLeftUpper = new float[] { -width/2.0f, height/2.0f };
        BaseLeftLower = new float[] { -width/2.0f, -height/2.0f };
        BaseRightUpper = new float[] { width/2.0f, height/2.0f };
        BaseRightLower = new float[] { width/2.0f, -height/2.0f};

        LeftUpper = new float[] { left, top };
        LeftLower = new float[] { left, top - height};
        RightUpper = new float[] {left + width, top};
        RightLower = new float[] {left + width, top-height};

        translation = new float[] { left + width/2, top - height/2};

        R = r;
        G = g;
        B = b;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    public void SetScale(float factor)
    {
        scale = factor;
    }

    public void SetRotation(float deg)
    {
        degrees = deg;
    }

    public void CenterAt(PointF position)
    {
        translation = new float[]{position.x, position.y};
    }

    public void UpdateVertexData()
    {
        LeftUpper = new float[] {BaseLeftUpper[0], BaseLeftUpper[1]};
        LeftLower = new float[] {BaseLeftLower[0], BaseLeftLower[1]};
        RightUpper = new float[] {BaseRightUpper[0], BaseRightUpper[1]};
        RightLower = new float[] {BaseRightLower[0], BaseRightLower[1]};

        //Calculate new width and height
        float newWidth = Math.abs(BaseLeftUpper[0] - BaseRightUpper[0])*scale;
        float newHeight = Math.abs(BaseLeftUpper[1] - BaseLeftLower[1])*scale;

        //Apply scaling
        LeftUpper[0] = -newWidth/2.0f;
        LeftUpper[1] = newHeight/2.0f;

        LeftLower[0] = -newWidth/2.0f;
        LeftLower[1] = -newHeight/2.0f;

        RightUpper[0] = newWidth/2.0f;
        RightUpper[1] = newHeight/2.0f;

        RightLower[0] = newWidth/2.0f;
        RightLower[1] = -newHeight/2.0f;


        //Apply rotation
        float sin = (float) Math.sin(Math.toRadians(degrees));
        float cos = (float) Math.cos(Math.toRadians(degrees));

        float lu0 = LeftUpper[0];
        float ll0 = LeftLower[0];
        float ru0 = RightUpper[0];
        float rl0 = RightLower[0];

        LeftUpper[0] = cos * LeftUpper[0] - sin * LeftUpper[1];
        LeftUpper[1] = sin * lu0 + cos * LeftUpper[1];

        LeftLower[0] = cos * LeftLower[0] - sin * LeftLower[1];
        LeftLower[1] = sin * ll0 + cos * LeftLower[1];

        RightUpper[0] = cos * RightUpper[0] - sin * RightUpper[1];
        RightUpper[1] = sin * ru0 + cos * RightUpper[1];

        RightLower[0] = cos * RightLower[0] - sin * RightLower[1];
        RightLower[1] = sin * rl0 + cos * RightLower[1];

        //Apply translation
        LeftUpper[0] += translation[0];
        LeftUpper[1] += translation[1];

        LeftLower[0] += translation[0];
        LeftLower[1] += translation[1];

        RightUpper[0] += translation[0];
        RightUpper[1] += translation[1];

        RightLower[0] += translation[0];
        RightLower[1] += translation[1];

        UpdateVertexBuffer();
    }

    public PointF GetCenter()
    {
        return null;
    }

    public PointF GetCorner(int number)
    {
        switch(number)
        {
            case 0:
                return new PointF(LeftUpper[0], LeftUpper[1]);
            case 1:
                return new PointF(RightUpper[0], RightUpper[1]);
            case 2:
                return new PointF(RightLower[0], RightLower[1]);
            case 3:
                return new PointF(LeftLower[0], LeftLower[1]);
        }
        return null;
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

    private void UpdateVertexBuffer()
    {
        vertices = new float[]{
                LeftUpper[0], LeftUpper[1], 0.0f,
                RightUpper[0], RightUpper[1], 0.0f,
                RightLower[0], RightLower[1], 0.0f,
                LeftLower[0], LeftLower[1], 0.0f
        };
        color = new float[] {R,G,B, 1.0f};

        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    private void UpdateDrawListBuffer()
    {
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }
}
