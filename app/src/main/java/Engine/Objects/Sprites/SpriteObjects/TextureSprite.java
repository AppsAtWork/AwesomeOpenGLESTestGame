package Engine.Objects.Sprites.SpriteObjects;

import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.UVCoordProviders.TextureProvider;

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
