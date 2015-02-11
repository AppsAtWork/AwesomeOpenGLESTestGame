package Engine.OpenGLObjects;

/**
 * Created by Casper on 11-2-2015.
 */
public abstract class OpenGLGeometry extends OpenGLObject
{
    protected float[] color;

    public void SetColor(float r, float g, float b, float alpha)
    {
        color = new float[] {r,g,b, alpha};
    }

}
