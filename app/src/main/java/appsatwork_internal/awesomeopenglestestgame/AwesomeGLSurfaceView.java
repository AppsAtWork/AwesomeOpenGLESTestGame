package appsatwork_internal.awesomeopenglestestgame;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.Log;
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
        PointF worldTouch = renderer.ToWorldCoords(Clip(new PointF(event.getX(), event.getY())));

        //Figure out nearest corner
        float leftUpperDistance = distance(worldTouch, renderer.square.LeftUpper);
        float leftLowerDistance = distance(worldTouch, renderer.square.LeftLower);
        float rightUpperDistance = distance(worldTouch, renderer.square.RightUpper);
        float rightLowerDistance = distance(worldTouch, renderer.square.RightLower);

        float minimum = Math.min(leftUpperDistance, Math.min(leftLowerDistance, Math.min(rightLowerDistance, rightUpperDistance)));

        if(minimum < 0.3f) {
            if (minimum == leftUpperDistance)
                renderer.square.LeftUpper = worldTouch;
            else if (minimum == leftLowerDistance)
                renderer.square.LeftLower = worldTouch;
            else if (minimum == rightLowerDistance)
                renderer.square.RightLower = worldTouch;
            else if (minimum == rightUpperDistance)
                renderer.square.RightUpper = worldTouch;

            renderer.square.UpdateVertexBuffer();
        }
        return true;
    }

    private float distance(PointF p1, PointF p2)
    {
        float xDist = Math.abs(p1.x - p2.x);
        float yDist = Math.abs(p1.y - p2.y);
        return (float)Math.sqrt(xDist * xDist + yDist * yDist);
    }

    private PointF Clip(PointF point)
    {
        float x = point.x / (float)getWidth() * 2.0f - 1.0f;
        float y = point.y / (float)getHeight() * -2.0f + 1.0f;
        return new PointF(x,y);
    }

}
