package Engine.OpenGLObjects.Sprites.SpriteObjects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureAtlas;

/**
 * Created by Casper on 11-2-2015.
 */
public class AtlasSprite extends OpenGLSprite
{
    private int TextureIndex;

    public AtlasSprite(TextureAtlas atlas, int textureIndex, float centerX, float centerY, float width, float height, FittingType type)
    {
        super(atlas, centerX, centerY, width, height, type);
        TextureIndex = textureIndex;
        UpdateUVBuffer();
    }

    protected void UpdateUVBuffer()
    {
        uvs = textureProvider.GetUVCoords(TextureIndex);
        CreateUVBuffer();
    }

}
