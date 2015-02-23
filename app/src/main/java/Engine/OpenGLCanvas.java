package Engine;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;

import Engine.OpenGLObjects.Geometry.Circle;
import Engine.OpenGLObjects.Geometry.Line;
import Engine.OpenGLObjects.Geometry.Rectangle;
import Engine.OpenGLObjects.Geometry.RegularPolygon;
import Engine.OpenGLObjects.Geometry.Triangle;
import Engine.OpenGLObjects.OpenGLColor;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.AtlasSprite;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;
import Engine.OpenGLObjects.Sprites.TextureManagement;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.Texture;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureAtlas;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureProvider;


/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLCanvas
{
    private Context context;
    public OpenGLCanvas(Context context)
    {
        this.context = context;
    }

    public TextureAtlas LoadTextureAtlas(int resourceID, int textureSize)
    {
        if(TextureManagement.GetTextureProvider(resourceID) == null)
        {
            TextureAtlas atlas = new TextureAtlas(textureSize,context.getResources(), resourceID);
            TextureManagement.EnableTextureProvider(atlas);
            return atlas;
        }
        else
        {
            return (TextureAtlas)TextureManagement.GetTextureProvider(resourceID);
        }
    }

    public AtlasSprite DrawSprite(TextureAtlas atlas, int atlasIndex, PointF center, float width, float height, FittingType type)
    {
        AtlasSprite atlasSprite = new AtlasSprite(atlas, atlasIndex, center.x, center.y, width,height, type);
        atlasSprite.StartDrawing();
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
                sprite.StartDrawing();
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
        textureSprite.StartDrawing();
        return textureSprite;
    }

    public Triangle DrawTriangle(PointF pt1, PointF pt2, PointF pt3, float r, float g, float b, float alpha)
    {
        Triangle triangle = new Triangle(pt1, pt2, pt3, r,g,b,alpha);
        triangle.StartDrawing();
        return triangle;
    }

    //Draw a circle with the specified radius and center point.
    //Coordinates and lengths are in world space.
    //Returns a circle that can be manipulated flexibly (but indirectly).
    public Circle DrawCircle(PointF center, float radius, float r, float g, float b, float alpha)
    {
        Circle circle = new Circle(center.x, center.y, radius, new OpenGLColor(r,g,b,alpha));
        circle.StartDrawing();
        return circle;
    }

    //Draw a line between pt1 and pt2 with thickness. Coordinates and lengths are in world space.
    //Return a line that can be manipulated flexibly.
    public Line DrawLine(PointF pt1, PointF pt2, float thickness, float r, float g, float b, float alpha)
    {
        Line line = new Line(pt1, pt2,thickness, r,g,b,alpha);
        line.StartDrawing();
        return line;
    }

    //Draw a rectangle. Coordinates and lengths are in world space.
    //Returns a rectangle that can be manipulated flexibly.
    public Rectangle DrawRectangle(PointF center, float width, float height, float r, float g, float b, float alpha)
    {
        Rectangle rect = new Rectangle(center.x,center.y, width, height, r,g,b,alpha);
        rect.StartDrawing();
        return rect;
    }

    public RegularPolygon DrawRegularPolygon(PointF center, float radius, int corners, OpenGLColor color)
    {
        RegularPolygon pol = new RegularPolygon(center.x, center.y, radius, corners, color);
        pol.StartDrawing();
        return pol;
    }
}
