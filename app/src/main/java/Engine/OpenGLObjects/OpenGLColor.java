package Engine.OpenGLObjects;

/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLColor {
    public float R;
    public float G;
    public float B;
    public float Alpha;

    public OpenGLColor(float r, float g, float b, float alpha)
    {
        R=r; G=g; B=b; Alpha = alpha;
    }

    public float[] GetFloatArray()
    {
        return new float[]{R,G,B,Alpha};
    }
}
