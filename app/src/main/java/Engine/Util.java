package Engine;

import android.graphics.PointF;

/**
 * Created by Casper on 8-2-2015.
 */
public class Util
{
    public static float Distance(PointF p1, PointF p2)
    {
        float xDist = Math.abs(p1.x - p2.x);
        float yDist = Math.abs(p1.y - p2.y);
        return (float)Math.sqrt(xDist * xDist + yDist * yDist);
    }
}
