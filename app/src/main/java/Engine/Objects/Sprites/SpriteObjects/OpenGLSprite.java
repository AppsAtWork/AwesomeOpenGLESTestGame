package Engine.Objects.Sprites.SpriteObjects;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import Engine.Drawing.Drawers.TextureDrawer;
import Engine.Drawing.DrawingListGenerators.RectangleDrawingList;
import Engine.Objects.OpenGLObject;
import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 21-2-2015.
 */
public class OpenGLSprite extends OpenGLObject
{
    protected float uvs[] = new float[8];
    protected FloatBuffer uvBuffer;

    public float Width() {return BaseWidth * scale;}
    public float Height() {return BaseHeight * scale;}

    protected float BaseWidth;
    protected float BaseHeight;

    public TextureProvider TextureProvider;
    protected int TextureIndex;

    @Override
    public PointF Center()
    {
        float xSum = 0;
        float ySum = 0;
        int count = 0;
        for(int i = 0; i < vertices.length; i += 3)
        {
            count += 2;
            xSum += vertices[i];
            ySum += vertices[i+1];
        }
        return new PointF(xSum / count, ySum / count);
    }

    public PointF LeftUpper() { return new PointF(vertices[0], vertices[1]); }

    public PointF RightUpper() { return new PointF(vertices[3], vertices[4]); }

    public PointF RightLower()
    {
        return new PointF(vertices[6], vertices[7]);
    }

    public PointF LeftLower()
    {
        return new PointF(vertices[9], vertices[10]);
    }

    public float Area() { return Width() * Height(); }

    public OpenGLSprite(TextureProvider provider, float centerX, float centerY, float width, float height, FittingType fittingType)
    {
        TextureProvider = provider;

        baseVertices = new float[]{
                -width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f, -height / 2.0f, 0.0f
        };

        vertices = new float[]{
                centerX - width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY - height / 2.0f, 0.0f,
                centerX - width / 2.0f, centerY - height / 2.0f, 0.0f
        };

        translation = new float[]{centerX, centerY};

        BaseWidth = width;
        BaseHeight = height;

        this.DrawingList = new RectangleDrawingList();
        this.drawer = new TextureDrawer(this);
    }

    public FloatBuffer GetUVBuffer() {return this.uvBuffer;}

    protected void UpdateUVBuffer()
    {
        uvs = TextureProvider.GetUVCoords(TextureIndex);
        CreateUVBuffer();
    }

    protected void CreateUVBuffer()
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(uvs.length*4);
        buffer.order(ByteOrder.nativeOrder());
        uvBuffer = buffer.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);
    }

    @Override
    public float Intersects(PointF point)
    {
        float triangleSum =
                TriangleArea(LeftLower(), point, RightLower()) +
                        TriangleArea(RightLower(), point, RightUpper()) +
                        TriangleArea(RightUpper(), point, LeftUpper()) +
                        TriangleArea(point, LeftUpper(), LeftLower());

        return triangleSum - Area();
    }

    private float TriangleArea(PointF a, PointF b, PointF c)
    {
        return Math.abs(a.x*(b.y-c.y)+b.x*(c.y-a.y)+c.x*(a.y-b.y))/2.0f;
    }
}
