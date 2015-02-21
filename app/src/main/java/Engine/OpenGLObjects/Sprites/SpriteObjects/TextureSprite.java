package Engine.OpenGLObjects.Sprites.SpriteObjects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.OpenGLSprite;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.Texture;

/**
 * Created by Casper on 17-2-2015.
 */
public class TextureSprite extends OpenGLSprite
{
    public TextureSprite(Texture tex, float centerX, float centerY, float width, float height, FittingType type)
    {
        super(tex, centerX, centerY, width, height, type);
        UpdateUVBuffer();
    }

    protected void UpdateUVBuffer()
    {
        uvs = textureProvider.GetUVCoords(0);
        CreateUVBuffer();
    }
}
