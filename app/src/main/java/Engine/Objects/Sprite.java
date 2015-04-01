package Engine.Objects;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Drawing.TextureDrawer;
import Engine.Objects.GeometryObjects.Rectangle;
import Engine.Objects.TextureObjects.UVCoordProviders.SimpleTextureAtlas;
import Engine.Objects.TextureObjects.UVCoordProviders.Texture;
import Engine.Objects.TextureObjects.UVCoordProviders.TextureProvider;
import Engine.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;

/**
 * Created by Casper on 27-3-2015.
 */
public class Sprite implements IDrawable
{
    private Rectangle Rectangle;
    private TextureProvider TextureProvider;
    private FloatBuffer UVBuffer;

    private TextureDrawer drawer;

    public Sprite(Rectangle rectangle, Texture provider)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(0));
    }

    public Sprite(Rectangle rectangle, SimpleTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }

    public Sprite(Rectangle rectangle, VariableTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }


    public void SetGeometry(Rectangle rectangle)
    {
        this.Rectangle = rectangle;
    }

    public Rectangle GetGeometry()
    {
        return this.Rectangle;
    }

    public void Draw(float[] projectionViewMatrix, int program)
    {
        if(drawer == null)
            drawer = new TextureDrawer(program);

        drawer.Draw(projectionViewMatrix, TextureProvider, Rectangle.GetVertices(), UVBuffer);
    }
}
