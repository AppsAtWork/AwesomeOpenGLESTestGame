package Engine;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import Engine.OpenGLObjects.OpenGLObject;
import Engine.OpenGLObjects.Rectangle;
import Engine.OpenGLObjects.Triangle;

/**
 * Created by Casper on 7-2-2015.
 */
public class MyGLSurfaceView extends GLSurfaceView
{
    private OpenGLRenderer renderer;
    private Triangle driehoekie;
    private Rectangle blokje;
    private Rectangle blokje2;
    private Rectangle blokje3;

    public MyGLSurfaceView(Context context)
    {
        super(context);
        this.setEGLContextClientVersion(2);
        renderer = new OpenGLRenderer(context);
        this.setRenderer(renderer);

        //Don't wait till dirty
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        InitGameObjects();
    }

    public void InitGameObjects()
    {
        driehoekie = new Triangle(new PointF(-0.5f, 0.0f), new PointF(0.5f, 0.0f), new PointF(0.0f, 0.5f), 1.0f, 0.0f, 0.0f, 0.7f);
        blokje = new Rectangle(0,0,0.5f,0.25f, 0.0f, 1.0f, 0.0f, 0.7f);
        blokje2 = new Rectangle(0,0,0.25f,0.5f, 1.0f, 0.0f, 0.0f, 0.7f);
        blokje3 = new Rectangle(0,0,0.65f,0.75f, 1.0f, 0.0f, 1.0f, 0.7f);
        OpenGLObjectManager.Drawables.add(blokje);
        OpenGLObjectManager.Drawables.add(blokje2);
        OpenGLObjectManager.Drawables.add(blokje3);
        OpenGLObjectManager.Drawables.add(driehoekie);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        PointF worldCoords = Clip(new PointF(event.getX(), event.getY()));
        OpenGLObject oglObject = OpenGLObjectManager.FirstIntersection(worldCoords);

        if(oglObject != null) {
            OpenGLObjectManager.MoveToFront(oglObject);
            oglObject.SetCenter(worldCoords);
            oglObject.SetRotation(Util.Distance(new PointF(0,0), worldCoords)*360.0f);
            oglObject.SetScale(Util.Distance(new PointF(0,0), worldCoords)+0.3f);
            oglObject.ApplyTransformations();
        }
        return true;
    }

    private PointF Clip(PointF point)
    {
        float x = point.x / (float)getWidth() * 2.0f - 1.0f;
        float y = point.y / (float)getHeight() * -2.0f + 1.0f;
        return renderer.ToWorldCoords(new PointF(x,y));
    }
}
