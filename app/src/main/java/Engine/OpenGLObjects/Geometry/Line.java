package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Casper on 2/12/2015.
 */
public class Line extends OpenGLGeometry
{
    private float[] BaseP1;
    private float[] BaseP2;

    private float[] P1;
    private float[] P2;

    private float Thickness;

    public Line(PointF p1, PointF p2, float thickness, float r, float g, float b, float alpha)
    {
        BaseP1 = new float[]{p1.x, p1.y};
        BaseP2 = new float[]{p2.x, p2.y};
        Thickness = thickness;

        P1 = new float[] {p1.x, p1.y};
        P2 = new float[] { p2.x, p2.y};

        //Todo: correct BaseP1 to center around origin
        BaseP1[0] = BaseP1[0] - GetCenter().x;
        BaseP1[1] = BaseP1[1] - GetCenter().y;

        BaseP2[0] = BaseP2[0] - GetCenter().x;
        BaseP2[1] = BaseP2[1] - GetCenter().y;

        color = new float[] { r,g,b,alpha};
        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    @Override
    public PointF GetCenter() {
        return new PointF(((P1[0] + P2[0])/2.0f), ((P1[1] + P2[1])/2.0f));
    }

    @Override
    public void ApplyTransformations()
    {
        P1[0] = BaseP1[0];
        P1[1] = BaseP1[1];
        P2[0] = BaseP2[0];
        P2[1] = BaseP2[1];

        //Scale dem points
        P1[0] *= scale;
        P1[1] *= scale;
        P2[0] *= scale;
        P2[1] *= scale;

        //Rotate dem points
        float sin = (float) Math.sin(Math.toRadians(degrees));
        float cos = (float) Math.cos(Math.toRadians(degrees));

        float p10 = P1[0];
        float p20 = P2[0];

        P1[0] = cos * P1[0] - sin * P1[1];
        P1[1] = sin * p10 + cos * P1[1];

        P2[0] = cos * P2[0] - sin * P2[1];
        P2[1] = sin * p20+ cos * P2[1];

        //Translate dem points
        P1[0] += translation[0];
        P1[1] += translation[1];
        P2[0] += translation[0];
        P2[1] += translation[1];

        UpdateVertexBuffer();
    }

    @Override
    protected void UpdateVertexBuffer()
    {
        vertices = new float[] {
            P1[0], P1[1], 0.0f,
            P2[0], P2[1], 0.0f
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[] {
          0,1
        };
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }

    @Override
    public float Intersects(PointF point)
    {
        return DistanceToLine(point)/5.0f;
    }

    private float DistanceToLine(PointF point)
    {
        float upper = Math.abs((P2[1] - P1[1])*point.x - (P2[0]-P1[0])*point.y + P2[0]*P1[1]-P2[1]*P1[0]);
        return (float)(upper/(Math.sqrt((P2[1]-P1[1])*(P2[1]-P1[1]) + (P2[0] - P1[0])* (P2[0] - P1[0]))));

    }

    @Override
    public void Draw(float[] projectionViewMatrix, int program)
    {
        GLES20.glLineWidth(Thickness);
        //Get handle to the vPosition member of the shader
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        //Enable it as a vertex array: here we send the vertices of the line to.
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertices there.
        GLES20.glVertexAttribPointer(
                positionHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                0,
                vertexBuffer
        );

        //Projection matrix
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "uMVPMatrix"), 1, false, projectionViewMatrix, 0);

        //Drawing type
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "drawingType"), 0);

        //Now set the color
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(program, "vColor"), 1, color, 0);

        //I think we can draw now :)
        GLES20.glDrawElements(GLES20.GL_LINES, drawingOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        //Disable the vertex array.
        GLES20.glDisableVertexAttribArray(positionHandle);

        GLES20.glLineWidth(1.0f);
    }
}
