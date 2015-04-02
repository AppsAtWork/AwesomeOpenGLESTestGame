package Engine.Objects.Drawables;

import Engine.Objects.Transformables.ITransform;

/**
 * Created by Casper on 27-3-2015.
 */
public interface IDrawable
{
    public ITransform GetTransformable();

    public void Draw(float[] projectionViewMatrix, int programHandle);
}
