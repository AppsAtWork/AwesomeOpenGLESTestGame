package Engine.OpenGLObjects.Sprites.UVCoordProviders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Casper on 21-2-2015.
 */
public abstract class TextureProvider
{
    public int TextureSlot;
    public int TextureHandle;
    private Resources resources;
    public int ResourceID;

    public TextureProvider(Resources resources, int resID)
    {
        this.resources = resources;
        this.ResourceID = resID;
    }

    public Bitmap GetBitmap()
    {
        return BitmapFactory.decodeResource(resources, ResourceID);
    }

    public abstract float[] GetUVCoords(int index);
}
