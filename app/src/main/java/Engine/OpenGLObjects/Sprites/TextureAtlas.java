package Engine.OpenGLObjects.Sprites;

import android.graphics.Bitmap;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureAtlas
{
    public int TextureResolution = 256;
    public Bitmap bitmap;
    public static int AtlasResolution = 512;

    public TextureAtlas(int textureRes, Bitmap bmp)
    {
        TextureResolution = textureRes;
        bitmap = bmp;
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

        /*
         uvs = new float[] {
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f
        };
         */

        return new float [] {
            x1, y1,
            x2, y1,
            x2, y2,
            x1, y2
        };
    }
}
