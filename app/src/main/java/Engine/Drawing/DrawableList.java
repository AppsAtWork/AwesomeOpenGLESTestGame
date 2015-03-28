package Engine.Drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Engine.Objects.IDrawable;

/**
 * Created by Casper on 3/26/2015.
 */
public class DrawableList
{
    private volatile List<IDrawable> Drawables;

    public DrawableList()
    {
        Drawables = new ArrayList<IDrawable>();
    }

    public void MoveToFront(IDrawable drawable)
    {
        Collections.rotate(Drawables.subList(Drawables.indexOf(drawable), Drawables.size()), -1);
    }

    public void MoveToBack(IDrawable drawable) throws Exception
    {
        throw new Exception();
    }

    public void MoveForward(IDrawable drawable)
    {
        int drawableIndex = Drawables.indexOf(drawable);
        Drawables.set(drawableIndex, Drawables.get(drawableIndex + 1));
        Drawables.set(drawableIndex + 1, drawable);
    }

    public void MoveBackward(IDrawable drawable)
    {
        int drawableIndex = Drawables.indexOf(drawable);
        Drawables.set(drawableIndex, Drawables.get(drawableIndex - 1));
        Drawables.set(drawableIndex - 1, drawable);
    }

    public void Add(IDrawable drawable)
    {
        Drawables.add(drawable);
    }

    public void Remove(IDrawable drawable)
    {
        Drawables.remove(drawable);
    }

    public IDrawable Get(int index)
    {
        return Drawables.get(index);
    }

    public int Size()
    {
        return Drawables.size();
    }
}
