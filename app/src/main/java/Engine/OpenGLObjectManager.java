package Engine;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import Engine.OpenGLObjects.OpenGLObject;

/**
 * Created by Casper on 8-2-2015.
 */
public class OpenGLObjectManager
{
    //Hier kun je echt kekke shit doen met datastructuren.
    public volatile static List<OpenGLObject> Drawables = new ArrayList<>();

    //Returns the first object that a ray cast through this point into the scene intersects with.
    //So that is the top-most object at this point.
    public static OpenGLObject FirstIntersection(PointF point)
    {
        for(int i = Drawables.size()-1; i >= 0; i--)
        {
            if(Drawables.get(i).Intersects(point) < 0.005f)
            {
                return Drawables.get(i);}
        }
        return null;
    }

    public static void MoveToFront(OpenGLObject oglObject)
    {
        Collections.rotate(Drawables.subList(Drawables.indexOf(oglObject), Drawables.size()), -1);
    }

    public static void MoveForward(OpenGLObject oglObject)
    {
        int oglObjectIndex = Drawables.indexOf(oglObject);
        Drawables.set(oglObjectIndex, Drawables.get(oglObjectIndex + 1));
        Drawables.set(oglObjectIndex + 1, oglObject);
    }

    public static void MoveBackward(OpenGLObject oglObject)
    {
        int oglObjectIndex = Drawables.indexOf(oglObject);
        Drawables.set(oglObjectIndex, Drawables.get(oglObjectIndex - 1));
        Drawables.set(oglObjectIndex - 1, oglObject);
    }
}
