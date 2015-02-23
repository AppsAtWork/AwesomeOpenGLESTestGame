package Engine.OpenGLObjects.Sprites.UVCoordProviders;

import android.content.res.Resources;

/**
 * Created by Casper on 17-2-2015.
 */
public class Texture extends TextureProvider
{
    public Texture(Resources res, int resID) { super(res, resID); }

    @Override
    public float[] GetUVCoords(int index) {
        return new float[]
                {
                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f
                };
    }
}
