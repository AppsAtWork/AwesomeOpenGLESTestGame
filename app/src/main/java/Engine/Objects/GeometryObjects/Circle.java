package Engine.Objects.GeometryObjects;

import android.graphics.PointF;

/**
 * Created by Casper on 27-3-2015.
 */
public class Circle extends RegularPolygon
{
    public Circle(PointF center, float radius)
    {
        super(center, radius, 40);
    }
}
