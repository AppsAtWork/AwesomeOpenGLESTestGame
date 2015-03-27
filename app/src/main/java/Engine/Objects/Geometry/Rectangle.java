package Engine.Objects.Geometry;

import android.graphics.PointF;

import Engine.Drawing.Drawers.OpenGLGeometryDrawer;
import Engine.Drawing.DrawingListGenerators.RectangleDrawingListGenerator;

/**
 * Created by Casper on 7-2-2015.
 */
public class Rectangle extends OpenGLGeometry
{
    public float Width() {return BaseWidth*scale;}
    public float Height() {return BaseHeight * scale;}

    private float BaseWidth;
    private float BaseHeight;

    public PointF Center() { return new PointF((vertices[0] + vertices[3])/2.0f, (vertices[7] + vertices[1]) / 2.0f); }

    public PointF LeftUpper()
    {
        return new PointF(vertices[0], vertices[1]);
    }

    public PointF RightUpper()
    {
        return new PointF(vertices[3], vertices[4]);
    }

    public PointF RightLower()
    {
        return new PointF(vertices[6], vertices[7]);
    }

    public PointF LeftLower()
    {
        return new PointF(vertices[9], vertices[10]);
    }

    public float Area()
    {
        return Width() * Height();
    }

    public Rectangle(float centerX, float centerY, float width, float height, float r, float g, float b, float a) {
        baseVertices = new float[]{
                -width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f, -height / 2.0f, 0.0f
        };

        vertices = new float[]{
                centerX - width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY - height / 2.0f, 0.0f,
                centerX - width / 2.0f, centerY - height / 2.0f, 0.0f
        };

        translation = new float[]{centerX, centerY};

        color = new float[]{r, g, b, a};
        this.DrawingListGenerator = new RectangleDrawingListGenerator();
        this.drawer = new OpenGLGeometryDrawer(this);
        BaseWidth = width;
        BaseHeight = height;

        UpdateVertexBuffer();
    }

    @Override
    public float Intersects(PointF point)
    {
        float triangleSum =
                TriangleArea(LeftLower(), point, RightLower()) +
                TriangleArea(RightLower(), point, RightUpper()) +
                TriangleArea(RightUpper(), point, LeftUpper()) +
                TriangleArea(point, LeftUpper(), LeftLower());

        return triangleSum - Area();
    }

    private float TriangleArea(PointF a, PointF b, PointF c)
    {
        return Math.abs(a.x*(b.y-c.y)+b.x*(c.y-a.y)+c.x*(a.y-b.y))/2.0f;
    }
}
