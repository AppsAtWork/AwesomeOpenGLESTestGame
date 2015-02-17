package Engine.OpenGLObjects.Sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Casper on 17-2-2015.
 */
public class Texture
{
    public int TextureSlot = -1;
    public int TextureHandle = -1;
    private Resources resources;
    private int id;

    public Texture(Resources res, int resID)
    {
        resources = res;
        id = resID;
    }

    public Bitmap GetBitmap()
    {
        return BitmapFactory.decodeResource(resources, id);
    }

    public float[] GetUVCoords()
    {
        return new float[]
                {
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    1.0f, 1.0f,
                    0.0f, 1.0f
                };
    }
}
