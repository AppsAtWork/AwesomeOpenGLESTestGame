package Engine.Util;

/**
 * Created by Casper on 16-2-2015.
 */
public class Color
{
    public float R;
    public float G;
    public float B;
    public float Alpha;

    public Color(float r, float g, float b, float alpha)
    {
        R=r; G=g; B=b; Alpha = alpha;
    }

    public Color(float[] array)
    {
        R=array[0]; G=array[1]; B=array[2]; Alpha = array[3];
    }

    public float[] GetFloatArray()
    {
        return new float[]{R,G,B,Alpha};
    }
}
