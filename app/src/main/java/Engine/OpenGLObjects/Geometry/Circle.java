package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;

import Engine.OpenGLObjects.OpenGLColor;
import Engine.Util;

/**
 * Created by Casper on 2/12/2015.
 */
public class Circle extends RegularPolygon
    {
    static final int PRECISION = 40;
    public Circle(float centerX, float centerY, float radius, OpenGLColor col){
        super(centerX,centerY,radius,PRECISION,col);
    }

    @Override
    public float Intersects(PointF point)
    {
        return Util.Distance(Center(), point) - Radius();
    }
}
