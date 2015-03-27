package Engine.Objects.Geometry;

import android.graphics.PointF;

import Engine.Drawing.Drawers.OpenGLGeometryDrawer;
import Engine.Drawing.DrawingListGenerators.TriangleDrawingListGenerator;

/**
 * Created by Casper on 11-2-2015.
 */
public class Triangle extends OpenGLGeometry
{
    public PointF P1() { return new PointF(vertices[0], vertices[1]);}
    public PointF P2() { return new PointF(vertices[3], vertices[4]);}
    public PointF P3() { return new PointF(vertices[6], vertices[7]);}
    @Override
    public PointF Center() { return new PointF((1.0f/3.0f) * (P1().x + P2().x + P3().x), (1.0f/3.0f) * (P1().y + P2().y + P3().y)); }

    public Triangle(PointF point1, PointF point2, PointF point3, float r, float g, float b, float a)
    {
        color = new float[] {r,g,b,a};

        vertices = new float[] {
              point1.x, point1.y, 0.0f,
              point2.x, point2.y, 0.0f,
              point3.x, point3.y, 0.0f
        };

        PointF center = Center();

        baseVertices = new float[]
                {
                  point1.x - center.x, point1.y - center.y, 0.0f,
                  point2.x - center.x, point2.y - center.y, 0.0f,
                  point3.x - center.x, point3.y - center.y, 0.0f
                };
        this.drawer = new OpenGLGeometryDrawer(this);
        this.DrawingListGenerator = new TriangleDrawingListGenerator();
        UpdateVertexBuffer();
    }

    @Override
    public float Intersects(PointF p)
    {
        PointF p1 = P1();
        PointF p2 = P2();
        PointF p3 = P3();

        float alpha = ((p2.y - p3.y)*(p.x - p3.x) + (p3.x - p2.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        float beta = ((p3.y - p1.y)*(p.x - p3.x) + (p1.x - p3.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        float gamma = 1.0f - alpha - beta;

        if(alpha < 0 || beta < 0 || gamma < 0)
            return Math.abs(Math.min(alpha, (Math.min(beta, gamma))));
        else
            return 0;
    }
}
