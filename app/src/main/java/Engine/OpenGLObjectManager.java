package Engine;

import android.graphics.PointF;

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
    public static List<OpenGLObject> Drawables = new ArrayList<>();
    private static OpenGLObject Grabbed;
    //Returns the first object that a ray cast through this point into the scene intersects with.
    //So that is the top-most object at this point.
    public static OpenGLObject FirstIntersection(PointF point)
    {
        if(Grabbed != null) {

            if(Grabbed.Intersects(point) < 0.15f)
                return Grabbed;
            else
                Grabbed = null;
        }
        for(int i = Drawables.size()-1; i >= 0; i--)
        {
            if(Drawables.get(i).Intersects(point) < 0.005f)
            {
                Grabbed = Drawables.get(i);
                return Drawables.get(i);}
        }
        return null;
    }

    public static void MoveToFront(OpenGLObject oglObject)
    {
        Collections.rotate(Drawables.subList(Drawables.indexOf(oglObject), Drawables.size()), -1);
    }
}
