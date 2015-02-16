package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Engine.OpenGLObjects.OpenGLColor;
import Engine.Util;

/**
 * Created by Casper on 16-2-2015.
 */
public class RegularPolygon extends OpenGLGeometry {

    public PointF Center() { return new PointF(translation[0], translation[1]);}
    public float Radius() { return BaseRadius * scale; }
    public float BaseRadius;

    public RegularPolygon(float centerX, float centerY, float radius, float n, OpenGLColor col){
        SetColor(col);
        baseVertices = GenerateVertices(0,0,radius,n);
        vertices = GenerateVertices(centerX,centerY,radius,n);
        translation = new float[]{centerX, centerY};
        BaseRadius = radius;
        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    private float[] GenerateVertices(float x, float y, float r, float n)
    {
        float[] vertices = new float[((int)n + 1)*3 + 6];
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = 0.0f;
        int i = 3;
        for(float factor = 0; factor < 2; factor = factor + 2.0f/n)
        {
            vertices[i++] = ((float)(r * Math.cos(Math.PI * factor)) + x);
            vertices[i++] = ((float)(r * Math.sin(Math.PI * factor)) + y);
            vertices[i++] = (0.0f);
        }
        vertices[i++] = ((float)(r * Math.cos(Math.PI * 2)) + x);
        vertices[i++] = ((float)(r * Math.sin(Math.PI * 2)) + y);
        vertices[i++] = (0.0f);

        return vertices;
    }

    @Override
    protected void UpdateDrawListBuffer() {
        drawingOrder = new short[vertices.length - 3];
        drawingOrder[0] = 0;
        int vertexNumber = 1;
        for(int i = 1; i < drawingOrder.length-3; i+=3)
        {
            drawingOrder[i] = (short)(vertexNumber);
            drawingOrder[i+1] = (short)(++vertexNumber);
            drawingOrder[i+2] = 0;
        }
        CreateDrawListBuffer();
    }

    @Override
    public float Intersects(PointF point)
    {
        return Util.Distance(Center(), point) - Radius();
    }
}
