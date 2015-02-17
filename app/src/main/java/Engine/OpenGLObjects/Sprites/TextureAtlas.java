package Engine.OpenGLObjects.Sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureAtlas
{
    public int ResourceID;
    public int TextureResolution = 256;
    public static int AtlasResolution = 512;
    public int TextureSlot = -1;
    public int TextureHandle = -1;
    private Resources resources;

    public TextureAtlas(int textureRes, Resources res, int resourceID)
    {
        resources = res;
        ResourceID = resourceID;
        TextureResolution = textureRes;
    }

    public Bitmap GetBitmap()
    {
        return BitmapFactory.decodeResource(resources, ResourceID);
    }

    public float[] GetUVCoords(int textureIndex)
    {
        int texturesPerRow = AtlasResolution / TextureResolution;
        float normalizedTextureResolution = TextureResolution / (float)AtlasResolution;

        int column = textureIndex % texturesPerRow;
        int row = textureIndex / texturesPerRow;

        float x1 = normalizedTextureResolution * row;
        float x2 = normalizedTextureResolution * (row + 1);

        float y1 = normalizedTextureResolution * column;
        float y2 = normalizedTextureResolution * (column + 1);

        return new float [] {
            x1, y1,
            x2, y1,
            x2, y2,
            x1, y2
        };
    }
}
