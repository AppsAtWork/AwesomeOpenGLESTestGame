package Engine.OpenGLObjects.Sprites.SpriteObjects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.OpenGLSprite;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.Texture;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 17-2-2015.
 */
public class TextureSprite extends OpenGLSprite
{
    public TextureSprite(TextureProvider tex, float centerX, float centerY, float width, float height, FittingType type)
    {
        super(tex, centerX, centerY, width, height, type);
        UpdateUVBuffer();
    }
}
