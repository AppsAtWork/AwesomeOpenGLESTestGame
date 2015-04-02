package Engine.Objects.Transformables.Geometries;

import android.graphics.PointF;

/**
 * Created by Casper on 11-2-2015.
 */
public class Triangle extends Geometry
{
    public PointF P1() { return new PointF(vertices[0], vertices[1]);}
    public PointF P2() { return new PointF(vertices[3], vertices[4]);}
    public PointF P3() { return new PointF(vertices[6], vertices[7]);}

    public Triangle(PointF point1, PointF point2, PointF point3)
    {
        vertices = new float[] {
              point1.x, point1.y, 0.0f,
              point2.x, point2.y, 0.0f,
              point3.x, point3.y, 0.0f
        };

        PointF center = GetCenter();

        baseVertices = new float[]
                {
                  point1.x - center.x, point1.y - center.y, 0.0f,
                  point2.x - center.x, point2.y - center.y, 0.0f,
                  point3.x - center.x, point3.y - center.y, 0.0f
                };
    }

    @Override
    public short[] GetDrawingList()
    {
        return new short[] {0,1,2};
    }
}
