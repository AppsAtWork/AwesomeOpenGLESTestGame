package appsatwork_internal.awesomeopenglestestgame;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Casper on 7-2-2015.
 */
public class AwesomeGLSurfaceView extends GLSurfaceView
{
    private AwesomeRenderer renderer;

    public AwesomeGLSurfaceView(Context context)
    {
        super(context);
        this.setEGLContextClientVersion(2);
        renderer = new AwesomeRenderer();
        this.setRenderer(renderer);

        //Don't wait till dirty
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        PointF touch = Clip(new PointF(event.getX(), event.getY()));

        renderer.square.SetColor(touch.x, touch.y, 0.7f);

        return true;
    }

    private PointF Clip(PointF point)
    {
        float x = point.x / getWidth() * -2 + 1.0f;
        float y = point.y / getHeight() * -2 + 1.0f;
        return new PointF(x,y);
    }

}
