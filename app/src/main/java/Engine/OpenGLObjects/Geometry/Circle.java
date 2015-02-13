package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import Engine.Util;

/**
 * Created by Casper on 2/12/2015.
 */
public class Circle extends OpenGLGeometry
{
    private float precision = 0.05f;
    @Override
    public PointF Center() { return new PointF(translation[0], translation[1]);}
    public float Radius() { return BaseRadius * scale; }
    public float BaseRadius;

    public Circle(float centerX, float centerY, float radius, float r, float g, float b, float alpha)
    {
        color = new float[] {r,g,b,alpha};

        baseVertices = GenerateCircleVertices(0,0, radius);
        vertices = GenerateCircleVertices(centerX, centerY, radius);
        translation = new float[] {centerX, centerY};
        BaseRadius = radius;
        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    @Override
    protected void UpdateVertexBuffer()
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    private float[] GenerateCircleVertices(float x, float y, float r)
    {
        float[] vertices = new float[(int)(3.0f * 2.0f/precision)+6] ;
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = 0.0f;
        int i = 3;
        for(float factor = 0; factor <= 2 + precision/2.0f; factor = factor + precision)
        {
            vertices[i++] = ((float)(r * Math.cos(Math.PI * factor)) + x);
            vertices[i++] = ((float)(r * Math.sin(Math.PI * factor)) + y);
            vertices[i++] = (0.0f);
        }
        return vertices;
    }

    @Override
    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[vertices.length-3];
        drawingOrder[0] = 0;
        int vertexNumber = 1;
        for(int i = 1; i < drawingOrder.length-3; i+=3)
        {
            drawingOrder[i] = (short)(vertexNumber);
            drawingOrder[i+1] = (short)(++vertexNumber);
            drawingOrder[i+2] = 0;
        }

        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }

    @Override
    public float Intersects(PointF point)
    {
        return Util.Distance(Center(), point) - Radius() - 0.2f;
    }
}
