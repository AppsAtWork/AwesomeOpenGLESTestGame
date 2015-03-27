package Engine.Objects.Geometry;

import android.graphics.PointF;

import Engine.Drawing.Drawers.OpenGLGeometryDrawer;
import Engine.Drawing.DrawingListGenerators.RegularPolygonDrawingListGenerator;
import Engine.Util.Color;

/**
 * Created by Casper on 16-2-2015.
 */
public class RegularPolygon extends OpenGLGeometry {

    public PointF Center() { return new PointF(translation[0], translation[1]);}
    public float Radius() { return BaseRadius * scale; }
    public float BaseRadius;

    public RegularPolygon(float centerX, float centerY, float radius, float n, Color col){
        SetColor(col);
        baseVertices = GenerateVertices(0,0,radius,n);
        vertices = GenerateVertices(centerX,centerY,radius,n);
        translation = new float[]{centerX, centerY};
        BaseRadius = radius;
        this.DrawingListGenerator = new RegularPolygonDrawingListGenerator(n);
        this.drawer = new OpenGLGeometryDrawer(this);
        UpdateVertexBuffer();
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
    public float Intersects(PointF point)
    {
        return Engine.Util.Distance.Distance(Center(), point) - Radius();
    }
}
