package Engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;

import Engine.OpenGLObjects.Geometry.Circle;
import Engine.OpenGLObjects.Geometry.Line;
import Engine.OpenGLObjects.Geometry.Rectangle;
import Engine.OpenGLObjects.Geometry.RegularPolygon;
import Engine.OpenGLObjects.Geometry.Triangle;
import Engine.OpenGLObjects.OpenGLColor;
import Engine.OpenGLObjects.Sprites.Sprite;

/**
 * Created by Casper on 16-2-2015.
 */
public class OpenGLCanvas extends GLSurfaceView
{
    OpenGLRenderer renderer;
    public OpenGLCanvas(Context context)
    {
        super(context);
        this.setEGLContextClientVersion(2);
        renderer = new OpenGLRenderer(context);
        this.setRenderer(renderer);

        //Don't wait till dirty
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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

    public Sprite DrawSprite(Bitmap texture, Rectangle rectangle)
    {
        return null;
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

    public PointF ScreenSpaceToWorldSpace(PointF point)
    {
        float x = point.x / (float)getWidth() * 2.0f - 1.0f;
        float y = point.y / (float)getHeight() * -2.0f + 1.0f;
        return renderer.ToWorldCoords(new PointF(x,y));
    }
}
