package Engine.Drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Engine.Objects.OpenGLObject;

/**
 * Created by Casper on 3/26/2015.
 */
public class DrawableList
{
    private volatile static List<OpenGLObject> Drawables;

    public DrawableList()
    {
        Drawables = new ArrayList<OpenGLObject>();
    }

    public void MoveToFront(OpenGLObject oglObject)
    {
        Collections.rotate(Drawables.subList(Drawables.indexOf(oglObject), Drawables.size()), -1);
    }

    public void MoveToBack(OpenGLObject oglObject) throws Exception
    {
        throw new Exception();
    }

    public void MoveForward(OpenGLObject oglObject)
    {
        int oglObjectIndex = Drawables.indexOf(oglObject);
        Drawables.set(oglObjectIndex, Drawables.get(oglObjectIndex + 1));
        Drawables.set(oglObjectIndex + 1, oglObject);
    }

    public void MoveBackward(OpenGLObject oglObject)
    {
        int oglObjectIndex = Drawables.indexOf(oglObject);
        Drawables.set(oglObjectIndex, Drawables.get(oglObjectIndex - 1));
        Drawables.set(oglObjectIndex - 1, oglObject);
    }

    public void Add(OpenGLObject oglObject)
    {
        Drawables.add(oglObject);
    }

    public void Remove(OpenGLObject oglObject)
    {
        Drawables.remove(oglObject);
    }

    public OpenGLObject Get(int index)
    {
        return Drawables.get(index);
    }

    public int Size()
    {
        return Drawables.size();
    }
}
