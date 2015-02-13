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
    private static List<OpenGLObject> Drawables = new ArrayList<>();
    private static List<OpenGLObject> NonStaticDrawables = new ArrayList<>();

    public static List<OpenGLObject> GetDrawables() {return Drawables;}

    public static void AddDrawable(OpenGLObject oglObject, boolean itsStatic)
    {
        if(!itsStatic)
        {
            NonStaticDrawables.add(oglObject);
        }
        Drawables.add(oglObject);
    }


    //Returns the first object that a ray cast through this point into the scene intersects with.
    //So that is the top-most object at this point.
    public static OpenGLObject FirstIntersection(PointF point)
    {
        for(int i = NonStaticDrawables.size()-1; i >= 0; i--)
        {
            if(NonStaticDrawables.get(i).Intersects(point) < 0.025f)
                return NonStaticDrawables.get(i);
        }
        return null;
    }

    public static void MoveToFront(OpenGLObject oglObject)
    {
        Collections.rotate(Drawables.subList(Drawables.indexOf(oglObject), Drawables.size()), -1);
    }
}
