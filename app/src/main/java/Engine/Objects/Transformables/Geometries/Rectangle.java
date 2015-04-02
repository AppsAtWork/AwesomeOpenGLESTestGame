package Engine.Objects.Transformables.Geometries;

import android.graphics.PointF;

/**
 * Created by Casper on 7-2-2015.
 */
public class Rectangle extends Geometry
{
    public float Width() {return BaseWidth*scale;}
    public float Height() {return BaseHeight * scale;}

    private float BaseWidth;
    private float BaseHeight;

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

    public Rectangle(PointF center, float width, float height) {
        baseVertices = new float[]{
                -width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f, -height / 2.0f, 0.0f
        };

        vertices = new float[]{
                center.x - width / 2.0f, center.y + height / 2.0f, 0.0f,
                center.x + width / 2.0f, center.y + height / 2.0f, 0.0f,
                center.x + width / 2.0f, center.y - height / 2.0f, 0.0f,
                center.x - width / 2.0f, center.y - height / 2.0f, 0.0f
        };

        translation = new float[]{center.x, center.y};

        BaseWidth = width;
        BaseHeight = height;
    }

    @Override
    public short[] GetDrawingList()
    {
        return new short[] {0,1,2,0,2,3};
    }
}
