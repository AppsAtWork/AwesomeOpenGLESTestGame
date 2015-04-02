package com.appsatwork.ezgles.Drawing;

import android.content.Context;
import android.graphics.PointF;

import com.appsatwork.ezgles.Objects.Drawables.BorderedShapeDrawable;
import com.appsatwork.ezgles.Objects.Drawables.SpriteDrawable;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Border;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Line;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Rectangle;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.RegularPolygon;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Triangle;
import com.appsatwork.ezgles.Objects.Drawables.ShapeDrawable;
import com.appsatwork.ezgles.Objects.TextureObjects.TextureManager;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.Texture;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.SimpleTextureAtlas;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.TextureProvider;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;
import com.appsatwork.ezgles.Util.Color;


/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLCanvas
{
    private Context context;
    public DrawableList DrawableList;
    public TextureManager TextureManager;

    public OpenGLCanvas(Context context)
    {
        this.context = context;
        this.DrawableList = new DrawableList();
        this.TextureManager = new TextureManager();
    }

    public SimpleTextureAtlas LoadSimpleTextureAtlas(int resourceID, int textureSize)
    {
        if(TextureManager.GetTextureProvider(resourceID) == null)
        {
            SimpleTextureAtlas atlas = new SimpleTextureAtlas(textureSize,context.getResources(), resourceID);
            TextureManager.EnableTextureProvider(atlas);
            return atlas;
        }
        else
        {
            return (SimpleTextureAtlas) TextureManager.GetTextureProvider(resourceID);
        }
    }

    public VariableTextureAtlas LoadVariableTextureAtlas(int resourceID, int xmlID)
    {
        if(TextureManager.GetTextureProvider(resourceID) == null)
        {
            VariableTextureAtlas atlas = new VariableTextureAtlas(context.getResources(), resourceID, xmlID);
            TextureManager.EnableTextureProvider(atlas);
            return atlas;
        }
        else
        {
            return (VariableTextureAtlas) TextureManager.GetTextureProvider(resourceID);
        }
    }

    public SpriteDrawable DrawSprite(SimpleTextureAtlas atlas, int atlasIndex, PointF center, float width, float height)
    {
        SpriteDrawable atlasSpriteDrawable = new SpriteDrawable(new Rectangle(center, width, height), atlas, atlasIndex);
        DrawableList.Add(atlasSpriteDrawable);
        return atlasSpriteDrawable;
    }

    public SpriteDrawable DrawSprite(VariableTextureAtlas atlas, int atlasIndex, PointF center, float width, float height)
    {
        SpriteDrawable atlasSpriteDrawable = new SpriteDrawable(new Rectangle(center, width, height), atlas, atlasIndex);
        DrawableList.Add(atlasSpriteDrawable);
        return atlasSpriteDrawable;
    }

    public SpriteDrawable DrawSprite(int resourceID, PointF center, float width, float height)
    {
        //Check if there is already a texture in tehre
        TextureProvider provider = TextureManager.GetTextureProvider(resourceID);
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
                SpriteDrawable spriteDrawable = new SpriteDrawable(new Rectangle(center, width, height), texture);
                DrawableList.Add(spriteDrawable);
                return spriteDrawable;
            }
            else
            {
                return GetTextureSprite(resourceID, center, width, height);
            }
        }
    }

    private SpriteDrawable GetTextureSprite(int resourceID, PointF center, float width, float height)
    {
        //Create a new texture provider and return the damn thing
        Texture texture = new Texture(context.getResources(), resourceID);
        SpriteDrawable spriteDrawable = new SpriteDrawable(new Rectangle(center, width, height), texture);
        TextureManager.EnableTextureProvider(texture);
        DrawableList.Add(spriteDrawable);
        return spriteDrawable;
    }

    public ShapeDrawable DrawTriangle(PointF pt1, PointF pt2, PointF pt3, Color color)
    {
        Triangle triangle = new Triangle(pt1, pt2, pt3);
        ShapeDrawable shapeDrawable = new ShapeDrawable(triangle, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    //Draw a line between pt1 and pt2 with thickness. Coordinates and lengths are in world space.
    //Return a line that can be manipulated flexibly.
    public ShapeDrawable DrawLine(PointF pt1, PointF pt2, float thickness, Color color)
    {
        Line line = new Line(pt1, pt2,thickness);
        ShapeDrawable shapeDrawable = new ShapeDrawable(line, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    //Draw a rectangle. Coordinates and lengths are in world space.
    //Returns a rectangle that can be manipulated flexibly.
    public ShapeDrawable DrawRectangle(PointF center, float width, float height, Color color)
    {
        Rectangle rect = new Rectangle(center, width, height);
        ShapeDrawable shapeDrawable = new ShapeDrawable(rect, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    public ShapeDrawable DrawRegularPolygon(PointF center, float radius, int corners, Color color)
    {
        RegularPolygon pol = new RegularPolygon(center, radius, corners);
        ShapeDrawable shapeDrawable = new ShapeDrawable(pol, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    public ShapeDrawable DrawCircle(PointF center, float radius, Color color)
    {
        RegularPolygon circle = new RegularPolygon(center, radius, 50);
        ShapeDrawable shapeDrawable = new ShapeDrawable(circle, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    public ShapeDrawable DrawBorder(Rectangle around, float thickness, Color color)
    {
        Border border = new Border(around, thickness);
        ShapeDrawable shapeDrawable = new ShapeDrawable(border, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    public ShapeDrawable DrawBorder(RegularPolygon around, float thickness, Color color)
    {
        Border border = new Border(around, thickness);
        ShapeDrawable shapeDrawable = new ShapeDrawable(border, color);
        DrawableList.Add(shapeDrawable);
        return shapeDrawable;
    }

    public BorderedShapeDrawable DrawWithBorder(Rectangle geometry, float thickness, Color color, Color borderColor)
    {
        Border border = new Border(geometry, thickness);
        BorderedShapeDrawable shape = new BorderedShapeDrawable(border, geometry, color, borderColor);
        DrawableList.Add(shape);
        return shape;
    }

    public BorderedShapeDrawable DrawWithBorder(RegularPolygon geometry, float thickness, Color color, Color borderColor)
    {
        Border border = new Border(geometry, thickness);
        BorderedShapeDrawable shape = new BorderedShapeDrawable(border, geometry, color, borderColor);
        DrawableList.Add(shape);
        return shape;
    }
}
