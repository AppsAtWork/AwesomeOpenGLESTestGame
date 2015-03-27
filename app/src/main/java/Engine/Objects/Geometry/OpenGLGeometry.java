package Engine.Objects.Geometry;

import Engine.Objects.OpenGLObject;
import Engine.Util.Color;

/**
 * Created by Casper on 11-2-2015.
 */
public abstract class OpenGLGeometry extends OpenGLObject
{
    protected float[] color;

    public void SetColor(Color col)
    {
        color = new float[] {col.R, col.G, col.B, col.Alpha};
    }

    public Color GetColor() { return new Color(color[0], color[1],color[2],color[3]);}
}
