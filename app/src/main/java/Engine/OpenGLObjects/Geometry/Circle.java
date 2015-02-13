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
    private float[] BaseCenter;
    private float[] Center;
    private float BaseRadius;
    private float Radius;

    public Circle(float centerX, float centerY, float radius, float r, float g, float b, float alpha)
    {
        color = new float[] {r,g,b,alpha};
        BaseCenter = new float[] { 0, 0 };
        Center = new float[] {centerX, centerY};
        BaseRadius = radius;
        Radius = radius;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    @Override
    public PointF GetCenter() {
        return null;
    }

    @Override
    public void ApplyTransformations()
    {
        Center[0] = translation[0];
        Center[1] = translation[1];

        UpdateVertexBuffer();
    }

    boolean regenerate = true;

    @Override
    protected void UpdateVertexBuffer()
    {
        if(regenerate)
            vertices = GenerateCircleVertices();
        else
        {
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    private float[] GenerateCircleVertices()
    {
        float[] vertices = new float[(int)(3.0f * 2.0f/0.05f)+6] ;
        vertices[0] = Center[0];
        vertices[1] = Center[1];
        vertices[2] = 0.0f;
        int i = 3;
        for(float factor = 0; factor <= 2; factor = factor + 0.05f)
        {
            vertices[i++] = ((float)(Radius * Math.cos(Math.PI * factor) + translation[0]));
            vertices[i++] = ((float)(Radius * Math.sin(Math.PI * factor) + translation[1]));
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
    public float Intersects(PointF point) {
        return Util.Distance(point, new PointF(Center[0], Center[1]))/10.0f;
    }
}
