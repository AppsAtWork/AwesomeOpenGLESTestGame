package Engine.OpenGLObjects.Sprites.SpriteObjects;

import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.SimpleTextureAtlas;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 11-2-2015.
 */
public class AtlasSprite extends OpenGLSprite
{
    public AtlasSprite(TextureProvider atlas, int textureIndex, float centerX, float centerY, float width, float height, FittingType type)
    {
        super(atlas, centerX, centerY, width, height, type);
        TextureIndex = textureIndex;
        UpdateUVBuffer();
    }
}
