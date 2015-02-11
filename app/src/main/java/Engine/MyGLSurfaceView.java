package Engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import Engine.OpenGLObjects.Sprite;
import Engine.OpenGLObjects.Square;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 7-2-2015.
 */
public class MyGLSurfaceView extends GLSurfaceView
{
    private OpenGLRenderer renderer;
    private Square test;
    private Square test2;

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
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dj);
        test = new Square(0.25f, -0.25f, 0.5f, 0.75f, 0.2f, 0.5f, 0.3f, 1.0f);
        test2 = new Square(-0.25f, 0.25f, 0.5f, 0.25f, 0.5f, 0.3f, 0.4f, 1.0f);
        OpenGLObjectManager.Drawables.add(test);
        OpenGLObjectManager.Drawables.add(test2);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        test.SetCenter(ToWorldSpace(new PointF(event.getX(), event.getY())));
        test.SetRotation(Util.Distance(ToWorldSpace(new PointF(event.getX(), event.getY())), new PointF(-1.0f, 1.0f)) * 360.0f);
        test.SetScale(Util.Distance(ToWorldSpace(new PointF(event.getX(), event.getY())), new PointF(0,0)));
        test.ApplyTransformations();

        PointF ding = ToWorldSpace(new PointF(event.getX(), event.getY()));
        test2.SetCenter(new PointF(-ding.x, -ding.y));
        test2.SetRotation(Util.Distance(new PointF(-ding.x, -ding.y), new PointF(-1.0f, 1.0f)) * 360.0f);
        test2.SetScale(Util.Distance(ToWorldSpace(new PointF(event.getX(), event.getY())), new PointF(0,0)));
        test2.ApplyTransformations();
        return true;
    }

    private PointF ToWorldSpace(PointF point)
    {
        float x = point.x / (float)getWidth() * 2.0f - 1.0f;
        float y = point.y / (float)getHeight() * -2.0f + 1.0f;
        return renderer.ToWorldCoords(new PointF(x,y));
    }
}
