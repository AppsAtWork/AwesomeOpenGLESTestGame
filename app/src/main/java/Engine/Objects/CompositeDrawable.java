package Engine.Objects;

import java.util.ArrayList;
import java.util.List;

import Engine.Objects.Transformables.CompositeTransformable;
import Engine.Objects.Transformables.Geometry;
import Engine.Objects.Transformables.ITransform;

/**
 * Created by Casper on 1-4-2015.
 */
public class CompositeDrawable implements IDrawable
{
    private List<IDrawable> drawables;
    private CompositeTransformable transformable;

    public CompositeDrawable(List<IDrawable> drawables)
    {
        this.drawables = drawables;
        List<ITransform> transformables = new ArrayList<>();
        for(IDrawable drawable : drawables)
            transformables.add(drawable.GetTransformable());
        transformable = new CompositeTransformable(transformables);
    }
    @Override
    public CompositeTransformable GetTransformable()
    {
        return transformable;
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        for(IDrawable drawable : drawables)
            drawable.Draw(projectionViewMatrix, programHandle);
    }
}
