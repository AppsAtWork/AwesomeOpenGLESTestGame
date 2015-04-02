package Engine.Objects.Transformables.Geometries;

import android.graphics.PointF;

import Engine.Util.Vector2;

/*
 * Created by Casper on 2/12/2015.
 */
public class Line extends Geometry
{
    public float Thickness;

    public PointF P1() { return new PointF(vertices[0], vertices[1]);}
    public PointF P2() { return new PointF(vertices[3], vertices[4]);}

    public Line(PointF p1, PointF p2, float thickness)
    {
        Thickness = thickness/60.0f;

        Vector2 direction = new Vector2(p2.x - p1.x, p2.y - p1.y);
        direction.Normalize(Thickness/2.0f);
        Vector2 perpendicular = new Vector2(-direction.Y, direction.X);

        vertices = new float[] {
                p1.x + perpendicular.X/2.0f, p1.y + perpendicular.Y/2.0f, 0.0f,
                p2.x + perpendicular.X/2.0f, p2.y + perpendicular.Y/2.0f, 0.0f,
                p2.x - perpendicular.X/2.0f, p2.y - perpendicular.Y/2.0f, 0.0f,
                p1.x - perpendicular.X/2.0f, p1.y - perpendicular.Y/2.0f, 0.0f
        };

        PointF center = GetCenter();
        translation = new float[]{center.x, center.y};
        baseVertices = new float[] {
                p1.x + perpendicular.X/2.0f - center.x, p1.y + perpendicular.Y/2.0f - center.y, 0.0f,
                p2.x + perpendicular.X/2.0f - center.x, p2.y + perpendicular.Y/2.0f - center.y, 0.0f,
                p2.x - perpendicular.X/2.0f - center.x, p2.y - perpendicular.Y/2.0f - center.y, 0.0f,
                p1.x - perpendicular.X/2.0f - center.x, p1.y - perpendicular.Y/2.0f - center.y, 0.0f
        };
    }

    @Override
    public short[] GetDrawingList()
    {
        return new short[] {0,1,2,0,2,3};
    }
}
