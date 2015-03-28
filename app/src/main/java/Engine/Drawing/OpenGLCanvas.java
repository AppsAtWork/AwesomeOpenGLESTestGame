package Engine.Drawing;

import android.content.Context;
import android.graphics.PointF;

import Engine.Objects.GeometryObjects.Circle;
import Engine.Objects.GeometryObjects.Line;
import Engine.Objects.GeometryObjects.Rectangle;
import Engine.Objects.GeometryObjects.RegularPolygon;
import Engine.Objects.GeometryObjects.Triangle;
import Engine.Objects.Shape;
import Engine.Objects.Sprite;
import Engine.Objects.TextureObjects.TextureManagement;
import Engine.Objects.TextureObjects.UVCoordProviders.Texture;
import Engine.Objects.TextureObjects.UVCoordProviders.SimpleTextureAtlas;
import Engine.Objects.TextureObjects.UVCoordProviders.TextureProvider;
import Engine.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;
import Engine.Util.Color;


/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLCanvas
{
    private Context context;
    public DrawableList DrawableList;

    public OpenGLCanvas(Context context)
    {
        this.context = context; this.DrawableList = new DrawableList();
    }

    public SimpleTextureAtlas LoadSimpleTextureAtlas(int resourceID, int textureSize)
    {
        if(TextureManagement.GetTextureProvider(resourceID) == null)
        {
            SimpleTextureAtlas atlas = new SimpleTextureAtlas(textureSize,context.getResources(), resourceID);
            TextureManagement.EnableTextureProvider(atlas);
            return atlas;
        }
        else
        {
            return (SimpleTextureAtlas)TextureManagement.GetTextureProvider(resourceID);
        }
    }

    public VariableTextureAtlas LoadVariableTextureAtlas(int resourceID, int xmlID)
    {
        if(TextureManagement.GetTextureProvider(resourceID) == null)
        {
            VariableTextureAtlas atlas = new VariableTextureAtlas(context.getResources(), resourceID, xmlID);
            TextureManagement.EnableTextureProvider(atlas);
            return atlas;
        }
        else
        {
            return (VariableTextureAtlas)TextureManagement.GetTextureProvider(resourceID);
        }
    }

    public Sprite DrawSprite(SimpleTextureAtlas atlas, int atlasIndex, PointF center, float width, float height)
    {
        Sprite atlasSprite = new Sprite(new Rectangle(center.x, center.y, width, height), atlas, atlasIndex);
        DrawableList.Add(atlasSprite);
        return atlasSprite;
    }

    public Sprite DrawSprite(VariableTextureAtlas atlas, int atlasIndex, PointF center, float width, float height)
    {
        Sprite atlasSprite = new Sprite(new Rectangle(center.x, center.y, width, height), atlas, atlasIndex);
        DrawableList.Add(atlasSprite);
        return atlasSprite;
    }

    public Sprite DrawSprite(int resourceID, PointF center, float width, float height)
    {
        //Check if there is already a texture in tehre
        TextureProvider provider = TextureManagement.GetTextureProvider(resourceID);
        if(provider == null)
        {
           return GetTextureSprite(resourceID, center, width, height);
        }
        else
        {
            //There is a texture provider in there for this texture, but we can only use it if it is not a texture atlas
            if(provider.getClass() == Texture.class)
            {
                //Use the provided texture provider
                Texture texture = (Texture)provider;
                Sprite sprite = new Sprite(new Rectangle(center.x, center.y, width, height), texture);
                DrawableList.Add(sprite);
                return sprite;
            }
            else
            {
                return GetTextureSprite(resourceID, center, width, height);
            }
        }
    }

    private Sprite GetTextureSprite(int resourceID, PointF center, float width, float height)
    {
        //Create a new texture provider and return the damn thing
        Texture texture = new Texture(context.getResources(), resourceID);
        Sprite sprite = new Sprite(new Rectangle(center.x, center.y, width, height), texture);
        TextureManagement.EnableTextureProvider(texture);
        DrawableList.Add(sprite);
        return sprite;
    }

    public Shape DrawTriangle(PointF pt1, PointF pt2, PointF pt3, Color color)
    {
        Triangle triangle = new Triangle(pt1, pt2, pt3);
        Shape shape = new Shape(triangle, color);
        DrawableList.Add(shape);
        return shape;
    }

    //Draw a line between pt1 and pt2 with thickness. Coordinates and lengths are in world space.
    //Return a line that can be manipulated flexibly.
    public Shape DrawLine(PointF pt1, PointF pt2, float thickness, Color color)
    {
        Line line = new Line(pt1, pt2,thickness);
        Shape shape = new Shape(line, color);
        DrawableList.Add(shape);
        return shape;
    }

    //Draw a rectangle. Coordinates and lengths are in world space.
    //Returns a rectangle that can be manipulated flexibly.
    public Shape DrawRectangle(PointF center, float width, float height, Color color)
    {
        Rectangle rect = new Rectangle(center.x,center.y, width, height);
        Shape shape = new Shape(rect, color);
        DrawableList.Add(shape);
        return shape;
    }

    public Shape DrawRegularPolygon(PointF center, float radius, int corners, Color color)
    {
        RegularPolygon pol = new RegularPolygon(center, radius, corners);
        Shape shape = new Shape(pol, color);
        DrawableList.Add(shape);
        return shape;
    }

    public Shape DrawCircle(PointF center, float radius, Color color)
    {
        Circle circle = new Circle(center, radius);
        Shape shape = new Shape(circle, color);
        DrawableList.Add(shape);
        return shape;
    }
}
