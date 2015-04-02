package Engine.Drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Engine.Objects.Drawables.IDrawable;

/**
 * Created by Casper on 3/26/2015.
 */
public class DrawableList
{
    private volatile List<IDrawable> drawables;

    public DrawableList()
    {
        drawables = new ArrayList<IDrawable>();
    }

    public void MoveToFront(IDrawable drawable)
    {
        Collections.rotate(drawables.subList(drawables.indexOf(drawable), drawables.size()), -1);
    }

    public void MoveToBack(IDrawable drawable) throws Exception
    {
        throw new Exception();
    }

    public void MoveForward(IDrawable drawable)
    {
        int drawableIndex = drawables.indexOf(drawable);
        drawables.set(drawableIndex, drawables.get(drawableIndex + 1));
        drawables.set(drawableIndex + 1, drawable);
    }

    public void MoveBackward(IDrawable drawable)
    {
        int drawableIndex = drawables.indexOf(drawable);
        drawables.set(drawableIndex, drawables.get(drawableIndex - 1));
        drawables.set(drawableIndex - 1, drawable);
    }

    public void Add(IDrawable drawable)
    {
        drawables.add(drawable);
    }

    public void Remove(IDrawable drawable)
    {
        drawables.remove(drawable);
    }

    public IDrawable Get(int index)
    {
        return drawables.get(index);
    }

    public int Size()
    {
        return drawables.size();
    }
}
