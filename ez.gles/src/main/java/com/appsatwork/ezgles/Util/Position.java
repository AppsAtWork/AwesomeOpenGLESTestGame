package com.appsatwork.ezgles.Util;

import android.graphics.PointF;

/**
 * Created by Casper on 22-2-2015.
 */
public class Position
{
    public float X;
    public float Y;

    public Position(float x, float y)
    {
        X = x;
        Y = y;
    }

    public void Move(Velocity velocity)
    {
        X += velocity.SpeedVector.X;
        Y += velocity.SpeedVector.Y;
    }

    public PointF GetPoint()
    {
        return new PointF(X,Y);

    }
}
