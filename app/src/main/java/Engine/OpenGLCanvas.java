package Engine;

import android.content.Context;
import android.graphics.PointF;

import Engine.Drawing.DrawableList;
import Engine.Objects.Geometry.Line;
import Engine.Objects.Geometry.Rectangle;
import Engine.Objects.Geometry.RegularPolygon;
import Engine.Objects.Geometry.Triangle;
import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.SpriteObjects.AtlasSprite;
import Engine.Objects.Sprites.SpriteObjects.TextureSprite;
import Engine.Objects.Sprites.TextureManagement;
import Engine.Objects.Sprites.UVCoordProviders.Texture;
import Engine.Objects.Sprites.UVCoordProviders.SimpleTextureAtlas;
import Engine.Objects.Sprites.UVCoordProviders.TextureProvider;
import Engine.Objects.Sprites.UVCoordProviders.VariableTextureAtlas;
import Engine.Util.Color;


/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLCanvas
{
    private Context context;
    public Engine.Drawing.DrawableList DrawableList;

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

    public AtlasSprite DrawSprite(TextureProvider atlas, int atlasIndex, PointF center, float width, float height, FittingType type)
    {
        AtlasSprite atlasSprite = new AtlasSprite(atlas, atlasIndex, center.x, center.y, width,height, type);
        DrawableList.Add(atlasSprite);
        return atlasSprite;
    }

    public TextureSprite DrawSprite(int resourceID, PointF center, float width, float height, FittingType type)
    {
        //Check if there is already a texture in tehre
        TextureProvider provider = TextureManagement.GetTextureProvider(resourceID);
        if(provider == null)
        {
           return GetTextureSprite(resourceID, center, width, height, type);
        }
        else
        {
            //There is a texture provider in there for this texture, but we can only use it if it is not a texture atlas
            if(provider.getClass() == Texture.class)
            {
                //Use the provided texture provider
                Texture texture = (Texture)provider;
                TextureSprite sprite = new TextureSprite(texture, center.x, center.y, width, height, type);
                DrawableList.Add(sprite);
                return sprite;
            }
            else
            {
                return GetTextureSprite(resourceID, center, width, height, type);
            }
        }
    }

    private TextureSprite GetTextureSprite(int resourceID, PointF center, float width, float height, FittingType type)
    {
        //Create a new texture provider and return the damn thing
        Texture texture = new Texture(context.getResources(), resourceID);
        TextureSprite textureSprite = new TextureSprite(texture, center.x, center.y, width, height, type);
        TextureManagement.EnableTextureProvider(texture);
        DrawableList.Add(textureSprite);
        return textureSprite;
    }

    public Triangle DrawTriangle(PointF pt1, PointF pt2, PointF pt3, float r, float g, float b, float alpha)
    {
        Triangle triangle = new Triangle(pt1, pt2, pt3, r,g,b,alpha);
        DrawableList.Add(triangle);
        return triangle;
    }

    //Draw a line between pt1 and pt2 with thickness. Coordinates and lengths are in world space.
    //Return a line that can be manipulated flexibly.
    public Line DrawLine(PointF pt1, PointF pt2, float thickness, float r, float g, float b, float alpha)
    {
        Line line = new Line(pt1, pt2,thickness, r,g,b,alpha);
        DrawableList.Add(line);
        return line;
    }

    //Draw a rectangle. Coordinates and lengths are in world space.
    //Returns a rectangle that can be manipulated flexibly.
    public Rectangle DrawRectangle(PointF center, float width, float height, float r, float g, float b, float alpha)
    {
        Rectangle rect = new Rectangle(center.x,center.y, width, height, r,g,b,alpha);
        DrawableList.Add(rect);
        return rect;
    }

    public RegularPolygon DrawRegularPolygon(PointF center, float radius, int corners, Color color)
    {
        RegularPolygon pol = new RegularPolygon(center.x, center.y, radius, corners, color);
        DrawableList.Add(pol);
        return pol;
    }
}
