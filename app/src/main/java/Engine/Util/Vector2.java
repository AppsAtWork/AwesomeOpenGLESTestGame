package Engine.Util;

/**
 * Created by Casper on 22-2-2015.
 */
public class Vector2
{
    public float X;
    public float Y;

    public Vector2(float x, float y)
    {
        X = x;
        Y = y;
    }

    public void Normalize(float length)
    {
        float l = length / Length();

        X = X * l;
        Y = Y * l;
    }

    public float Length()
    {
        return (float) Math.sqrt(X*X + Y *Y);
    }


    public Vector2 Clone()
    {
        return new Vector2(X,Y);
    }
}
