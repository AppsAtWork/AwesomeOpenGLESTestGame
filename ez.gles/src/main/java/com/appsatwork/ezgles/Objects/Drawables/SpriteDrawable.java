package com.appsatwork.ezgles.Objects.Drawables;

import java.nio.FloatBuffer;

import com.appsatwork.ezgles.Drawing.TextureDrawer;
import com.appsatwork.ezgles.Util.BufferBuilder;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Rectangle;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.SimpleTextureAtlas;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.Texture;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.TextureProvider;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;

/**
 * Created by Casper on 27-3-2015.
 */
public class SpriteDrawable implements IDrawable
{
    private Rectangle Rectangle;
    private TextureProvider TextureProvider;
    private FloatBuffer UVBuffer;

    private TextureDrawer drawer;

    public SpriteDrawable(Rectangle rectangle, Texture provider)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(0));
    }

    public SpriteDrawable(Rectangle rectangle, SimpleTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }

    public SpriteDrawable(Rectangle rectangle, VariableTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }

    public Rectangle GetTransformable()
    {
        return this.Rectangle;
    }

    public void Draw(float[] projectionViewMatrix)
    {
        if(drawer == null)
            drawer = new TextureDrawer();

        drawer.Draw(projectionViewMatrix, TextureProvider, Rectangle.GetVertices(), UVBuffer);
    }
}
