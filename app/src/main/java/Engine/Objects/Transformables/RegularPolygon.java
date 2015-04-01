package Engine.Objects.Transformables;

import android.graphics.PointF;

/**
 * Created by Casper on 16-2-2015.
 */
public class RegularPolygon extends Geometry
{
    public float Radius() { return BaseRadius * scale; }
    public float Corners;
    private float BaseRadius;

    public RegularPolygon(PointF center, float radius, float n)
    {
        this.Corners = n;

        baseVertices = GenerateVertices(new PointF(0,0),radius,n);

        vertices = GenerateVertices(center,radius,n);
        translation = new float[]{center.x, center.y};

        BaseRadius = radius;
    }

    private float[] GenerateVertices(PointF center, float r, float n)
    {
        float[] vertices = new float[((int)n + 1)*3 + 6];
        vertices[0] = center.x;
        vertices[1] = center.y;
        vertices[2] = 0.0f;
        int i = 3;
        for(float factor = 0; factor < 2; factor = factor + 2.0f/n)
        {
            vertices[i++] = ((float)(r * Math.cos(Math.PI * factor)) + center.x);
            vertices[i++] = ((float)(r * Math.sin(Math.PI * factor)) + center.y);
            vertices[i++] = (0.0f);
        }
        vertices[i++] = ((float)(r * Math.cos(Math.PI * 2)) + center.x);
        vertices[i++] = ((float)(r * Math.sin(Math.PI * 2)) + center.y);
        vertices[i++] = (0.0f);

        return vertices;
    }
    @Override
    public short[] GetDrawingList()
    {
        short[] drawingOrder = new short[((int)Corners + 1)*3 + 6 - 3];
        drawingOrder [0] = 0;
        int vertexNumber = 1;
        for(int i = 1; i < drawingOrder .length-3; i+=3)
        {
            drawingOrder [i] = (short)(vertexNumber);
            drawingOrder [i+1] = (short)(++vertexNumber);
            drawingOrder [i+2] = 0;
        }
        return drawingOrder;
    }
}
